package com.example.festus.mi_note.common

import com.example.domain.DispatcherProvider
import com.example.domain.NoteServiceLocator
import kotlinx.coroutines.Job

/**
 * Created by Festus Kiambi on 12/10/18.
 */
abstract class BaseLogic(val dispatcher: DispatcherProvider) {

    protected lateinit var jobTracker: Job
}