package com.example.uzair.blazeimageloader.viewmodel

data class ImageFeedViewModel(
    val imageUrl: String,
    val userNameProfileImageUrl: String,
    val username: String,
    val totalLikes: Int,
    val isLikedByUser: Boolean
)