package com.example.data

import android.net.Uri
import com.example.data.entities.AnonymousRoomNote
import com.example.data.entities.RegisteredRoomNote
import com.example.data.entities.RegisteredRoomTransaction
import com.example.data.note.registered.FirebaseNote
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.NoteTransaction
import com.example.domain.domainmodel.TransactionType

import com.example.domain.domainmodel.User

/**
 * Created by Festus Kiambi on 12/4/18.
 */

internal val Note.safeGetUid: String
    get() = this.creator?.uid ?: ""

internal val NoteTransaction.safeGetUid: String
    get() = this.creator?.uid ?: ""

internal val Uri?.defaultIfEmpty: String
    get() = if (this.toString() == "" || this == null) "satellite_beam"
    else this.toString()


//"this" refers to the object upon which this extension property is called
internal val Note.toAnonymousNote: AnonymousRoomNote
    get() = AnonymousRoomNote(
        this.creationDate,
        this.contents,
        this.upVotes,
        this.imageUrl,
        this.safeGetUid
    )


internal val AnonymousRoomNote.toNote: Note
    get() = Note(
        this.creationDate,
        this.contents,
        this.upVotes,
        this.imageUrl,
        User(this.creatorId)
    )
//"this" refers to the object upon which this extension property is called
internal val Note.toRegistredNote: RegisteredRoomNote
    get() = RegisteredRoomNote(
        this.creationDate,
        this.contents,
        this.upVotes,
        this.imageUrl,
        this.safeGetUid
    )


internal val RegisteredRoomNote.toNote: Note
    get() = Note(
        this.creationDate,
        this.contents,
        this.upVotes,
        this.imageUrl,
        User(this.creatorId)
    )

//"this" refers to the object upon which this extension property is called
internal val NoteTransaction.toRegistredRoomTransaction: RegisteredRoomTransaction
    get() = RegisteredRoomTransaction(
        this.creationDate,
        this.contents,
        this.upVotes,
        this.imageUrl,
        this.safeGetUid,
        this.transactionType.value
    )


internal val RegisteredRoomTransaction.toNoteTransaction: NoteTransaction
    get() = NoteTransaction(
        this.creationDate,
        this.contents,
        this.upVotes,
        this.imageUrl,
        User(this.creatorId),
        this.transactionType.toTransactionType()
    )

internal val NoteTransaction.toNote: Note
    get() = Note(
        this.creationDate,
        this.contents,
        this.upVotes,
        this.imageUrl,
        User(this.safeGetUid)
    )

internal fun String.toTransactionType(): TransactionType {
    return if (this.equals(TransactionType.DELETE)) TransactionType.DELETE
    else TransactionType.UPDATE
}


internal val Note.toFirebaseNote: FirebaseNote
    get() = FirebaseNote(
        this.creationDate,
        this.contents,
        this.upVotes,
        this.imageUrl,
        this.safeGetUid
    )


internal val FirebaseNote.toNote: Note
    get() = Note(
        this.creationDate,
        this.contents,
        this.upVotes,
        this.imageurl,
        User(this.creator, "", "")
    )


internal fun List<AnonymousRoomNote>.toNoteListFromAnonymous(): List<Note> = this.flatMap {
    listOf(it.toNote)
}

internal fun List<RegisteredRoomNote>.toNoteListFromRegistered(): List<Note> = this.flatMap {
    listOf(it.toNote)
}

internal fun List<RegisteredRoomTransaction>.toNoteTransactionListFromRegistered(): List<NoteTransaction> = this.flatMap {
    listOf(it.toNoteTransaction)
}