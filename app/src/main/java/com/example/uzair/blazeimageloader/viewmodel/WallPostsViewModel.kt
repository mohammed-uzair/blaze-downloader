package com.example.uzair.blazeimageloader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.uzair.blazeimageloader.datasource.factory.WallPostFactory
import com.example.uzair.blazeimageloader.models.WallPost
import com.example.uzair.blazeimageloader.repository.IWallPostsRepository
import com.example.uzair.blazeimageloader.repository.WallPostsRepository
import com.example.uzair.blazeimageloader.util.NetworkState

/**
 * This is the viewmodel for the home fragment and does all the processing for the home UI data here.
 */
class WallPostsViewModel : ViewModel() {
    private var networkState: LiveData<NetworkState>
    private var wallPostsLiveData: LiveData<PagedList<WallPost>>

    init {
        val wallPostFactory = WallPostFactory()

        networkState = Transformations.switchMap(
            wallPostFactory.getMutableLiveData()
        ) { dataSource -> dataSource.networkState }

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(20).build()

        wallPostsLiveData = LivePagedListBuilder(wallPostFactory, pagedListConfig)
            .build()
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return networkState
    }

    fun getAllWallPosts(): LiveData<PagedList<WallPost>> {
        return wallPostsLiveData
    }
}