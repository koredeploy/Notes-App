package com.bankaapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bankaapp.data.dao.NoteDao
import com.bankaapp.data.dto.Note

@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

}

