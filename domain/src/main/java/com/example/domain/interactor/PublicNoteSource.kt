package com.example.domain.interactor

import com.example.domain.DispatcherProvider
import com.example.domain.ServiceLocator
import com.example.domain.domainmodel.ColorType
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.User
import kotlinx.coroutines.channels.Channel
import com.example.domain.domainmodel.Result


/**
 * Created by Festus Kiambi on 12/3/18.
 */

class PublicNoteSource {
    suspend fun getNotes(locator: ServiceLocator,
                         dispatcher: DispatcherProvider): Result<Exception, List<Note>> {
        val listener = Channel<Result<Exception, List<Note>>>()

//    launch {
//        remote.getNotes(listener)
//    }
//
//    launch {
//        local.getNotes(listener)
//    }

        return listener.receive()
    }

    suspend fun getNoteById(id: String,
                            locator: ServiceLocator,
                            dispatcher: DispatcherProvider): Result<Exception, Note> {


        return Result.build {
            Note("28/10/2018",
                "When I understand that this glass is already broken, every moment with it becomes precious.",
                0,
                ColorType.BLUE,
                User(
                    "8675309",
                    "Ajahn Chah",
                    ""
                )
            )
        }
    }

    suspend fun updateNote(note: Note,
                           locator: ServiceLocator,
                           dispatcher: DispatcherProvider): Result<Exception, Boolean> {
        val listener = Channel<Result<Exception, Boolean>>()

//    launch {
//        locator.remote.updateNote(note, listener)
//    }
//
//    launch {
//        locator.local.updateNote(note, listener)
//    }

        return listener.receive()
    }

    suspend fun deleteNote(id: String,
                           locator: ServiceLocator,
                           dispatcher: DispatcherProvider
    ): Result<Exception, Boolean> {
        val listener = Channel<Result<Exception, Boolean>>()

//    launch {
//        locator.remote.updateNote(note, listener)
//    }
//
//    launch {
//        locator.local.updateNote(note, listener)
//    }

        return listener.receive()
    }
}