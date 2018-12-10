package com.example.festus.mi_note.common

import com.example.domain.DispatcherProvider
import com.example.domain.ServiceLocator
import kotlinx.coroutines.Job

/**
 * Created by Festus Kiambi on 12/10/18.
 */
abstract class BaseLogic(val dispatcher: DispatcherProvider,
                         val locator: ServiceLocator
) {

    protected lateinit var jobTracker: Job
}