package com.example.schulnotizen.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.schulnotizen.data.AppDatabase
import com.example.schulnotizen.data.ExamEntity
import com.example.schulnotizen.data.NoteEntity
import com.example.schulnotizen.data.SchoolRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SchoolViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val repository = SchoolRepository(db.noteDao(), db.examDao())

    val notes: StateFlow<List<NoteEntity>> = repository.allNotes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val openTabs: StateFlow<List<NoteEntity>> = repository.openTabs.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val exams: StateFlow<List<ExamEntity>> = repository.allExams.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun createNote(title: String, subject: String, content: String, colorHex: String): Long {
        var newId = 0L
        viewModelScope.launch {
            newId = repository.saveNote(
                NoteEntity(
                    title = title.ifBlank { "Unbenannte Notiz" },
                    subject = subject.ifBlank { "Allgemein" },
                    content = content,
                    tabColorHex = colorHex,
                    isOpenInTab = true
                )
            )
        }
        return newId
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.saveNote(note.copy(updatedAt = System.currentTimeMillis()))
        }
    }

    fun setTabOpen(id: Long, isOpen: Boolean) {
        viewModelScope.launch {
            repository.setTabOpen(id, isOpen)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun createExam(title: String, subject: String, dateMillis: Long, notes: String) {
        viewModelScope.launch {
            repository.saveExam(
                ExamEntity(
                    title = title.ifBlank { "Klausur" },
                    subject = subject.ifBlank { "Allgemein" },
                    dateEpochMillis = dateMillis,
                    notes = notes
                )
            )
        }
    }

    fun deleteExam(id: Long) {
        viewModelScope.launch {
            repository.deleteExam(id)
        }
    }
}
