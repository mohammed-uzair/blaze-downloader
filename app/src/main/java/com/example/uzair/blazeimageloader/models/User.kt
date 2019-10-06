package com.example.uzair.blazeimageloader.models

data class User(
    val id: String,
    val username: String,
    val name: String,
    val profile_image: ProfileImage,
    val links: Links
)