package com.example.data.note

import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result
import com.example.domain.repository.INoteRepository

/**
 * Created by Festus Kiambi on 12/4/18.
 */

class RoomLocalAnonymousRepositoryImpl: INoteRepository{
    override suspend fun getNotes(): Result<Exception, List<Note>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getNote(id: String): Result<Exception, Note?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteNote(note: Note): Result<Exception, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateNote(note: Note): Result<Exception, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}