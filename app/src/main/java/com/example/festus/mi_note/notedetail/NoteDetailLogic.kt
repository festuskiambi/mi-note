package com.example.festus.mi_note.notedetail

import com.example.domain.DispatcherProvider
import com.example.domain.ServiceLocator
import com.example.domain.domainmodel.Note
import com.example.domain.interactor.AnonymousNoteSource
import com.example.domain.interactor.AuthSource
import com.example.domain.interactor.PublicNoteSource
import com.example.domain.interactor.RegisteredNoteSource
import com.example.festus.mi_note.common.BaseLogic
import com.example.festus.mi_note.common.MESSAGE_GENERIC_ERROR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by Festus Kiambi on 12/10/18.
 */
class NoteDetailLogic(
    dispatcher: DispatcherProvider,
    locator: ServiceLocator,
    val vModel: INoteDetailContract.ViewModel,
    val view: INoteDetailContract.View,
    val anonymous: AnonymousNoteSource,
    val registered: RegisteredNoteSource,
    val public: PublicNoteSource,
    val authSource: AuthSource,
    id: String,
    isPrivate: Boolean
): BaseLogic(dispatcher, locator), INoteDetailContract.Logic,CoroutineScope {

    init {
        vModel.setId(id)
        vModel.setIsPrivateMode(isPrivate)
        jobTracker = Job()
    }

    override val coroutineContext: CoroutineContext
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    override fun event(event: NoteDetailEvent) {
        when(event){
            is NoteDetailEvent.onBackClick -> onBackClick()
            is NoteDetailEvent.onStart -> onStart()
        }
    }

    private fun onBackClick(){
       view.startListFeature()
    }

    private fun onStart(){
        val state = vModel.getNoteState()

        if (state !=null){
            renderView(state)
        }else{
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