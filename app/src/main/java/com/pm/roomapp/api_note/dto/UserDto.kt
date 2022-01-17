package com.pm.roomapp.api_note.dto

import com.pm.roomapp.api_note.models.User

data class UserDto (
    val status : String,
    val message : String,
    val user : List<User>,
    val token : String
)