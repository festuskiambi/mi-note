package com.example.festus.mi_note.notedetail

import com.example.domain.domainmodel.Note

/**
 * Created by Festus Kiambi on 12/10/18.
 */

interface INoteDetailContract {

    interface View {
        fun setBackgroundImage(imageUrl: String)
        fun setDateLabel(date: String)
        fun setNoteBody(content: String)
        fun hideBackButton()
        fun getNoteBody(): String
        fun getTime(): String
        fun startListFeature()
        fun restartFeature()
        fun showMessage()
        fun showDeleteSnackbar()
    }

    interface ViewModel {
        fun setIsPrivateMode(isPrivateMode: Boolean)
        fun getIsPrivateMode(): Boolean
        fun setNoteState(note: Note)
        fun getNoteState(): Note?
        fun setId(id: String)
        fun getId(): String?
    }

    interface Logic {
        fun event(event: NoteDetailEvent)
    }
}

sealed class NoteDetailEvent {
    object onDoneClick : NoteDetailEvent()
    object onDeleteClick : NoteDetailEvent()
    object onDeleteConfirmed : NoteDetailEvent()
    object onBackClick : NoteDetailEvent()
    object onStart : NoteDetailEvent()
    object onBind : NoteDetailEvent()
    object onDestroy : NoteDetailEvent()
}