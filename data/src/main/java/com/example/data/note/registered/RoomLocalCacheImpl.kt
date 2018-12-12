package com.example.data.note.registered

import com.example.data.note.RoomNoteDao
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result
import com.example.domain.repository.ILocalNoteRepository

/**
 * Created by Festus Kiambi on 12/12/18.
 */

class RoomLocalCacheImpl(noteDao: RoomNoteDao): ILocalNoteRepository{
    override suspend fun getNotes(): Result<Exception, List<Note>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getNote(id: String): Result<Exception, Note?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteNote(note: Note): Result<Exception, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAll(): Result<Exception, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateAll(list: List<Note>): Result<Exception, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateNote(note: Note): Result<Exception, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}