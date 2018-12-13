package com.example.data.note.anonymous

import com.example.data.toAnonymousNote
import com.example.data.toNote
import com.example.data.toNoteListFromAnonymous
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result
import com.example.domain.error.MiNoteError
import com.example.domain.repository.ILocalNoteRepository

/**
 * Created by Festus Kiambi on 12/4/18.
 */

class RoomLocalAnonymousRepositoryImpl(private val noteDao: AnonymousNoteDao): ILocalNoteRepository{

    override suspend fun deleteAll(): Result<Exception, Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateAll(list: List<Note>): Result<Exception, Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getNotes(): Result<Exception, List<Note>> {
      return  Result.build { noteDao.getNotes().toNoteListFromAnonymous() }
    }

    override suspend fun getNote(id: String): Result<Exception, Note?> {
        return Result.build { noteDao.getNoteById(id).toNote }
    }

    override suspend fun deleteNote(note: Note): Result<Exception, Unit> {
        noteDao.deleteNote(note.toAnonymousNote)

        return Result.build { Unit}
    }

    override suspend fun updateNote(note: Note): Result<Exception, Unit> {
        val updated = noteDao.insertOrUpdate(note.toAnonymousNote)

        return when{
            updated == 0L -> Result.build { throw MiNoteError.LocalIOException }
            else -> Result.build { Unit }
        }
    }

}