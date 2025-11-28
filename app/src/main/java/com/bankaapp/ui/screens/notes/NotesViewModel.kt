package com.bankaapp.ui.screens.notes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankaapp.data.domain.usecase.DeleteNoteUseCase
import com.bankaapp.data.domain.usecase.GetAllNotesUseCase
import com.bankaapp.data.domain.usecase.GetNoteByIdUseCase
import com.bankaapp.data.domain.usecase.PostNoteUseCase
import com.bankaapp.data.domain.usecase.UpdateNoteUseCase
import com.bankaapp.data.dto.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val postNoteUseCase: PostNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _noteState = MutableStateFlow<NoteScreenState>(NoteScreenState.DefaultState)
    val noteState: StateFlow<NoteScreenState> = _noteState.asStateFlow()
    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?> = _selectedNote.asStateFlow()

    init {
        // Load notes when ViewModel is initialized
        getAllNotes()
    }

    fun getAllNotes() {
        viewModelScope.launch {
            _noteState.value = NoteScreenState.LoadingState

            try {
                getAllNotesUseCase().collect { notes ->
                    _noteState.value = NoteScreenState.Success(notes = notes)
                }
            } catch (exception: Exception) {
                Log.e("NoteViewModel", "Error loading notes: ${exception.message}")
                _noteState.value = NoteScreenState.Error(
                    exception.message ?: "Failed to load notes"
                )
            }
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            _noteState.value = NoteScreenState.LoadingState

            val id = UUID.randomUUID().toString()

            postNoteUseCase(id, title, content)
                .onSuccess { note ->
                    Log.i("NoteViewModel", "Note saved successfully: ${note.title}")
                    // Notes will automatically update via Flow in getAllNotes()
                    _noteState.value = NoteScreenState.NoteAdded(
                        message = "Note added successfully"
                    )
                }
                .onFailure { exception ->
                    Log.e("NoteViewModel", "Error saving note: ${exception.message}")
                    _noteState.value = NoteScreenState.Error(
                        exception.message ?: "Failed to save note"
                    )
                }
        }
    }

    fun getNoteById(noteId: String) {
        viewModelScope.launch {
            _noteState.value = NoteScreenState.LoadingState

            getNoteByIdUseCase(noteId)
                .onSuccess { note ->
                    _selectedNote.value = note
                    Log.i("NoteViewModel", "Note retrieved successfully: ${note?.title}")
                }
                .onFailure { exception ->
                    Log.e("NoteViewModel", "Error retrieving note: ${exception.message}")
                    _selectedNote.value = null
                }
        }
    }


    fun updateNote(id: String, title: String, content: String) {
        viewModelScope.launch {
            updateNoteUseCase(id, title, content)
                .onSuccess { note ->
                    Log.i("NoteViewModel", "Note updated successfully: ${note.title}")
                    _selectedNote.value = note
                    // Notes will automatically update via Flow in getAllNotes()
                }
                .onFailure { exception ->
                    Log.e("NoteViewModel", "Error updating note: ${exception.message}")
                    _noteState.value = NoteScreenState.Error(
                        exception.message ?: "Failed to update note"
                    )
                }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            deleteNoteUseCase(noteId)
                .onSuccess {
                    Log.i("NoteViewModel", "Note deleted successfully")
                    _selectedNote.value = null
                    // Notes will automatically update via Flow in getAllNotes()
                }
                .onFailure { exception ->
                    Log.e("NoteViewModel", "Error deleting note: ${exception.message}")
                    _noteState.value = NoteScreenState.Error(
                        exception.message ?: "Failed to delete note"
                    )
                }
        }
    }

    fun clearSelectedNote() {
        _selectedNote.value = null
    }



}