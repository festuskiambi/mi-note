package com.example.festus.mi_note

import com.example.domain.DispatcherProvider
import com.example.domain.ServiceLocator
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result
import com.example.domain.domainmodel.User
import com.example.domain.interactor.AnonymousNoteSource
import com.example.domain.interactor.AuthSource
import com.example.domain.interactor.PublicNoteSource
import com.example.domain.interactor.RegisteredNoteSource
import com.example.festus.mi_note.notedetail.INoteDetailContract
import com.example.festus.mi_note.notedetail.NoteDetailEvent
import com.example.festus.mi_note.notedetail.NoteDetailLogic
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Created by Festus Kiambi on 12/11/18.
 */

class NoteDetailLogicTest {

    private val dispatcher: DispatcherProvider = mockk()
    private val locator: ServiceLocator = mockk()
    private val vModel: INoteDetailContract.ViewModel = mockk(relaxed = true)
    private val view: INoteDetailContract.View = mockk(relaxed = true)
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
        locator,
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

    /**
     * When auth presses done, they are finished editing their note. They will be returned to a list
     * view of all notes. Depending on if the note isPrivate, and whether or not the user is
     * anonymous, will dictate where the note is written to.
     *
     * a. isPrivate: true, user: null
     * b. isPrivate: false, user: not null
     * c. isPrivate: true, user: not null
     *
     * 1. Check current user status: null (anonymous), isPrivate is beside the point if null user
     * 2. Create a copy of the note in vM, with updated "content" value
     * 3. exit to list activity upon completion
     */

    @Test
    fun`on done click private, not signed in`() = runBlocking {

        logic = getLogic()

        every {
            view.getNoteBody()
        } returns getNote().contents

        every {
            vModel.getNoteState()
        } returns getNote()

        coEvery {
            anonymous.updateNote(getNote(), locator, dispatcher)
        } returns Result.build { true }

        coEvery {
            auth.getCurrentUser(locator)
        } returns Result.build { null }

        //call the unit to be tested
        logic.event(NoteDetailEvent.OnDoneClick)

        //verify interactions and state if necessary

        verify { view.getNoteBody() }
        verify { vModel.getNoteState() }
        coVerify { auth.getCurrentUser(locator) }
        coVerify { anonymous.updateNote(getNote(), locator, dispatcher) }
        verify { view.startListFeature() }
    }

    @Test
    fun `on update click, signed in`() = runBlocking {

         logic = getLogic()
        every {
            vModel.getNoteState()
        }returns getNote()

        every{
            view.getNoteBody()
        }returns getNote().contents

        coEvery {
            auth.getCurrentUser(locator)
        }returns Result.build { getUser() }

        coEvery {
            registered.updateNote(getNote(),locator,dispatcher)
        }returns Result.build { true }

        logic.event(NoteDetailEvent.OnDoneClick)

        verify { view.getNoteBody() }
        verify { vModel.getNoteState() }
        coVerify { auth.getCurrentUser(locator) }
     //   coVerify { registered.updateNote(getNote(),locator,dispatcher) }
        verify { view.startListFeature() }
    }

    @Test
    fun `On start`() = runBlocking {
        logic = getLogic()

        every {
            vModel.getNoteState()
        } returns getNote()

        logic.event(NoteDetailEvent.OnStart)

        verify { vModel.getNoteState() }
        verify { view.setBackgroundImage(getNote().imageUrl) }
        verify { view.setDateLabel(getNote().creationDate) }
        verify { view.setNoteBody(getNote().contents) }
    }
}