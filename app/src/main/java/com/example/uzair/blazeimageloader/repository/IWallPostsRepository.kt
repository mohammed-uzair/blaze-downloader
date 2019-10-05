package com.example.uzair.blazeimageloader.repository

import WallPost
import androidx.lifecycle.LiveData

interface IWallPostsRepository {
    fun getAllWallPosts(): LiveData<WallPost>
}