package com.example.domain.repository

import com.example.domain.domainmodel.NoteTransaction
import com.example.domain.domainmodel.Result

import java.lang.Exception

/**
 * Created by Festus Kiambi on 12/11/18.
 */
interface ITransactionRepository {

    suspend fun getTransactions(): Result<Exception, List<NoteTransaction>>

    suspend fun deleteTransactions(): Result<Exception, Unit>

    suspend fun updateTransactions(): Result<Exception, Unit>
}