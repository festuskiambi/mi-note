package com.example.domain.repository

import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result


/**
 * Created by Festus Kiambi on 12/11/18.
 */
interface ILocalNoteRepository{

    suspend fun getNotes(): Result<Exception,List<Note>>


    suspend fun getNote(id: String): Result<Exception,Note?>

    suspend fun deleteNote(note: Note): Result<Exception, Unit>

    suspend fun deleteAll(): Result<Exception, Unit>

    suspend fun updateAll(list: List<Note>): Result<Exception, Unit>

    suspend fun updateNote(note: Note): Result<Exception, Unit>
}