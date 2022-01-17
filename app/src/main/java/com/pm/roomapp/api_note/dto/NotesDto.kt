package com.pm.roomapp.api_note.dto

import com.pm.roomapp.api_note.models.Notes

data class NotesDto(
    val status : String,
    val message : String,
    val notes : Notes
)