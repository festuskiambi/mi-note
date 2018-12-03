package com.example.domain

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Created by Festus Kiambi on 12/3/18.
 */

object DispatcherProvider {
    fun provideUIContext(): CoroutineContext {
        return Dispatchers.Main
    }

    fun provideIOContext(): CoroutineContext {
        return Dispatchers.IO
    }
}