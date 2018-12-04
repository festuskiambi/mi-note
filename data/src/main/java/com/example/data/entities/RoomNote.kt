package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Festus Kiambi on 12/4/18.
 */

@Entity(
    tableName = "local_notes",
    indices = [Index("creation_date")]
)
data class RoomNote(

    @PrimaryKey
    @ColumnInfo(name = "creation_date")
    val creationDate: String,

    @ColumnInfo(name = "contents")
    val contents: String,

    @ColumnInfo(name = "up_votes")
    val upVotes: Int,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "creator_id")
    val creatorId: String

)