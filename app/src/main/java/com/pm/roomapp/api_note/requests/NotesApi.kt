package com.pm.roomapp.api_note.requests

import com.pm.roomapp.api_note.dto.NotesDto
import com.pm.roomapp.api_note.models.Notes
import retrofit2.Call
import retrofit2.http.*

interface NotesApi {
    @GET("notes/read")
    fun getNotes(@Header("Authorization") token: String): Call<List<Notes>>

    @FormUrlEncoded
    @POST("notes/create")
    fun createNote(
        @Header("Authorization") token: String,
        @Field("users_id") users_id: String?,
        @Field("description") description: String
    ): Call<NotesDto>

    @FormUrlEncoded
    @POST("notes/update")
    fun updateNote(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
        @Field("description") description: String
    ): Call<NotesDto>

    @FormUrlEncoded
    @POST("notes/delete")
    fun deleteNote(@Header("Authorization") token: String, @Field("id") id: Int): Call<NotesDto>
}