package com.example.domain.interactor

import com.example.domain.ServiceLocator
import com.example.domain.domainmodel.Result
import com.example.domain.domainmodel.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * Created by Festus Kiambi on 12/3/18.
 */

class AuthSource {

    suspend fun getCurrentUser(locator: ServiceLocator): Result<Exception, User?> {
        return locator.authRepository.getCurrentUser()
    }

    suspend fun deleteCurrentUser(locator: ServiceLocator): Result<Exception, Boolean> = runBlocking {

        val result = async(Dispatchers.IO) {
            locator.authRepository.deleteCurrentUser()
        }

        result.await()
    }

    suspend fun signOutCurrentUser(locator: ServiceLocator): Result<Exception, Unit> {
        return locator.authRepository.signOutCurrentUser()
    }

    suspend fun createGoogleUser(idToken: String, locator: ServiceLocator): Result<Exception, Boolean> {
        return locator.authRepository.createGoogleUser(idToken)
    }

}