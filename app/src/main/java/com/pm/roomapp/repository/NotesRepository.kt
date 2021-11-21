package com.pm.roomapp.repository

import androidx.lifecycle.LiveData
import com.pm.roomapp.data.NotesDao
import com.pm.roomapp.model.Notes

class NotesRepository(private val notesDao: NotesDao) {

    val readAllData: LiveData<List<Notes>> = notesDao.readAllData()

    suspend fun addNote(notes: Notes){
        notesDao.addNote(notes)
    }

//    suspend fun updateNote(notes: Notes){
//        notesDao.updateNote(notes)
//    }
}