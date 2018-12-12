package com.example.data.note.registered

import com.example.data.note.RoomNoteDao
import com.example.data.toNote
import com.example.data.toNoteList
import com.example.data.toRoomNote
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result
import com.example.domain.error.MiNoteError
import com.example.domain.repository.INoteRepository

/**
 * Created by Festus Kiambi on 12/4/18.
 */
class RoomLocalRegisteredRepositoryImpl(private val noteDao: RoomNoteDao): INoteRepository{

    override suspend fun getNotes(): Result<Exception, List<Note>> {
        return  Result.build { noteDao.getNotes().toNoteList() }
    }

    override suspend fun getNote(id: String): Result<Exception, Note?> {
        return Result.build { noteDao.getNoteById(id).toNote }
    }

    override suspend fun deleteNote(note: Note): Result<Exception, Boolean> {
        noteDao.deleteNote(note.toRoomNote)

        return Result.build { true }
    }

    override suspend fun updateNote(note: Note): Result<Exception, Boolean> {
        val updated = noteDao.insertOrUpdate(note.toRoomNote)

        return when{
            updated == 0L -> Result.build { throw MiNoteError.LocalIOException }
            else -> Result.build { true }
        }
    }

}