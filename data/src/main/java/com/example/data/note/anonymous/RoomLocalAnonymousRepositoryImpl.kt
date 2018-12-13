package com.example.data.note.anonymous

import com.example.data.note.registered.RegisteredNoteDao
import com.example.data.toRoomNote
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result
import com.example.domain.error.MiNoteError
import com.example.domain.repository.ILocalNoteRepository

/**
 * Created by Festus Kiambi on 12/4/18.
 */

class RoomLocalAnonymousRepositoryImpl(private val noteDao: RegisteredNoteDao): ILocalNoteRepository{

    override suspend fun deleteAll(): Result<Exception, Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateAll(list: List<Note>): Result<Exception, Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getNotes(): Result<Exception, List<Note>> {
      return  Result.build { noteDao.getNotes().toNoteList() }
    }

    override suspend fun getNote(id: String): Result<Exception, Note?> {
        return Result.build { noteDao.getNoteById(id).toNote }
    }

    override suspend fun deleteNote(note: Note): Result<Exception, Unit> {
        noteDao.deleteNote(note.toRoomNote)

        return Result.build { Unit}
    }

    override suspend fun updateNote(note: Note): Result<Exception, Unit> {
        val updated = noteDao.insertOrUpdate(note.toRoomNote)

        return when{
            updated == 0L -> Result.build { throw MiNoteError.LocalIOException }
            else -> Result.build { Unit }
        }
    }

}