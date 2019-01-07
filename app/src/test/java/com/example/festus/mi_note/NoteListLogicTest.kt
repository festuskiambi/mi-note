package com.example.festus.mi_note

import com.example.domain.DispatcherProvider
import com.example.domain.NoteServiceLocator
import com.example.domain.UserServiceLocator
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.User
import com.example.domain.interactor.AnonymousNoteSource
import com.example.domain.interactor.AuthSource
import com.example.domain.interactor.PublicNoteSource
import com.example.domain.interactor.RegisteredNoteSource
import com.example.festus.mi_note.notelist.INoteListContract
import com.example.festus.mi_note.notelist.NoteListAdapter
import com.example.festus.mi_note.notelist.NoteListLogic
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.BeforeEach

/**
 * Created by Festus Kiambi on 12/21/18.
 */

class NoteListLogicTest {

    private val dispatcher: DispatcherProvider = mockk()
    private val noteLocator: NoteServiceLocator = mockk()
    private val userLocater: UserServiceLocator = mockk()
    private val vModel: INoteListContract.ViewModel = mockk(relaxed = true)
    private val view: INoteListContract.View = mockk(relaxed = true)
    private val navigator: INoteListContract.Navigator = mockk(relaxed = true)
    private val adapter: NoteListAdapter = mockk()
    private val anonymous: AnonymousNoteSource = mockk()
    private val registered: RegisteredNoteSource = mockk()
    private val public: PublicNoteSource = mockk()
    private val auth: AuthSource = mockk()


    fun getNote(
        creationDate: String = "28/10/2018",
        contents: String = "When I understand that this glass is already broken, every moment with it becomes precious.",
        upVotes: Int = 0,
        imageUrl: String = "",
        creator: User? = User(
            "8675309",
            "Ajahn Chah",
            ""
        )

    ) = Note(
        creationDate,
        contents,
        upVotes,
        imageUrl,
        creator
    )

    fun getUser(
        uid: String = "8675309",
        name: String = "Ajahn Chah",
        profilePicUrl: String = ""
    ) = User(uid, name, profilePicUrl)

   private val getNoteList = listOf(getNote(), getNote(), getNote())

    private val logic = NoteListLogic(
        dispatcher,
        userLocater,
        noteLocator,
        view,
        adapter,
        vModel,
        navigator,
        anonymous,
        registered,
        public,
        auth
    )


    @BeforeEach
    fun setUp() {
        clearMocks()
        every { dispatcher.provideUIContext() } returns Dispatchers.Unconfined

    }

}


