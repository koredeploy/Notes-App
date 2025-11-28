package com.bankaapp.data.domain.repository

import com.bankaapp.data.dto.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
        suspend fun postNote(id: String, title: String, content: String): Result<Note>

        fun getNotes(): Flow<List<Note>>
        suspend fun getNoteById(id: String): Result<Note?>
        suspend fun updateNote(id: String, title: String, content: String): Result<Note>
        suspend fun deleteNote(noteId: String): Result<Unit>

        fun observeNoteById(id: String): Flow<Note?>

//      suspend fun searchNotes(query: String): Flow<List<Note>>
}
