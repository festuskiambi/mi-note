package com.example.data.note.registered

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.entities.RoomNote
import com.example.data.note.RoomNoteDao

/**
 * Created by Festus Kiambi on 12/4/18.
 */

const val DATABASE_REG = "registered"

@Database(
    entities = [RoomNote::class],
    version = 1,
    exportSchema = false
)

abstract class RegisteredNoteDataBase : RoomDatabase(){

    abstract fun roomNoteDao(): RoomNoteDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: RegisteredNoteDataBase? = null

        fun getInstance(context: Context): RegisteredNoteDataBase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): RegisteredNoteDataBase {
            return Room.databaseBuilder(context, RegisteredNoteDataBase::class.java,
                DATABASE_REG
            )
                .build()
        }
    }
}