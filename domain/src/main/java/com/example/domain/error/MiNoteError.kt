package com.example.domain.error

/**
 * Created by Festus Kiambi on 12/3/18.
 */

sealed class MiNoteError: Exception() {
    object LocalIOException: MiNoteError()
    object RemoteIOException: MiNoteError()
    object NetworkUnavailableException: MiNoteError()
    object AuthError: MiNoteError()

}

const val ERROR_UPDATE_FAILED = "Update operation unsuccessful."
const val ERROR_DELETE_FAILED = "Delete operation unsuccessful."

