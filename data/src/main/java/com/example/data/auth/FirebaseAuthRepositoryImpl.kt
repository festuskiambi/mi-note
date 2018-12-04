package com.example.data.auth

import com.example.domain.domainmodel.Result
import com.example.domain.domainmodel.User
import com.example.domain.error.MiNoteError
import com.example.domain.repository.IAuthRepository
import com.example.data.defaultIfEmpty

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.concurrent.TimeUnit

/**
 * Created by Festus Kiambi on 12/4/18.
 */

class FirebaseAuthRepositoryImpl: IAuthRepository{
    override suspend fun getCurrentUser(): Result<Exception, User?> {
        val firebaseUser = auth.currentUser

        if(firebaseUser == null) return Result.build { null }
        else return Result.build {
            User(
                firebaseUser.uid,
                firebaseUser.displayName ?: "",
                firebaseUser.photoUrl.defaultIfEmpty
            )
        }
    }

    override suspend fun signOutCurrentUser(): Result<Exception, Unit> {
        return  Result.build { auth.signOut() }
    }

    override suspend fun deleteCurrentUser(): Result<Exception, Boolean> {
        return try {
            val user = auth.currentUser ?: throw MiNoteError.AuthError

            val task =user.delete()

            Tasks.await(task,1000,TimeUnit.MILLISECONDS)

            if(task.isSuccessful) Result.build { true }
            else Result.build { throw MiNoteError.AuthError }


        }catch (exception: Exception){
            Result.build { throw exception }
        }
    }

    override suspend fun createGoogleUser(idToken: String): Result<Exception, Boolean> {

        val credential = GoogleAuthProvider.getCredential(idToken,null)

        return try {
            val task = auth.signInWithCredential(credential)

            Tasks.await(task,1000,TimeUnit.MILLISECONDS)

            if(task.isSuccessful) Result.build { true }
            else Result.build { throw MiNoteError.AuthError }

        }catch (exception: Exception){
            Result.build { throw exception }
        }
    }

    val auth: FirebaseAuth
    get() = FirebaseAuth.getInstance()

}