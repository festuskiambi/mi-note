package com.example.festus.mi_note.login

import com.example.domain.DispatcherProvider
import com.example.domain.ServiceLocator
import com.example.domain.interactor.AuthSource
import com.example.domain.domainmodel.Result
import com.example.festus.mi_note.common.BaseLogic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Festus Kiambi on 12/10/18.
 */
class LoginLogic(
    dispatcher: DispatcherProvider,
    locator: ServiceLocator,
    val view: ILoginContract.View,
    val authSource: AuthSource
) : BaseLogic(dispatcher, locator), CoroutineScope, ILoginContract.Logic {

    init {
        jobTracker = Job()
    }

    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker

    override fun event(event: LoginEvent<LoginResult>) {

        when (event) {
            is LoginEvent.OnStart -> onStart()
            is LoginEvent.OnDestroy -> onDestroy()
            is LoginEvent.OnBackClick -> onBackClick()
            is LoginEvent.OnAuthButtonClick -> onAuthButtonClick()
            is LoginEvent.OnGoogleSignInResult -> onSignInResult(event.result)
        }
    }

    private fun onSignInResult(result: LoginResult) = launch {
        if (result.requestCode == RC_SIGN_IN && result.account != null) {

            val createGoogleUserResult = authSource.createGoogleUser(
                result.account.idToken!!,
                locator
            )

            when (createGoogleUserResult) {
                is Result.Value -> onStart()
                is Result.Error -> handleError(createGoogleUserResult.error)
            }
        } else {
            renderErrorState(ERROR_AUTH)
        }

    }

    private fun onAuthButtonClick() = launch {
        val authResult = authSource.getCurrentUser(locator)

        when (authResult) {
            is Result.Value -> {
                if (authResult.value == null) view.startSignInInFlow()
                else signOutUser()
            }
            is Result.Error -> {
                handleError(authResult.error)
            }

        }
    }

    private suspend fun signOutUser() {
        val signOutResult = authSource.signOutCurrentUser(locator)

        when (signOutResult) {
            is Result.Value -> renderNullUser()
            is Result.Error -> renderErrorState(ERROR_AUTH)
        }
    }

    private fun onBackClick() {
        view.StartListFeature()
    }

    private fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onStart() = launch {
        val authResult = authSource.getCurrentUser(locator)

        when (authResult) {
            is Result.Value -> {
                if (authResult.value == null) renderNullUser()
                else renderActiveUser()
            }

            is Result.Error -> handleError(authResult.error)
        }
    }

    private fun handleError(error: Exception) {
        view.setAuthButton(RETRY)
        view.setLoginStatus(ERROR_NETWORK_UNAVAILABLE)
        view.setStatusDrawable(ANTENNA_EMPTY)

    }

    private fun renderActiveUser() {
        view.showLoopAnimation()
        view.setAuthButton(SIGN_OUT)
        view.setLoginStatus(SIGNED_IN)
    }

    private fun renderNullUser() {
        view.setLoginStatus(SIGNED_OUT)
        view.setStatusDrawable(ANTENNA_FULL)
        view.setAuthButton(SIGN_IN)
    }

    private fun renderErrorState(message: String) {
        //TODO handle different types of errors
        view.setStatusDrawable(ANTENNA_EMPTY)
        view.setAuthButton(RETRY)
        view.setLoginStatus(message)

    }

}