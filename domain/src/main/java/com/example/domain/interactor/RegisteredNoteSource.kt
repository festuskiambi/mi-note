package com.example.domain.interactor

import com.example.domain.DispatcherProvider
import com.example.domain.NoteServiceLocator
import com.example.domain.domainmodel.*

import com.example.domain.repository.IRemoteNoteRepository
import com.example.domain.repository.ITransactionRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope


/**
 * Created by Festus Kiambi on 12/3/18.
 */
class RegisteredNoteSource {

    suspend fun getNotes(
        locator: NoteServiceLocator,
        dispatcher: DispatcherProvider
    ): Result<Exception, List<Note>> {

        val transactionResult = locator.transactionReg.getTransactions()

        when (transactionResult) {
            is Result.Value -> {
                //if items exist in transaction cache
                if (transactionResult.value.size != 0) synchronizeTransactionCache(
                    transactionResult.value,
                    locator.remoteReg,
                    locator.transactionReg
                )
            }
            is Result.Error -> {

            }

        }

        return locator.remoteReg.getNotes()
    }

    private suspend fun synchronizeTransactionCache(
        transactions: List<NoteTransaction>,
        remoteReg: IRemoteNoteRepository,
        transactionReg: ITransactionRepository
    ) {
        val synchronizationResult = remoteReg.synchronizeTransactions(transactions)

        when (synchronizationResult) {
            is Result.Value -> transactionReg.deleteTransactions()
            is Result.Error -> {

            }
        }

    }

    suspend fun getNoteById(
        id: String,
        locator: NoteServiceLocator,
        dispatcher: DispatcherProvider
    ): Result<Exception, Note?> {

        return locator.remoteReg.getNote(id)
    }

    suspend fun updateNote(
        note: Note,
        locator: NoteServiceLocator,
        dispatcher: DispatcherProvider

    ): Result<Exception, Unit> {

        val remoteResult = locator.remoteReg.updateNote(note)

        return if (remoteResult is Result.Value) remoteResult
        else locator.transactionReg.updateTransactions(note.toTransaction(TransactionType.UPDATE))
    }

    suspend fun deleteNote(
        note: Note,
        locator: NoteServiceLocator,
        dispatcher: DispatcherProvider

    ): Result<Exception, Unit> {

        val remoteResult = locator.remoteReg.deleteNote(note)

        return if (remoteResult is Result.Value) remoteResult
        else locator.transactionReg.updateTransactions(note.toTransaction(TransactionType.DELETE))
    }
}