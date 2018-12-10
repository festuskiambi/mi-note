package com.example.festus.mi_note.login

import com.example.domain.DispatcherProvider
import com.example.domain.ServiceLocator
import com.example.domain.interactor.AuthSource
import com.example.festus.mi_note.common.BaseLogic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
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

    private fun onSignInResult(result: LoginResult) {

    }

    private fun onAuthButtonClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onBackClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onStart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}