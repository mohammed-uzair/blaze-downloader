package com.example.uzair.blazeimageloader.repository

import androidx.lifecycle.MutableLiveData
import com.example.uzair.blaze_downloader.BlazeDownloader
import com.example.uzair.blazeimageloader.models.JsonData
import com.example.uzair.blazeimageloader.models.WallPost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This class is the intermediate between the data sources and the data requesting logic.
 * <p>
 * When ever a data is requested, this class is responsible to decide where to fetch the data
 * from (eg: local cache, database, web service).
 * <p>
 * This class abstracts the data fetch logic from other modules, thus other modules
 * should only ask data from this class and they should not care where the data is received from
 * (eg: local cache, database, web service).
 * <p>
 * For this application I have only implemented the data source as a web service(from blaze downloader module),
 * but at later point we can also add a data base or a local cache without disturbing any other
 * modules.
 */
class WallPostsRepository : IWallPostsRepository {
    override fun getAllWallPosts(mutableLiveData: MutableLiveData<ArrayList<WallPost>>) {
        /*Write a logic here to find where to get the data from,
        for now in this case its only webservice.*/

        /*If the request is raised for the second time within a short interval(<5 seconds),
        return the data from the cached version*/

        /*Send a request to the server if there was any data changed for this same request,
         * if the server responds back with none,
         * fetch the data from the cached version if available else, fetch from data base*/

        /*Finally if nothing works out from above, fetch the data from the server, using the
        blaze downloader*/
        getWallPostsFromWebService(mutableLiveData)
    }

    /**
     * This method will make a web service request and get all the wall posts from the server
     *
     * @param mutableLiveData A live list reference to update the UI
     */
    private fun getWallPostsFromWebService(mutableLiveData: MutableLiveData<ArrayList<WallPost>>) {
        val imageFeedUrl = "https://pastebin.com/raw/wgkJgazE"

        //Start a new coroutine
        CoroutineScope(Dispatchers.Default).launch {
            //Fetch the data from the data source
            val data: String? =
                BlazeDownloader.instance.getJsonData(imageFeedUrl)

            //Parse the received Json response
            data?.let {
                val jsonData = JsonData(data)

                //Post the result to the observer
                mutableLiveData.postValue(jsonData.getParsedData())
            }
        }
    }
}