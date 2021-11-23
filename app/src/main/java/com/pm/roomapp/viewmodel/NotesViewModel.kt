package com.pm.roomapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pm.roomapp.data.NotesDatabase
import com.pm.roomapp.repository.NotesRepository
import com.pm.roomapp.model.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Notes>>
    private val repository: NotesRepository

    init {
        val notesDao = NotesDatabase.getDatabase(application).notesDao()
        repository = NotesRepository(notesDao)
        readAllData = repository.readAllData
    }

    fun addNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(notes)
        }
    }

    fun updateNotes(note: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNotes(note)
        }
    }

    fun deleteNote(note: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotes()
        }
    }
}