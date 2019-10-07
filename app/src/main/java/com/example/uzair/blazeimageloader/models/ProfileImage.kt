package com.example.uzair.blazeimageloader.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfileImage (
    val small : String = "",
    val medium : String = "",
    val large : String = ""
)