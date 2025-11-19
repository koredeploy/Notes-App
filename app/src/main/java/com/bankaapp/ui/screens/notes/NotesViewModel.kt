package com.bankaapp.ui.screens.notes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankaapp.data.domain.usecase.GetAllNotesUseCase
import com.bankaapp.data.domain.usecase.PostNoteUseCase
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
    private val postNoteUseCase: PostNoteUseCase
) : ViewModel() {

    private val _noteState = MutableStateFlow<NoteScreenState>(NoteScreenState.DefaultState)
    val noteState: StateFlow<NoteScreenState> = _noteState.asStateFlow()

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
                    // But you can trigger a specific success state if needed
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
}