package com.example.domain

import com.example.domain.domainmodel.*
import com.example.domain.error.MiNoteError
import com.example.domain.interactor.RegisteredNoteSource
import com.example.domain.repository.IRemoteNoteRepository
import com.example.domain.repository.ITransactionRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created by Festus Kiambi on 12/3/18.
 */
class RegisteredNoteSourceTest {

    val locatorNote: NoteServiceLocator = mockk()
    val source = RegisteredNoteSource()
    val noteRepository: IRemoteNoteRepository = mockk()
    val transactionRepository: ITransactionRepository = mockk()

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
        creationDate = creationDate,
        contents = contents,
        upVotes = upVotes,
        imageUrl = imageUrl,
        creator = creator
    )

    fun getTransaction(
        creationDate: String = "28/10/2018",
        contents: String = "When I understand that this glass is already broken, every moment with it becomes precious.",
        upVotes: Int = 0,
        imageUrl: String = "",
        creator: User? = User(
            "8675309",
            "Ajahn Chah",
            ""
        ),
        transactiontype: TransactionType = TransactionType.DELETE
    ) = NoteTransaction(
        creationDate = creationDate,
        contents = contents,
        upVotes = upVotes,
        imageUrl = imageUrl,
        creator = creator,
        transactionType = transactiontype
    )

    @BeforeEach
    fun setUp() {
        clearMocks()
    }


    /**
     * when requesting notes this steps should occur
     * 1. check local transaction cache for any transactions
     * 2. if no transactions found get notes from remote storage
     * 3.if transactions are found in the transaction cache synch transactions in the cache with the remote storage
     * and get latest notes from the remote storage
     *
     * */

    @Test
    fun `on get notes with empty transactions`() = runBlocking {

        val testNotes = listOf(getNote(), getNote(), getNote())

        every { locatorNote.transactionReg } returns transactionRepository

        every { locatorNote.remoteReg } returns noteRepository

        coEvery { transactionRepository.getTransactions() } returns Result.build {
            emptyList<NoteTransaction>()
        }

        coEvery { noteRepository.getNotes() } returns Result.build {
            testNotes
        }

        val result = source.getNotes(locatorNote)

        coVerify { transactionRepository.getTransactions() }
        coVerify { noteRepository.getNotes() }

        if (result is Result.Value) assertEquals(result.value, testNotes)
        else assertTrue { false }

    }

    /**
     * if transactions found synch with remote and clear transactions cache
     * */

    @Test
    fun `on get notes with transactions`() = runBlocking {

        val testNotes = listOf(getNote(), getNote(), getNote())
        val testTransactions = listOf(getTransaction(), getTransaction(), getTransaction())

        every { locatorNote.remoteReg } returns noteRepository

        every { locatorNote.transactionReg } returns transactionRepository

        coEvery { transactionRepository.getTransactions() } returns Result.build {
            testTransactions
        }

        coEvery { noteRepository.synchronizeTransactions(testTransactions) } returns Result.build {
            Unit
        }

        coEvery { noteRepository.getNotes() } returns Result.build {
            testNotes
        }

        coEvery { transactionRepository.deleteTransactions() } returns Result.build {
            Unit
        }

        val result = source.getNotes(locatorNote)

        coVerify { noteRepository.getNotes() }
        coVerify { transactionRepository.getTransactions() }
        coVerify { transactionRepository.deleteTransactions() }
        coVerify { noteRepository.synchronizeTransactions(testTransactions) }

        if (result is Result.Value) assertEquals(result.value, testNotes)
        else assertTrue { false }
    }

    @Test
    fun `on get note by id success`() = runBlocking {

        val testId = getNote().creationDate


        every { locatorNote.remoteReg } returns noteRepository

        coEvery { noteRepository.getNote(testId) } returns Result.build {
            getNote()
        }

        val result = source.getNoteById(testId, locatorNote)

        coVerify { noteRepository.getNote(testId) }

        if (result is Result.Value) assertEquals(result.value, getNote())
        else assertTrue { false }
    }

    @Test
    fun `on get note by id fail`() = runBlocking {

        val testId = getNote().creationDate

        every { locatorNote.remoteReg } returns noteRepository

        coEvery { noteRepository.getNote(testId) } returns Result.build {
            throw MiNoteError.RemoteIOException
        }

        val result = source.getNoteById(testId, locatorNote)

        coVerify { noteRepository.getNote(testId) }

        assertTrue { result is Result.Error }
    }

    @Test
    fun `on update note success`() = runBlocking {

        val testNote = getNote()

        every { locatorNote.remoteReg } returns noteRepository

        coEvery { noteRepository.updateNote(testNote) } returns Result.build {
            Unit
        }

        val result = source.updateNote(testNote, locatorNote)

        coVerify { noteRepository.updateNote(testNote) }

        if (result is Result.Value) assertTrue { true }
        else assertTrue { false }
    }

    /**
     * if a request to update a note fails  throw an error and register the transaction in the transaction cache
     * */

    @Test
    fun `on update note fail and register transaction success` () = runBlocking {

        val testNote = getNote()
        val testTransaction = testNote.toTransaction(TransactionType.UPDATE)

        every { locatorNote.remoteReg  } returns noteRepository
        every { locatorNote.transactionReg } returns transactionRepository

        coEvery { noteRepository.updateNote(testNote) } returns Result.build {
            throw  MiNoteError.RemoteIOException
        }

        coEvery { transactionRepository.updateTransactions(testTransaction) } returns Result.build {
            Unit
        }

        val result = source.updateNote(testNote,locatorNote)

        coVerify { noteRepository.updateNote(testNote) }
        coVerify { transactionRepository.updateTransactions(testTransaction) }

        if (result is Result.Value) assertTrue { true }
        else assertTrue { false }
    }

    /**
     * if a request to update a note fails and also a request to register  a transaction fails throw an error
     * for both requests
     * */

    @Test
    fun `on update note fail and register transaction fail` () = runBlocking {
        val testNote = getNote()
        val testTransaction = testNote.toTransaction(TransactionType.UPDATE)

        every { locatorNote.remoteReg  } returns noteRepository
        every { locatorNote.transactionReg } returns transactionRepository

        coEvery { noteRepository.updateNote(testNote) } returns Result.build {
            throw  MiNoteError.RemoteIOException
        }

        coEvery { transactionRepository.updateTransactions(testTransaction) } returns Result.build {
            throw MiNoteError.TransactionError
        }

        val result = source.updateNote(testNote,locatorNote)

        coVerify { noteRepository.updateNote(testNote) }
        coVerify { transactionRepository.updateTransactions(testTransaction) }

        assertTrue { result is Result.Error }

    }
}