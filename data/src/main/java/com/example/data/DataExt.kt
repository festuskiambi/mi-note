package com.example.data

import android.net.Uri
import com.example.data.entities.RoomNote
import com.example.data.note.registered.FirebaseNote
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.User

/**
 * Created by Festus Kiambi on 12/4/18.
 */

internal val Note.safeGetUid: String
    get() = this.creator?.uid ?: ""

internal val Uri?.defaultIfEmpty: String
    get() = if (this.toString() == "" || this == null) "satellite_beam"
    else this.toString()


//"this" refers to the object upon which this extension property is called
internal val Note.toRoomNote: RoomNote
    get() = RoomNote(
        this.creationDate,
        this.contents,
        this.upVotes,
        this.imageUrl,
        this.safeGetUid
    )


internal val RoomNote.toNote: Note
    get() = Note(
        this.creationDate,
        this.contents,
        this.upVotes,
        this.imageUrl,
        User(this.creatorId,"","")
    )

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
        User(this.creator,"","")
    )


internal fun List<RoomNote>.toNoteList(): List<Note> = this.flatMap {
    listOf(it.toNote)

}