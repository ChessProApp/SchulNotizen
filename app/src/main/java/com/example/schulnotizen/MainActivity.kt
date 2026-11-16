package com.example.schulnotizen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.schulnotizen.data.NoteEntity
import com.example.schulnotizen.ui.SchoolViewModel
import com.example.schulnotizen.ui.screens.DashboardScreen
import com.example.schulnotizen.ui.screens.EditorScreen
import com.example.schulnotizen.ui.theme.SchulNotizenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SchulNotizenTheme {
                val viewModel: SchoolViewModel = viewModel()
                val notes by viewModel.notes.collectAsState()
                val openTabs by viewModel.openTabs.collectAsState()
                val exams by viewModel.exams.collectAsState()

                var activeNoteId by remember { mutableStateOf<Long?>(null) }

                val activeNote = notes.find { it.id == activeNoteId }

                if (activeNote != null) {
                    EditorScreen(
                        currentNote = activeNote,
                        openTabs = openTabs,
                        onTabSelected = { activeNoteId = it.id },
                        onTabClose = { tab ->
                            viewModel.setTabOpen(tab.id, false)
                            if (activeNoteId == tab.id) {
                                activeNoteId = openTabs.firstOrNull { it.id != tab.id }?.id
                            }
                        },
                        onNoteChanged = { updated ->
                            viewModel.updateNote(updated)
                        },
                        onBackToDashboard = {
                            activeNoteId = null
                        }
                    )
                } else {
                    DashboardScreen(
                        notes = notes,
                        exams = exams,
                        onOpenNote = { note ->
                            viewModel.setTabOpen(note.id, true)
                            activeNoteId = note.id
                        },
                        onCreateNote = { title, subject, colorHex ->
                            viewModel.createNote(title, subject, "", colorHex)
                        },
                        onDeleteNote = { id ->
                            viewModel.deleteNote(id)
                            if (activeNoteId == id) activeNoteId = null
                        },
                        onCreateExam = { title, subject, dateMillis, notesText ->
                            viewModel.createExam(title, subject, dateMillis, notesText)
                        },
                        onDeleteExam = { id ->
                            viewModel.deleteExam(id)
                        }
                    )
                }
            }
        }
    }
}
