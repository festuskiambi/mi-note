package com.example.festus.mi_note.notelist

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
import com.example.festus.mi_note.common.MODE_PRIVATE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Festus Kiambi on 1/7/19.
 */

class NoteListLogic(
    dispatcher: DispatcherProvider,
    val userServiceLocator: UserServiceLocator,
    val locator: NoteServiceLocator,
    val view: INoteListContract.View,
    val adapter: NoteListAdapter,
    val vModel: INoteListContract.ViewModel,
    val navigator: INoteListContract.Navigator,
    val anonymous: AnonymousNoteSource,
    val registered: RegisteredNoteSource,
    val public: PublicNoteSource,
    val authSource: AuthSource
) : BaseLogic(dispatcher), INoteListContract.Logic, CoroutineScope {

    init {
        jobTracker = Job()
    }

    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker


    override fun event(event: NoteListEvent<Int>) {
        when (event) {
            is NoteListEvent.OnNewNoteClick -> onNewNoteClick()
            is NoteListEvent.OnBind -> bind()
            is NoteListEvent.OnStart -> onStart()
            is NoteListEvent.OnLoginClick -> onLoginClick()
            is NoteListEvent.OnNoteItemClick -> onNoteItemClicked(event.position)
        }
    }

    private fun onNoteItemClicked(position: Int) {
        val noteList = vModel.getAdapterState()

        navigator.startNoteDetailFeatureWithExtras(noteList[position].creationDate,vModel.getIsPrivate())
    }

    private fun onLoginClick() {
        navigator.startLoginFeature()
    }

    private fun onNewNoteClick() {
        navigator.startNoteDetailFeatureWithExtras("", vModel.getIsPrivate())
    }

    private fun bind() {
        view.setToolbarTitle(MODE_PRIVATE)
        view.setAdapter(adapter)
        view.showLoadingView()
        adapter.logic = this

        launch {
            val result = authSource.getCurrentUser(userServiceLocator)

            when (result) {
                is Result.Value -> {
                    vModel.setUserState(result.value)
                }

                is Result.Error -> {
                    TODO()
                }

            }
        }
    }

    private fun onStart() {
        jobTracker = Job()
        getListData(vModel.getIsPrivate())
    }

    private fun getListData(isPrivate: Boolean) = launch {

        val dataResult: Result<Exception, List<Note>>

        when (isPrivate) {
            true -> dataResult = getPrivateListData()
            false -> dataResult = getPublicListData()
        }

        when (dataResult) {
            is Result.Value -> {
                vModel.setAdapterState(dataResult.value)
                renderView(dataResult.value)
            }
        }

    }

    private fun renderView(list: List<Note>) {
        if (list.isEmpty()) view.showEmptyState()
        else view.showNoteList()

        adapter.submitList(list)
    }

    suspend fun getPublicListData(): Result<Exception, List<Note>> {
        return public.getNotes(locator, dispatcher)
    }

    suspend fun getPrivateListData(): Result<Exception, List<Note>> {
        return if (vModel.getUserState() == null) anonymous.getNotes(locator, dispatcher)
        else registered.getNotes(locator)
    }


}
