package com.bankaapp.ui.screens.createnote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bankaapp.ui.screens.notes.NoteScreenState
import com.bankaapp.ui.screens.notes.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CreateNoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel()
) {
    var noteTitle by remember { mutableStateOf("") }
    var noteContent by remember { mutableStateOf("") }
    var showLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var shouldNavigateBack by remember { mutableStateOf(false) }

    val noteState by viewModel.noteState.collectAsState()

    // Get current date formatted as "12 November 2025 at 10:13"
    val currentDate = remember {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy 'at' HH:mm", Locale.getDefault())
        dateFormat.format(Date())
    }

    // Handle navigation


    // Handle state changes
    LaunchedEffect(noteState) {
        when (val state = noteState) {
            is NoteScreenState.LoadingState -> {
                showLoading = true
                errorMessage = null
            }
            is NoteScreenState.NoteAdded -> {
                showLoading = false
                navController.popBackStack()
            }
            is NoteScreenState.Error -> {
                showLoading = false
                errorMessage = state.message
            }
            else -> {
                showLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar with Back button and Done button
        Row(
            modifier = Modifier
                .padding(top = 25.dp, bottom = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.offset(x = (-10).dp),
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Text(
                    text = "Notes",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            TextButton(
                onClick = {

                    if (noteTitle.isNotBlank() || noteContent.isNotBlank()) {
                        showLoading = true
                        viewModel.addNote(
                            title = noteTitle.ifBlank { "Untitled" },
                            content = noteContent
                        )
//                        navController.popBackStack()
                    } else {
                        errorMessage = "Please add some content to save"
                    }
                },
                enabled = !showLoading
            ) {
                if (showLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Done",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Error message
        errorMessage?.let { error ->
            Text(
                text = error,
                fontSize = 14.sp,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        // Current Date Display
        Text(
            text = currentDate,
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Title TextField (without border/background)
        TextField(
            value = noteTitle,
            onValueChange = { noteTitle = it },
            placeholder = {
                Text(
                    text = "Title",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            },
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = !showLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Content TextField (without border/background)
        TextField(
            value = noteContent,
            onValueChange = { noteContent = it },
            placeholder = {
                Text(
                    text = "Start typing...",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black,
                lineHeight = 24.sp
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            minLines = 15,
            enabled = !showLoading
        )
    }
}