package com.example.domain.interactor

import com.example.domain.DispatcherProvider
import com.example.domain.ServiceLocator
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking


/**
 * Created by Festus Kiambi on 12/3/18.
 */
class RegisteredNoteSource {

    suspend fun getNotes(
        locator: ServiceLocator,
        dispatcher: DispatcherProvider
    ): Result<Exception, List<Note>> = runBlocking {

        val localResult = async(dispatcher.provideIOContext()) {
            locator.remoteReg.getNotes()
        }

      //  locator.cacheReg.getNotes()

        localResult.await()
    }

    suspend fun getNoteById(
        id: String,
        locator: ServiceLocator,
        dispatcher: DispatcherProvider
    ): Result<Exception, Note?> = coroutineScope {

        val localResult = async(dispatcher.provideIOContext()) {
            locator.remoteReg.getNote(id)
        }

        localResult.await()
    }

    suspend fun updateNote(
        note: Note,
        locator: ServiceLocator,
        dispatcher: DispatcherProvider
    ): Result<Exception, Boolean> = coroutineScope {

        val localResult = async(dispatcher.provideIOContext()) {
            locator.remoteReg.updateNote(note)
        }

        localResult.await()
    }

    suspend fun deleteNote(
        note: Note,
        locator: ServiceLocator,
        dispatcher: DispatcherProvider
    ): Result<Exception, Boolean> = coroutineScope{

        val localResult = async(dispatcher.provideIOContext()) {
            locator.remoteReg.deleteNote(note)
        }

        localResult.await()
    }
}