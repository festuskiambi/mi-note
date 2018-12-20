package com.example.festus.mi_note.buildlogic

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.example.data.auth.FirebaseAuthRepositoryImpl
import com.example.data.note.anonymous.AnonymousNoteDao
import com.example.data.note.anonymous.AnonymousNoteDatabase
import com.example.data.note.anonymous.RoomLocalAnonymousRepositoryImpl
import com.example.data.note.registered.FirestoreRemoteNoteImpl
import com.example.data.transaction.RoomTransactionRepositoryImpl
import com.example.domain.DispatcherProvider
import com.example.domain.NoteServiceLocator
import com.example.domain.interactor.AnonymousNoteSource
import com.example.domain.interactor.AuthSource
import com.example.domain.interactor.PublicNoteSource
import com.example.domain.interactor.RegisteredNoteSource
import com.example.domain.repository.*
import com.example.festus.mi_note.login.ILoginContract
import com.example.festus.mi_note.login.LoginActivity
import com.example.festus.mi_note.login.LoginLogic
import com.example.festus.mi_note.notedetail.*
import com.google.firebase.FirebaseApp

/**
 * Created by Festus Kiambi on 12/10/18.
 */

class Injector(private val activityContext: Context) {

    init {
        FirebaseApp.initializeApp(activityContext)
    }

    //For non-registered user persistence
    private val localAnon: ILocalNoteRepository by lazy {
        RoomLocalAnonymousRepositoryImpl(noteDao)
    }

    //For registered user remote persistence (Source of Truth)
    private val remoteReg: IRemoteNoteRepository by lazy {
        FirestoreRemoteNoteImpl()
    }

    //For registered user local persistience (cache)
//    private val cacheReg: ILocalNoteRepository by lazy {
//        RoomLocalCacheImpl(noteDao)
//    }

    //For registered user local persistience (cache)
    private val transactionReg: ITransactionRepository by lazy {
        RoomTransactionRepositoryImpl()
    }

    //For user management
    private val auth: IAuthRepository by lazy {
        FirebaseAuthRepositoryImpl()
    }

    private val noteDao: AnonymousNoteDao by lazy {
        AnonymousNoteDatabase.getInstance(activityContext).roomNoteDao()
    }
//
//    fun provideNoteListLogic(view: NoteListView): INoteListContract.Logic {
//        return NoteListLogic(
//            DispatcherProvider,
//            NoteServiceLocator(localAnon, remoteReg, transactionReg, auth),
//            ViewModelProviders.of(activityContext as NoteListActivity).get(NoteListViewModel::class.java),
//            NoteListAdapter(),
//            view,
//            AnonymousNoteSource(),
//            RegisteredNoteSource(),                PublicNoteSource(),
//            AuthSource()
//        )
//    }

    fun provideLoginLogic(view: LoginActivity): ILoginContract.Logic {
        return LoginLogic(
            DispatcherProvider,
            NoteServiceLocator(localAnon, remoteReg, transactionReg, auth),
            view,
            AuthSource()
        )
    }

    fun provideNoteDetailLogic(view: NoteDetailView, id: String, isPrivate:Boolean): INoteDetailContract.Logic {
        return NoteDetailLogic(
            DispatcherProvider,
            NoteServiceLocator(localAnon, remoteReg, transactionReg, auth),
            ViewModelProviders.of(activityContext as NoteDetailActivity)
                .get(NoteDetailViewModel::class.java),
            view,
            AnonymousNoteSource(),
            RegisteredNoteSource(),
            PublicNoteSource(),
            AuthSource(),
            id,
            isPrivate
        )
    }
}