package com.example.uzair.blazeimageloader.models

import androidx.room.Entity

/**
 * Model class for profileimage
 */
@Entity
data class ProfileImage(
    val small: String = "",
    val medium: String = "",
    val large: String = ""
)