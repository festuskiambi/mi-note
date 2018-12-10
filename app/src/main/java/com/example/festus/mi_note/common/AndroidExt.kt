package com.example.festus.mi_note.common

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.*

/**
 * Created by Festus Kiambi on 12/10/18.
 */
internal fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


internal fun Activity.attachFragment(manager: FragmentManager, containerId: Int, view: Fragment, tag: String) {
    manager.beginTransaction()
        .replace(containerId, view, tag)
        .commitNowAllowingStateLoss()
}

internal fun Fragment.getCalendarTime(): String {
    val cal = Calendar.getInstance(TimeZone.getDefault())
    val builder = StringBuilder()
    builder.append(cal.get(Calendar.HOUR_OF_DAY))
    builder.append(":")
    builder.append(cal.get(Calendar.MINUTE))
    builder.append(":")
    builder.append(cal.get(Calendar.SECOND))
    builder.append(cal.get(Calendar.AM_PM))
    builder.append(", ")
    builder.append(cal.get(Calendar.MONTH))
    builder.append(", ")
    builder.append(cal.get(Calendar.DAY_OF_WEEK_IN_MONTH))
    builder.append(", ")
    builder.append(cal.get(Calendar.YEAR))

    return builder.toString()
}

internal fun Fragment.makeToast(value: String) {
    Toast.makeText(activity, value, Toast.LENGTH_SHORT).show()
}

//internal fun Fragment.restartCurrentFeature() {
//    val i: Intent
//    when (this) {
//        is NoteDetailView -> {
//            i = Intent(this.activity, NoteDetailActivity::class.java)
//        }
//
//        //To Be Added
//
//        else -> {
//            i = Intent(this.activity, NoteListActivity::class.java)
//        }
//    }
//
//    this.activity?.finish()
//    startActivity(i)
//}