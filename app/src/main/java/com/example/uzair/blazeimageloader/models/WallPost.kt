package com.example.uzair.blazeimageloader.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WallPost(
    @PrimaryKey(autoGenerate = true)
    val postId: Int = 0,
    @ColumnInfo(name = "wallPostId")
    val id: String = "",
    val created_at: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val color: String = "",
    val likes: Int = 0,
    val liked_by_user: Boolean = true,
    @Embedded
    val user: User? = null,
    val current_user_collections: List<String>? = null,
    @Embedded
    val urls: Urls? = null,
    val categories: List<Categories>? = null,
    @Embedded
    val links: Links? = null
)