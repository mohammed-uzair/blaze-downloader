package com.example.uzair.blazeimageloader.repository

import androidx.lifecycle.MutableLiveData
import com.example.uzair.blazeimageloader.models.WallPost

interface IWallPostsRepository {
    fun getAllWallPosts(mutableLiveData: MutableLiveData<ArrayList<WallPost>>)
}