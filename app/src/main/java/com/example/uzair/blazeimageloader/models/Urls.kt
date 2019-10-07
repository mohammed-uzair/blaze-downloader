package com.example.uzair.blazeimageloader.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Urls(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "url_id")
    val id : Int = 0,
    val raw: String = "",
    val full: String = "",
    val regular: String = "",
    val small: String = "",
    val thumb: String = ""
)