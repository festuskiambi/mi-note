package com.example.domain

import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.NoteTransaction
import com.example.domain.domainmodel.Result
import com.example.domain.domainmodel.TransactionType
import com.example.domain.domainmodel.User
import com.example.domain.interactor.RegisteredNoteSource
import com.example.domain.repository.IRemoteNoteRepository
import com.example.domain.repository.ITransactionRepository
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
class RegisteredNoteSourceTest {

    val dispatcher: DispatcherProvider = mockk()
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
        every { dispatcher.provideIOContext() } returns Dispatchers.Unconfined
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

        val result = source.getNotes(locatorNote,dispatcher)

        coVerify { transactionRepository.getTransactions() }
        coVerify { noteRepository.getNotes()}

        if(result is Result.Value) assertEquals(result.value,testNotes)
        else assertTrue { false }

    }

    /**
     * if transactions found synch with remote and clear transactions cache
     * */

    @Test
    fun `on get notes with transactions` () = runBlocking {

        val testNotes = listOf(getNote(),getNote(),getNote())
        val testTransactions = listOf(getTransaction(),getTransaction(),getTransaction())

        every { locatorNote.remoteReg } returns noteRepository

        every {locatorNote.transactionReg} returns transactionRepository

        coEvery { transactionRepository.getTransactions() } returns Result.build {
            testTransactions
        }

        coEvery { noteRepository.synchronizeTransactions(testTransactions) } returns Result.build {
            Unit
        }

        coEvery { noteRepository.getNotes() }returns  Result.build {
            testNotes
        }

        coEvery { transactionRepository.deleteTransactions() } returns Result.build {
            Unit
        }

        val result = source.getNotes(locatorNote,dispatcher)

        coVerify { noteRepository.getNotes() }
        coVerify { transactionRepository.getTransactions() }
        coVerify { transactionRepository.deleteTransactions() }
        coVerify { noteRepository.synchronizeTransactions(testTransactions) }

        if(result is Result.Value) assertEquals(result.value,testNotes)
        else assertTrue { false }

    }
}