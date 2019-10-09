package com.example.uzair.blazeimageloader.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model class for links
 */
@Entity
data class Links(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "links_id")
    val id: Int = 0,
    @ColumnInfo(name = "link_self")
    val self: String = "",
    val html: String = "",
    val download: String = ""
)