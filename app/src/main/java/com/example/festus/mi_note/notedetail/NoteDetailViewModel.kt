package com.example.festus.mi_note.notedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.domainmodel.Note

/**
 * Created by Festus Kiambi on 12/10/18.
 */

class NoteDetailViewModel(

    private var displayState: MutableLiveData<Note> = MutableLiveData(),
    private var id: MutableLiveData<String> = MutableLiveData(),
    private var isPrivateMode: MutableLiveData<Boolean> = MutableLiveData()

) : INoteDetailContract.ViewModel, ViewModel() {

    override fun setIsPrivateMode(isPrivateMode: Boolean) {
        this.isPrivateMode.value!!
    }

    override fun getIsPrivateMode(): Boolean {
        return isPrivateMode.value!!
    }

    override fun setNoteState(note: Note) {
        displayState.value = note
    }

    override fun getNoteState(): Note? {
        return displayState.value
    }

    override fun setId(id: String) {
        this.id.value = id
    }

    override fun getId(): String? {
        return id.value
    }

}