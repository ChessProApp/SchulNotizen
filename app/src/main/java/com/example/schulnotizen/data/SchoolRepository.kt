package com.example.schulnotizen.data

import kotlinx.coroutines.flow.Flow

class SchoolRepository(
    private val noteDao: NoteDao,
    private val examDao: ExamDao
) {
    val allNotes: Flow<List<NoteEntity>> = noteDao.getAllNotes()
    val openTabs: Flow<List<NoteEntity>> = noteDao.getOpenTabs()
    val allExams: Flow<List<ExamEntity>> = examDao.getAllExams()

    suspend fun saveNote(note: NoteEntity): Long = noteDao.insertNote(note)
    suspend fun setTabOpen(id: Long, isOpen: Boolean) = noteDao.updateTabState(id, isOpen)
    suspend fun deleteNote(id: Long) = noteDao.deleteNote(id)

    suspend fun saveExam(exam: ExamEntity): Long = examDao.insertExam(exam)
    suspend fun deleteExam(id: Long) = examDao.deleteExam(id)
}
