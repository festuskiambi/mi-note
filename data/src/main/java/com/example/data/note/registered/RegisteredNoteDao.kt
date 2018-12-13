package com.example.data.note.registered

import androidx.room.*
import com.example.data.entities.RegisteredRoomNote
import com.example.data.entities.RoomNote

/**
 * Created by Festus Kiambi on 12/4/18.
 */

@Dao
interface RegisteredNoteDao {

    @Query("SELECT * FROM registered_notes ORDER BY creation_date")
    fun getNotes(): List<RegisteredRoomNote>

    @Query("SELECT * FROM local_notes WHERE creation_date = :creationDate ORDER BY creation_date")
    fun getNoteById(creationDate: String): RoomNote

    @Delete
    fun deleteNote(note: RoomNote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(note: RoomNote): Long
}