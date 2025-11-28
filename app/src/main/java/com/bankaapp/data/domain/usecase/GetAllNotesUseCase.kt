package com.bankaapp.data.domain.usecase

import com.bankaapp.data.domain.repository.NoteRepository
import com.bankaapp.data.dto.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> {
        return noteRepository.getNotes()
    }
}

class PostNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: String, title: String, content: String): Result<Note> {
        return noteRepository.postNote(id, title, content)
    }
}

class GetNoteByIdUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteId: String): Result<Note?> {
        return noteRepository.getNoteById(noteId)
    }
}


class UpdateNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: String, title: String, content: String): Result<Note> {
        return noteRepository.updateNote(id, title, content)
    }
}


class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteId: String): Result<Unit> {
        return noteRepository.deleteNote(noteId)
    }
}