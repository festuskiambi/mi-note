package com.example.domain

import com.example.domain.repository.IAuthRepository
import com.example.domain.repository.INoteRepository
import com.example.domain.repository.ITransactionRepository

/**
 * Created by Festus Kiambi on 12/3/18.
 */

class ServiceLocator(val localAnon: INoteRepository,
                     val remoteReg: INoteRepository,
                     val transactionReg: ITransactionRepository,
                     val authRepository: IAuthRepository
)