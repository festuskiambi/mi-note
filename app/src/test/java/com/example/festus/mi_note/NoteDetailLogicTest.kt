package com.example.festus.mi_note

import com.example.domain.DispatcherProvider
import com.example.domain.ServiceLocator
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.User
import com.example.domain.interactor.AnonymousNoteSource
import com.example.domain.interactor.AuthSource
import com.example.domain.interactor.PublicNoteSource
import com.example.domain.interactor.RegisteredNoteSource
import com.example.festus.mi_note.notedetail.INoteDetailContract
import com.example.festus.mi_note.notedetail.NoteDetailEvent
import com.example.festus.mi_note.notedetail.NoteDetailLogic
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Created by Festus Kiambi on 12/11/18.
 */

class NoteDetailLogicTest {

    private val dispatcher: DispatcherProvider = mockk()
    private val locater: ServiceLocator = mockk()
    private val vModel: INoteDetailContract.ViewModel = mockk(relaxed = true)
    private val view: INoteDetailContract.View = mockk()
    private val anonymous: AnonymousNoteSource = mockk()
    private val registered: RegisteredNoteSource = mockk()
    private val public: PublicNoteSource = mockk()
    private val auth: AuthSource = mockk()

    private lateinit var logic: NoteDetailLogic

    fun getNote(
        creationDate: String = "09:40:30, December 11th, 2018",
        contents: String = "When I understand that this glass is already broken,every moment with it becomes precious.",
        upVotes: Int = 0,
        imageUrl: String = "",
        creator: User? = User(
            "8675309",
            "Ajahn Chah",
            "satellite_beam"
        )
    ) = Note(
        creationDate = creationDate,
        contents = contents,
        upVotes = upVotes,
        imageUrl = imageUrl,
        creator = creator
    )

    fun getUser(
        uid: String = "8675309",
        name: String = "Ajahn Chah",
        profilePicUrl: String = ""

    ) = User(
        uid,
        name,
        profilePicUrl
    )

    fun getLogic(
        id: String = getNote().creationDate,
        isPrivate: Boolean = true
    ) = NoteDetailLogic(
        dispatcher,
        locater,
        vModel,
        view,
        anonymous,
        registered,
        public,
        auth,
        id,
        isPrivate
    )

    @BeforeEach
    fun clear(){
        clearMocks()

        every {
            dispatcher.provideUIContext()
        }returns Dispatchers.Unconfined
    }

    @Test
    fun `on start`() = runBlocking {
        logic = getLogic()

        every{
            vModel.getNoteState()
        }returns getNote()

        logic.event(NoteDetailEvent.onStart)

        verify { vModel.getNoteState() }
        verify{ view.setBackgroundImage(getNote().imageUrl)}
        verify{ view.setNoteBody(getNote().contents)}
        verify { view.setDateLabel(getNote().creationDate) }

    }
}