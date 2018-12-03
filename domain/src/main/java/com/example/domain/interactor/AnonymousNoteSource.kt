package com.example.domain.interactor

import com.example.domain.DispatcherProvider
import com.example.domain.ServiceLocator
import com.example.domain.domainmodel.Note
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import com.example.domain.domainmodel.Result


/**
 * Created by Festus Kiambi on 12/3/18.
 */

class AnonymousNoteSource {
    suspend fun getNotes(locator: ServiceLocator, dispatcher: DispatcherProvider):
            Result<Exception, List<Note>> = runBlocking {

        val localResult = async(dispatcher.provideIOContext()) {
            locator.localAnon.getNotes()
        }

        localResult.await()
    }

    suspend fun getNoteById(id: String, locator: ServiceLocator, dispatcher: DispatcherProvider):
            Result<Exception, Note?> = runBlocking {

        val localResult = async(dispatcher.provideIOContext()) {
            locator.localAnon.getNote(id)
        }

        localResult.await()
    }

    suspend fun updateNote(note: Note, locator: ServiceLocator, dispatcher: DispatcherProvider):
            Result<Exception, Boolean> = runBlocking {
        val localResult = async(dispatcher.provideIOContext()) {
            locator.localAnon.updateNote(note)
        }

        localResult.await()
    }

    suspend fun deleteNote(note: Note, locator: ServiceLocator, dispatcher: DispatcherProvider):
            Result<Exception, Boolean> = runBlocking {
        val localResult = async(dispatcher.provideIOContext()) {
            locator.localAnon.deleteNote(note)
        }

        localResult.await()

    }


}