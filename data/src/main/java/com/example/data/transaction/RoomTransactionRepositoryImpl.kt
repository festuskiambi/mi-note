package com.example.data.transaction

import com.example.domain.domainmodel.NoteTransaction
import com.example.domain.domainmodel.Result
import com.example.domain.repository.ITransactionRepository
import java.lang.Exception

/**
 * Created by Festus Kiambi on 12/12/18.
 */
class RoomTransactionRepositoryImpl(): ITransactionRepository{
    override suspend fun getTransactions(): Result<Exception, List<NoteTransaction>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteTransactions(): Result<Exception, Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateTransactions(): Result<Exception, Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}