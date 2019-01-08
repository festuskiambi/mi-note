package com.example.festus.mi_note.notelist

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.domainmodel.Note

/**
 * Created by Festus Kiambi on 1/7/19.
 */

class NoteListAdapter(
    var logic: INoteListContract.Logic? = null
) : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NoteDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class NoteViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
//        var square: ImageView = root.imv_list_item_icon
//        var dateIcon: ImageView = root.imv_date_and_time
//        var content: TextView = root.lbl_message
//        var date: TextView = root.lbl_date_and_time
//        var loading: ProgressBar = root.pro_item_data
    }
}