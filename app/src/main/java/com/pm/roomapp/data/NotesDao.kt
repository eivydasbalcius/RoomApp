package com.pm.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pm.roomapp.model.Notes

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNote(notes: Notes)

    @Update
    fun updateNotes(note: Notes)

    @Delete
    fun deleteNote(note: Notes)

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Notes>>
}