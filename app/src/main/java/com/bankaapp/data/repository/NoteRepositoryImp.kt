package com.bankaapp.data.repository

import android.util.Log
import androidx.annotation.Size
import com.bankaapp.data.dao.NoteDao
import com.bankaapp.data.domain.repository.NoteRepository
import com.bankaapp.data.dto.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun postNote(id: String, title: String, content: String): Result<Note> {
        return try {
            val note = Note(
                id = id,
                title = title,
                content = content,
                createdAt = System.currentTimeMillis()
            )
            noteDao.insertNote(note)
            Result.success(note)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun getNoteById(noteId: String): Result<Note?> {
        return try {
            val note = noteDao.getNoteById(noteId)
            Result.success(note)
        } catch (e: Exception) {
            Log.e("NoteRepository", "Error getting note by ID: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun updateNote(id: String, title: String, content: String): Result<Note> {
        return try {
            val updatedNote = Note(
                id = id,
                title = title,
                content = content,
                createdAt = System.currentTimeMillis()
            )
            noteDao.updateNote(updatedNote)
            Result.success(updatedNote)
        } catch (e: Exception) {
            Log.e("NoteRepository", "Error updating note: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun deleteNote(noteId: String): Result<Unit> {
        return try {
            noteDao.deleteNoteById(noteId)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("NoteRepository", "Error deleting note: ${e.message}")
            Result.failure(e)
        }
    }


    override fun observeNoteById(id: String): Flow<Note?> {
        return noteDao.observeNoteById(id)
    }

//    override fun searchNotes(query: String): Flow<List<Note>> {
//        return noteDao.searchNotesNoCase(query)
//    }

}