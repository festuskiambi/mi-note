package com.example.festus.mi_note.notelist

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.domainmodel.Note

/**
 * Created by Festus Kiambi on 1/7/19.
 */

class NoteDiffUtilCallback: DiffUtil.ItemCallback<Note> (){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.creationDate == newItem.creationDate
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.creationDate == newItem.creationDate
    }

}