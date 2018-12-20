package com.example.domain

import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.NoteTransaction
import com.example.domain.domainmodel.TransactionType
import com.example.domain.domainmodel.User
import com.example.domain.interactor.RegisteredNoteSource
import com.example.domain.repository.IRemoteNoteRepository
import com.example.domain.repository.ITransactionRepository
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.BeforeEach

/**
 * Created by Festus Kiambi on 12/3/18.
 */
class  RegisteredNoteSourceTest{

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
        transactiontype :TransactionType = TransactionType.DELETE
    ) = NoteTransaction(
        creationDate = creationDate,
        contents =  contents,
        upVotes =  upVotes,
        imageUrl = imageUrl,
        creator = creator,
        transactionType = transactiontype
    )

    @BeforeEach
    fun setUp(){
        clearMocks()
        every { dispatcher.provideIOContext() } returns Dispatchers.Unconfined
    }


}