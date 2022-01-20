package com.pm.roomapp.api_note.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notes (
    val id: Int,
    val title: String,
    val description: String,
    val users_id : Int,
    val user_name: String
) : Parcelable