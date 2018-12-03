package com.example.domain

import com.example.domain.repository.IAuthRepository
import com.example.domain.repository.INoteRepository

/**
 * Created by Festus Kiambi on 12/3/18.
 */

class ServiceLocator(val localAnon: INoteRepository,
                     val remoteReg: INoteRepository,
                     val cacheReg: INoteRepository,
                     val authRepository: IAuthRepository
)