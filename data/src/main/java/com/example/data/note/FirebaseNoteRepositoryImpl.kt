package com.example.data.note

import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result
import com.example.domain.repository.INoteRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Festus Kiambi on 12/4/18.
 */
class FirebaseNoteRepositoryImpl : INoteRepository{

    fun getTable(): DatabaseReference = FirebaseDatabase.getInstance().getReference("remote_notes")

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

data class FirebaseNote(
    val creationDate: String,
    val contents: String,
    val upVotes: Int,
    val imageurl: String,
    val creator: String
)