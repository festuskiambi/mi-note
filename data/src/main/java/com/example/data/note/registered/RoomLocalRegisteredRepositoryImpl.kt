package com.example.data.note.registered

import com.example.data.*
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result
import com.example.domain.error.MiNoteError
import com.example.domain.repository.ILocalNoteRepository

/**
 * Created by Festus Kiambi on 12/4/18.
 */
class RoomLocalRegisteredRepositoryImpl(private val noteDao: RegisteredNoteDao): ILocalNoteRepository{
    override suspend fun deleteAll(): Result<Exception, Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateAll(list: List<Note>): Result<Exception, Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getNotes(): Result<Exception, List<Note>> {
        return  Result.build { noteDao.getNotes().toNoteListFromRegistered() }
    }

    override suspend fun getNote(id: String): Result<Exception, Note?> {
        return Result.build { noteDao.getNoteById(id).toNote }
    }

    override suspend fun deleteNote(note: Note): Result<Exception, Unit> {
        noteDao.deleteNote(note.toRegistredNote)

        return Result.build { Unit }
    }

    override suspend fun updateNote(note: Note): Result<Exception, Unit> {
        val updated = noteDao.insertOrUpdate(note.toRegistredNote)

        return when{
            updated == 0L -> Result.build { throw MiNoteError.LocalIOException }
            else -> Result.build { Unit }
        }
    }

}