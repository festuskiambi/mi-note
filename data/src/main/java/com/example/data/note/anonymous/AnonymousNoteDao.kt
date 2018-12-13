package com.example.data.note.anonymous

import androidx.room.*
import com.example.data.entities.AnonymousRoomNote

/**
 * Created by Festus Kiambi on 12/4/18.
 */

@Dao
interface AnonymousNoteDao {
    @Query("SELECT * FROM anonymous_notes ORDER BY creation_date")
    fun getNotes(): List<AnonymousRoomNote>

    @Query("SELECT * FROM anonymous_notes WHERE creation_date = :creationDate ORDER BY creation_date")
    fun getNoteById(creationDate: String): AnonymousRoomNote

    @Delete
    fun deleteNote(note: AnonymousRoomNote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(note: AnonymousRoomNote): Long
}