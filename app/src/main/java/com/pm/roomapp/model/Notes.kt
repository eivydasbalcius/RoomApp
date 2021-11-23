package com.pm.roomapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notes_table")
class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val title: String,
    val note: String,
) : Parcelable