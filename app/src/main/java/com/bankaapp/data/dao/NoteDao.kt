package com.bankaapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bankaapp.data.dto.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
        //insert notes
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertNote(note: Note)

        //Update notes
        @Update
        suspend fun updateNote(note: Note)

        //Delete notes
        @Delete
        suspend fun deleteNote(note: Note)

        // Get all notes ordered by creation date (newest first)
         @Query("SELECT * FROM Note_model ORDER BY created_at DESC")
        fun getAllNotes(): Flow<List<Note>>

        // Case-insensitive search with COLLATE NOCASE (more explicit)
        @Query("SELECT * FROM Note_model WHERE content LIKE '%' || :searchQuery || '%' COLLATE NOCASE ORDER BY created_at DESC")
        fun searchNotesNoCase(searchQuery: String): Flow<List<Note>>

}
