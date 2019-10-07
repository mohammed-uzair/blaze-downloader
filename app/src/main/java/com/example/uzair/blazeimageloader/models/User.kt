package com.example.uzair.blazeimageloader.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    @ColumnInfo(name = "userId")
    val id: String = "",
    val username: String = "",
    val name: String = "",
    @Embedded(prefix = "prf_img")
    val profile_image: ProfileImage? = null,
    @Embedded(prefix = "links_")
    val links: Links? = null
)