package com.example.data

import com.example.data.entities.RoomNote
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.User
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExtTest {

    fun getNote(creationDate: String = "28/10/2018",
                contents: String = "When I understand that this glass is already broken, every moment with it becomes precious.",
                upVotes: Int = 0,
                imageUrl: String = "",
                creator: User? = User(
                    "8675309",
                    "Ajahn Chah",
                    ""
                )
    ) = Note(
        creationDate = creationDate,
        contents = contents,
        upVotes = upVotes,
        imageUrl = imageUrl,
        creator = creator
    )


    fun getRoomNote(creationDate: String = "28/10/2018",
                    contents: String = "When I understand that this glass is already broken, every moment with it becomes precious.",
                    upVotes: Int = 0,
                    imageUrl: String = "",
                    creator: String = "8675309"
    ) = RoomNote(
        creationDate = creationDate,
        contents = contents,
        upVotes = upVotes,
        imageUrl = imageUrl,
        creatorId = creator
    )



    @Test
    fun `test extension flat map` (){

        val notelist = listOf<Note>(getNote(),getNote(),getNote())

        val roomNoteList = listOf<RoomNote>(getRoomNote(),getRoomNote(),getRoomNote())

        val result = roomNoteList.toNoteList()

        assertTrue(result == notelist)
    }
}