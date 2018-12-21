package com.example.domain.repository

import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.NoteTransaction
import com.example.domain.domainmodel.Result


/**
 * Created by Festus Kiambi on 12/11/18.
 */

interface IRemoteNoteRepository{

    suspend fun synchronizeTransactions(transactions: List<NoteTransaction>): Result<Exception, Unit>

    suspend fun getNotes():Result<Exception, List<Note>>

    suspend fun getNote(id: String): Result<Exception, Note?>

    suspend fun deleteNote(note: Note): Result<Exception, Boolean>

    suspend fun updateNote(note: Note):Result<Exception, Boolean>
}