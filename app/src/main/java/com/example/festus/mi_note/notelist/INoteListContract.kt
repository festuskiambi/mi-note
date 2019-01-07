package com.example.festus.mi_note.notelist

import androidx.recyclerview.widget.ListAdapter
import com.example.domain.domainmodel.Note
import com.example.domain.domainmodel.User

/**
 * Created by Festus Kiambi on 1/7/19.
 */
interface INoteListContract {

    interface View {
        fun setAdapter(adapter: ListAdapter<Note, NoteListAdapter.NoteViewHolder>)
        fun showNoteList()
        fun showEmptyState()
        fun showLoadingView()
        fun setToolbarTitle(title: String)
    }

    interface ViewModel {
        fun setAdapterState(result: List<Note>)
        fun getAdapterState(): List<Note>
        fun getUserState(): User?
        fun setUserState(userResult: User?)
        fun setIsPrivate(isPrivateMode: Boolean)
        fun getIsPrivate(): Boolean
    }

    interface Navigator {
        fun startLoginFeature()
        fun startNoteDetailFeatureWithExtras(noteId: String, isPrivate: Boolean)
    }

    interface Logic {
        fun event(event: NoteListEvent<Int>)
    }
}

sealed class NoteListEvent<out T> {
    data class OnNoteItemClick<out Int>(val position: Int) : NoteListEvent<Int>()
    object OnNewNoteClick : NoteListEvent<Nothing>()
    object OnLoginClick : NoteListEvent<Nothing>()
    object OnTogglePublicMode : NoteListEvent<Nothing>()
    object OnStart : NoteListEvent<Nothing>()
    object OnBind : NoteListEvent<Nothing>()
    object OnDestroy : NoteListEvent<Nothing>()
}