package com.example.domain.repository

import com.example.domain.domainmodel.User
import com.example.domain.domainmodel.Result


/**
 * Created by Festus Kiambi on 12/3/18.
 */
interface IAuthRepository {

    suspend fun getCurrentUser(): Result<Exception, User?>

    suspend fun signOutCurrentUser(): Result<Exception, Unit>

    suspend fun deleteCurrentUser(): Result<Exception, Boolean>

    suspend fun createGoogleUser(idToken: String): Result<Exception, Boolean>

}