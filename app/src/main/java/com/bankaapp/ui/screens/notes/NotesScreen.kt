
package com.bankaapp.ui.screens.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bankaapp.R
import com.bankaapp.data.dto.Note
import com.bankaapp.ui.components.BottomNavBar
import com.bankaapp.ui.components.CustomDropdownMenu
import com.bankaapp.ui.components.CustomTextField
import com.bankaapp.ui.components.DropdownMenus
import com.bankaapp.ui.components.MediumSpace
import com.bankaapp.ui.navigation.BankaScreens
import com.bankaapp.ui.theme.darkBlue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var showDropdownMenu by remember { mutableStateOf(false) }

    val noteState by viewModel.noteState.collectAsState()

    Scaffold(
        bottomBar = {
            val noteCount = when (val state = noteState) {
                is NoteScreenState.Success -> state.notes.size
                else -> 0
            }
            BottomNavBar(
                text = "$noteCount Notes",
                icon = Icons.Default.Apps,
                onIconClick = { showDropdownMenu = !showDropdownMenu }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 40.dp),
                    text = "Notes",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                MediumSpace()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        CustomTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = "Search"
                        )
                    }
                    // Filter button
                    IconButton(
                        onClick = { /* Handle filter */ },
                        modifier = Modifier
                            .size(56.dp)
                            .background(
                                darkBlue,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.customfilterbutton),
                            contentDescription = "Filter",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                MediumSpace()

                // Content based on state
                when (val state = noteState) {
                    is NoteScreenState.LoadingState -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = darkBlue)
                        }
                    }

                    is NoteScreenState.Success -> {
                        val filteredNotes = if (searchQuery.isNotBlank()) {
                            state.notes.filter { note ->
                                note.title.contains(searchQuery, ignoreCase = true) ||
                                        note.content.contains(searchQuery, ignoreCase = true)
                            }
                        } else {
                            state.notes
                        }

                        if (filteredNotes.isEmpty()) {
                            EmptyNotesState()
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(filteredNotes) { note ->
                                    NoteCard(
                                        note = note,
                                        onClick = {
                                            // Navigate to note detail or edit screen
                                            // navController.navigate("${BankaScreens.NoteDetailScreen}/${note.id}")
                                        }
                                    )
                                }
                            }
                        }
                    }

                    is NoteScreenState.Error -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 100.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Error: ${state.message}",
                                fontSize = 16.sp,
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    else -> {
                        EmptyNotesState()
                    }
                }
            }

            // Dropdown Menu positioned at the bottom right
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 80.dp)
            ) {
                CustomDropdownMenu(
                    expanded = showDropdownMenu,
                    onDismissRequest = { showDropdownMenu = false },
                    items = DropdownMenus.notesMenuItems(
                        onCreateNote = {
                            navController.navigate(BankaScreens.CreateNoteScreen)
                        },
                        onLogout = {
                            navController.navigate(BankaScreens.OnboardingScreen)
                        }
                    )
                )
            }
        }
    }
}

@Composable
fun EmptyNotesState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.nonotes),
            contentDescription = "No notes",
            modifier = Modifier.size(200.dp)
        )
        MediumSpace()
        Text(
            text = "You do not have any notes yet.",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun NoteCard(
    note: Note,
    onClick: () -> Unit
) {
    val dateFormat = remember {
        SimpleDateFormat("dd MMM yyyy 'at' HH:mm", Locale.getDefault())
    }
    val formattedDate = remember(note.createdAt) {
        dateFormat.format(Date(note.createdAt))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = note.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (note.content.isNotBlank()) {
                Text(
                    text = note.content,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Text(
                text = formattedDate,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}