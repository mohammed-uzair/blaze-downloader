package com.example.uzair.blazeimageloader.datasource


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.uzair.blazeimageloader.models.WallPost
import com.example.uzair.blazeimageloader.util.NetworkState

class WallPostDataSource : PageKeyedDataSource<Long, WallPost>() {
    companion object {
        private val TAG = this::class.java.simpleName
    }

    private val initialLoading = MutableLiveData<NetworkState>()
    val networkState = MutableLiveData<NetworkState>()

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, WallPost>
    ) {
        //Left deliberately empty
    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, WallPost>
    ) {
        initialLoading.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)

        /*todo - get the data from the data source from web service, since our webservice
        does not support pagination for now, we will cache the data in the InMemory data base,
        and fetch the data from there*/
    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, WallPost>
    ) {
        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize)

        networkState.postValue(NetworkState.LOADING)

        /*todo - get the data from the data source from web service, since our webservice
        does not support pagination for now, we will cache the data in the InMemory data base,
        and fetch the data from there*/
    }
}
