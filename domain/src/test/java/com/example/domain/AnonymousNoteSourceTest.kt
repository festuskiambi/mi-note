package com.example.domain

import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.Result
import com.example.domain.domainmodel.User
import com.example.domain.error.MiNoteError
import com.example.domain.interactor.AnonymousNoteSource
import com.example.domain.repository.ILocalNoteRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created by Festus Kiambi on 12/3/18.
 */
class AnonymousNoteSourceTest{

    val anonymousNoteSource = AnonymousNoteSource()

    val locator: ServiceLocator = mockk()

    val dispatcher: DispatcherProvider = mockk()

    val noteRepo: ILocalNoteRepository = mockk()

    //Shout out to Philipp Hauer @philipp_hauer for the snippet below (creating test data) with
    //a default argument wrapper function:
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


    @BeforeEach
    fun setUpRedundantMocks(){
        clearMocks()
        every { dispatcher.provideIOContext() } returns Dispatchers.Unconfined
    }

    @Test
    fun ` on get notes successful` () = runBlocking{
        val testList = listOf(getNote(),getNote(),getNote())

        every { locator.localAnon } returns noteRepo

        coEvery{noteRepo.getNotes()} returns Result.build { testList }

        val reusult: Result<Exception, List<Note>> = anonymousNoteSource.getNotes(locator,dispatcher)

        verify { dispatcher.provideIOContext() }
        verify { locator.localAnon }
        coVerify { noteRepo.getNotes() }


        if(reusult is Result.Value) assertEquals(reusult.value,testList)
        else assertTrue { false }
    }

    @Test
    fun `on get notes Error` () = runBlocking {

        every { locator.localAnon } returns noteRepo

        coEvery { noteRepo.getNotes() } returns Result.build { throw MiNoteError.LocalIOException }

        val result: Result<Exception, List<Note>> = anonymousNoteSource.getNotes(locator,dispatcher)

        verify { dispatcher.provideIOContext() }
        verify { locator.localAnon }
        coVerify { noteRepo.getNotes() }

        assert(result is Result.Error)
    }

    @Test
    fun `on get note successfull` () = runBlocking {

        val testNote = getNote()

        every { locator.localAnon } returns noteRepo
        coEvery { noteRepo.getNote(testNote.creationDate) } returns Result.build { testNote }

        val result: Result<Exception, Note?> = anonymousNoteSource.getNoteById(testNote.creationDate,locator,dispatcher)

        verify { dispatcher.provideIOContext() }
        verify { locator.localAnon }
        coVerify { noteRepo.getNote(testNote.creationDate) }

        if(result is Result.Value) assertEquals(result.value,testNote)
        else assertTrue { false }
    }

    @Test
    fun `on get note error` () = runBlocking {
         val  testId = getNote().creationDate
        every { locator.localAnon } returns noteRepo
        coEvery{(noteRepo.getNote(testId)) } returns Result.build { throw MiNoteError.LocalIOException }

        val result: Result<Exception,Note?> = anonymousNoteSource.getNoteById(testId,locator,dispatcher)

        assert(result is Result.Error)
    }

    @Test
    fun `on update note successful` () = runBlocking {
        val testNote = getNote()
        every { locator.localAnon } returns noteRepo
        coEvery { noteRepo.updateNote(testNote) } returns Result.build { Unit }

        val result: Result<Exception, Unit> = anonymousNoteSource.updateNote(testNote,locator,dispatcher)

        verify { dispatcher.provideIOContext() }
        verify { locator.localAnon }
        coVerify { noteRepo.updateNote(testNote) }

//        if(result is Result.Value) assert(result.value)
//        else assertTrue { false }
    }

    @Test
    fun `on update note errror` () = runBlocking {

        val testNote = getNote()

        every{locator.localAnon} returns noteRepo
        coEvery { noteRepo.updateNote(testNote) } returns Result.build { throw MiNoteError.LocalIOException }

        val result: Result<Exception,Unit> = anonymousNoteSource.updateNote(testNote,locator,dispatcher)

       assert(result is Result.Error)

    }

    @Test
    fun `on delete note succesful` () = runBlocking {

        val testNote = getNote()

        every { locator.localAnon } returns noteRepo
        coEvery { noteRepo.deleteNote(testNote) } returns Result.build { Unit }

        val result: Result<Exception, Unit> = anonymousNoteSource.deleteNote(testNote,locator,dispatcher)

        verify { dispatcher.provideIOContext() }
        verify { locator.localAnon }
        coVerify { noteRepo.deleteNote(testNote) }

//        if(result is Result.Value) assert(result.value)
//        else assertTrue { false }
    }

    @Test
    fun `on delete note error` ()= runBlocking {

        val testNote = getNote()

        every { locator.localAnon } returns noteRepo
        coEvery { noteRepo.deleteNote(testNote) } returns Result.build { throw MiNoteError.LocalIOException }

        val result: Result<Exception,Unit>  = anonymousNoteSource.deleteNote(testNote,locator,dispatcher)

        assert(result is Result.Error)
    }
}