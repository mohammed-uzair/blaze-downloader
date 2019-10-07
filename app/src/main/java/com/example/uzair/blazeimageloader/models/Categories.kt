package com.example.uzair.blazeimageloader.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Categories(
    @PrimaryKey
    @ColumnInfo(name = "categoriesId")
    val id: Int = 0,
    val title: String = "",
    val photo_count: Int = 0,
    @Embedded
    val links: Links? = null
)