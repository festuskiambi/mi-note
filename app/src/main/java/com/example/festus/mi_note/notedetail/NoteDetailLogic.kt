package com.example.festus.mi_note.notedetail

import com.example.domain.DispatcherProvider
import com.example.domain.NoteServiceLocator
import com.example.domain.UserServiceLocator
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result
import com.example.domain.interactor.AnonymousNoteSource
import com.example.domain.interactor.AuthSource
import com.example.domain.interactor.PublicNoteSource
import com.example.domain.interactor.RegisteredNoteSource
import com.example.festus.mi_note.common.BaseLogic
import com.example.festus.mi_note.common.MESSAGE_GENERIC_ERROR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Festus Kiambi on 12/10/18.
 */
class NoteDetailLogic(
    dispatcher: DispatcherProvider,
    val userServiceLocator: UserServiceLocator,
    val locator: NoteServiceLocator,
    val vModel: INoteDetailContract.ViewModel,
    val view: INoteDetailContract.View,
    val anonymous: AnonymousNoteSource,
    val registered: RegisteredNoteSource,
    val public: PublicNoteSource,
    val authSource: AuthSource,
    id: String,
    isPrivate: Boolean
) : BaseLogic(dispatcher), INoteDetailContract.Logic, CoroutineScope {

    init {
        vModel.setId(id)
        vModel.setIsPrivateMode(isPrivate)
        jobTracker = Job()
    }

    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker


    override fun event(event: NoteDetailEvent) {
        when (event) {
            is NoteDetailEvent.OnBackClick -> onBackClick()
            is NoteDetailEvent.OnStart -> onStart()
            is NoteDetailEvent.OnDoneClick -> onDoneClick()
        }
    }

    fun onDoneClick() = launch {

        val userResult = authSource.getCurrentUser(userServiceLocator)

        when (userResult) {
            is Result.Value -> prepareRegistereedRepoUpdate()
            else -> prepareAnonymousRepoUpdate()
        }


    }

    suspend fun prepareAnonymousRepoUpdate() {

        val updateNote = vModel.getNoteState()!!.copy(contents = view.getNoteBody())

        val updateResult = anonymous.updateNote(updateNote,locator,dispatcher)

        when (updateResult) {
            is Result.Value -> view.startListFeature()
            is Result.Error -> view.showMessage(updateResult.error.toString())
        }
    }


    private suspend fun prepareRegistereedRepoUpdate() {
        val updatedNote = vModel.getNoteState()!!.copy(contents = view.getNoteBody())

        val result = registered.updateNote(updatedNote, locator)

        when (result) {
            is Result.Value -> view.startListFeature()
            is Result.Error -> view.showMessage(result.error.toString())
        }
    }

    private fun onBackClick() {
        view.startListFeature()
    }

    private fun onStart() {
        val state = vModel.getNoteState()

        if (state != null) {
            renderView(state)
        } else {
            view.showMessage(MESSAGE_GENERIC_ERROR)
            view.startListFeature()
        }
    }

    private fun renderView(state: Note) {
        view.setBackgroundImage(state.imageUrl)
        view.setNoteBody(state.contents)
        view.setDateLabel(state.creationDate)
    }


}