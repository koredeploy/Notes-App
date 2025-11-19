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