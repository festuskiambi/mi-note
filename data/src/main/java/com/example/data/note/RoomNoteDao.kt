package com.example.data.note

import androidx.room.*
import com.example.data.entities.RoomNote

/**
 * Created by Festus Kiambi on 12/4/18.
 */

@Dao
interface RoomNoteDao{

    @Query("SELECT * FROM local_notes ORDER BY creation_date")
    fun getNotes(): List<RoomNote>

    @Query("SELECT * FROM local_notes WHERE creation_date = :creationDate ORDER BY creation_date")
    fun getNoteById(creationDate: String): RoomNote

    @Delete
    fun deleteNote(note: RoomNote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(note: RoomNote): Long
}