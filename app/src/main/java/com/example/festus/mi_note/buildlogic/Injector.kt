package com.example.festus.mi_note.buildlogic

import android.content.Context
import com.example.data.note.RoomLocalAnonymousRepositoryImpl
import com.google.firebase.FirebaseApp

/**
 * Created by Festus Kiambi on 12/10/18.
 */

class Injector(private val activityConyext: Context){

    init {
        FirebaseApp.initializeApp(activityConyext)
    }

//    private val localAnon: ILocalNoteRepository by lazy {
//        RoomLocalAnonymousRepositoryImpl(noteDao)
//    }



}