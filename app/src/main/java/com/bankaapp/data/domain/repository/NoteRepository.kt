package com.bankaapp.data.domain.repository

import com.bankaapp.data.dto.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

        suspend fun postNote(id: String, title: String, content: String): Result<Note>

        fun getNotes(): Flow<List<Note>>

        suspend fun updateNote(note: Note): Result<Unit>

        suspend fun deleteNote(note: Note): Result<Unit>

//      suspend fun searchNotes(query: String): Flow<List<Note>>
}
