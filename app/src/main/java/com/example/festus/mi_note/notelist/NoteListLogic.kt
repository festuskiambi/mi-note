package com.example.festus.mi_note.notelist

import com.example.domain.DispatcherProvider
import com.example.domain.NoteServiceLocator
import com.example.domain.UserServiceLocator
import com.example.domain.interactor.AnonymousNoteSource
import com.example.domain.interactor.AuthSource
import com.example.domain.interactor.PublicNoteSource
import com.example.domain.interactor.RegisteredNoteSource
import com.example.festus.mi_note.common.BaseLogic
import kotlinx.coroutines.CoroutineScope
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
    val vModel :INoteListContract.ViewModel,
    val navigator: INoteListContract.Navigator,
    val anonymous: AnonymousNoteSource,
    val registered: RegisteredNoteSource,
    val public: PublicNoteSource,
    val authSource: AuthSource
    ): BaseLogic(dispatcher), INoteListContract.Logic, CoroutineScope{

    override val coroutineContext: CoroutineContext
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    override fun event(event: NoteListEvent<Int>) {
        when(event){
            is NoteListEvent.OnNewNoteClick -> onNewNoteClick()
        }
    }

    private fun onNewNoteClick() {
        navigator.startNoteDetailFeatureWithExtras("",vModel.getIsPrivate())
    }


}