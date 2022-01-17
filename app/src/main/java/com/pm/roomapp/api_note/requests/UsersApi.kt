package com.pm.roomapp.api_note.requests

import com.pm.roomapp.api_note.dto.UserDto
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UsersApi {

    @FormUrlEncoded
    @POST(("users/signin"))
    fun signin(@Field("username") username: String, @Field("password") password : String) : Call<UserDto>
}