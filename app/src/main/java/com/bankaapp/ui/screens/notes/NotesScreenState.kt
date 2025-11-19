package com.bankaapp.ui.screens.notes

import com.bankaapp.data.dto.Note

sealed class NoteScreenState {
    object DefaultState : NoteScreenState()
    object LoadingState : NoteScreenState()
    data class Success(val notes: List<Note>) : NoteScreenState()
    data class NoteAdded(val message: String) : NoteScreenState()
    data class Error(val message: String) : NoteScreenState()
}

