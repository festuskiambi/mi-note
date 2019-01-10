package com.example.festus.mi_note

import com.example.domain.DispatcherProvider
import com.example.domain.NoteServiceLocator
import com.example.domain.UserServiceLocator
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result
import com.example.domain.domainmodel.User
import com.example.domain.interactor.AnonymousNoteSource
import com.example.domain.interactor.AuthSource
import com.example.domain.interactor.PublicNoteSource
import com.example.domain.interactor.RegisteredNoteSource
import com.example.festus.mi_note.common.MODE_PRIVATE
import com.example.festus.mi_note.notelist.INoteListContract
import com.example.festus.mi_note.notelist.NoteListAdapter
import com.example.festus.mi_note.notelist.NoteListEvent
import com.example.festus.mi_note.notelist.NoteListLogic
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
    private val adapter: NoteListAdapter = mockk(relaxed = true)
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

    /**
     * New notes can be created when in two states either private or public mode
     *
     * */

    @Test
    fun `on new note click in private mode`() {
        every { vModel.getIsPrivate() } returns true

        logic.event(NoteListEvent.OnNewNoteClick)

        verify { navigator.startNoteDetailFeatureWithExtras("", true) }
    }

    @Test
    fun `on new note click in public mode`() {

        every { vModel.getIsPrivate() } returns false

        logic.event(NoteListEvent.OnNewNoteClick)

        verify { navigator.startNoteDetailFeatureWithExtras("", isPrivate = false) }
    }

    /**
     * on bind view is called by the view oncreate and the user can be either anonymous or registered,
     * save the user state in the vModel and make initializations
     *
     * */
    @Test
    fun `onBind event with an anonymous user`() = runBlocking {

        coEvery { auth.getCurrentUser(userLocater) } returns Result.build { null }

        logic.event(NoteListEvent.OnBind)

        coVerify { auth.getCurrentUser(userLocater) }
        verify { vModel.setUserState(null) }
        verify { view.showLoadingView() }
        verify { view.setToolbarTitle(MODE_PRIVATE) }
        verify { adapter.logic = logic }
    }

    @Test
    fun `onBind event with a registered user`() = runBlocking {
        val user = getUser()
        coEvery { auth.getCurrentUser(userLocater) } returns Result.build {
            user
        }

        logic.event(NoteListEvent.OnBind)

        coVerify { auth.getCurrentUser(userLocater) }
        verify { vModel.setUserState(user) }
        verify { view.showLoadingView() }
        verify { view.setAdapter(adapter) }
        verify { view.setToolbarTitle(MODE_PRIVATE) }
        verify { adapter.logic = logic }
    }

    /**
     * on start is called to render the ui. the possible scenarios include     *
     * a.user is in private mode either registered or anonymous
     * b.user is in public mode(registered only)     *
     *
     * */

    @Test
    fun `on start with anonymous user in private mode`() = runBlocking {
        every { vModel.getIsPrivate() } returns true
        every { vModel.getUserState() } returns null
        coEvery { anonymous.getNotes(noteLocator,dispatcher) } returns Result.build {
            getNoteList
        }

        logic.event(NoteListEvent.OnStart)
        verify { vModel.getIsPrivate() }
        verify { vModel.getUserState() }
        verify { view.showNoteList() }
        verify { adapter.submitList(getNoteList) }
        coVerify { anonymous.getNotes(noteLocator,dispatcher) }
    }

    @Test
    fun `on start with registered user in private mode` () = runBlocking {
        every { vModel.getIsPrivate() } returns true
        every { vModel.getUserState() } returns getUser()
        coEvery { registered.getNotes(noteLocator) } returns Result.build {
            getNoteList
        }

        logic.event(NoteListEvent.OnStart)

        verify { vModel.getIsPrivate() }
        verify { vModel.getUserState() }
        verify { view.showNoteList() }
        verify { adapter.submitList(getNoteList) }
        coVerify { registered.getNotes(noteLocator) }
    }

    @Test
    fun `on start in private mode with empty note list  `() = runBlocking {
        every { vModel.getIsPrivate()} returns true
        every { vModel.getUserState() } returns getUser()
        coEvery { registered.getNotes(noteLocator) } returns Result.build { emptyList<Note>() }

        logic.event(NoteListEvent.OnStart)

        verify { vModel.getIsPrivate() }
        verify { vModel.getUserState() }
        verify { view.showEmptyState() }
        coVerify { registered.getNotes(noteLocator) }

    }


    @Test
    fun `on start in public mode` () = runBlocking {
        every { vModel.getIsPrivate() } returns false
        coEvery { public.getNotes(noteLocator,dispatcher) } returns Result.build {
            getNoteList
        }

        logic.event(NoteListEvent.OnStart)

        verify { vModel.getIsPrivate() }
        verify { view.showNoteList() }
        verify { adapter.submitList(getNoteList) }
        coVerify { public.getNotes(noteLocator,dispatcher) }
    }

    /**
     * when the login button is clicked start the login feature
     *
     * */

    @Test
    fun `on login click` (){

        logic.event(NoteListEvent.OnLoginClick)

        verify { navigator.startLoginFeature() }
    }

    /**
     * a note item can be clicked when in private mode or public mode.
     * when its clicked start the notedetail feature
     *      *
     * */

    @Test
    fun `on a note item  click in private mode ` ()  {
        every { vModel.getIsPrivate() } returns true
        every { vModel.getAdapterState() } returns getNoteList

        val clickEvent = NoteListEvent.OnNoteItemClick(0)

        logic.event(clickEvent)
        verify { vModel.getIsPrivate() }
        verify { navigator.startNoteDetailFeatureWithExtras(getNote().creationDate,true) }
        verify { vModel.getAdapterState() }
    }

    @Test
    fun `on a note item  click in public mode ` ()  {
        every { vModel.getIsPrivate() } returns false
        every { vModel.getAdapterState() } returns getNoteList

        val clickEvent = NoteListEvent.OnNoteItemClick(0)

        logic.event(clickEvent)
        verify { vModel.getIsPrivate() }
        verify { navigator.startNoteDetailFeatureWithExtras(getNote().creationDate,false) }
        verify { vModel.getAdapterState() }
    }


}


