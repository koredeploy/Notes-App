package com.bankaapp.data.repository

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

    override suspend fun updateNote(note: Note): Result<Unit> {
        return try {
            noteDao.updateNote(note)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteNote(note: Note): Result<Unit> {
        return try {
            noteDao.deleteNote(note)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

//    override fun searchNotes(query: String): Flow<List<Note>> {
//        return noteDao.searchNotesNoCase(query)
//    }

}