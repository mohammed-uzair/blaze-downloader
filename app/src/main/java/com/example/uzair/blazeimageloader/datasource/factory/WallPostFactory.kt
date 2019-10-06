package com.example.uzair.blazeimageloader.datasource.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

import com.example.uzair.blazeimageloader.datasource.WallPostDataSource
import com.example.uzair.blazeimageloader.models.WallPost

class WallPostFactory : DataSource.Factory<Long, WallPost>(){
    private val mutableLiveData: MutableLiveData<WallPostDataSource> = MutableLiveData()
    private lateinit var wallPostsDataSource: WallPostDataSource

    override fun create() : DataSource<Long, WallPost> {
        wallPostsDataSource = WallPostDataSource()
        mutableLiveData.postValue(wallPostsDataSource)
        return wallPostsDataSource
    }

    fun getMutableLiveData():  MutableLiveData<WallPostDataSource> {
        return mutableLiveData
    }
}
