package com.example.uzair.blazeimageloader.view

import ImageFeed
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uzair.blaze_downloader.BlazeDownloader
import com.example.uzair.blazeimageloader.R
import com.example.uzair.blazeimageloader.repository.IImageFeedRepository
import com.example.uzair.blazeimageloader.repository.ImageFeedRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private lateinit var job: CompletableJob
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set our layout file
        setContentView(R.layout.activity_main)

        //Get all the image feeds from the data source
        val imageFeedRepository: IImageFeedRepository = ImageFeedRepository()

//        //Set the recycler view adapter
//        val recyclerView: RecyclerView = findViewById(R.id.main_recycler_view)
//        recyclerView.layoutManager = GridLayoutManager(baseContext, 2)
//        recyclerView.animation = null
//        recyclerView.setHasFixedSize(true)
//
//        //Set the recycler view adapter
//        recyclerView.adapter = ImageFeedAdapter(this, imageFeedRepository.getAllImageFeeds())

        //Get the Json data
        var mutData: MutableLiveData<String> = MutableLiveData()
        mutData.observe(this, Observer { p ->

            println("Download of JSON is completed -> \n${mutData.value}")

//            var data =
            try {
                var gson = Gson()
//                val data = gson.fromJson(p, ImageFeed::class.java)

                var jarr: JSONArray = JSONArray(p)
                val datas: ArrayList<ImageFeed> = ArrayList()
                for (i in 0 until jarr.length()) {
                    val jsobj: JsonObject

                    val data =
                        gson.fromJson(jarr.getJSONObject(i).toString(), ImageFeed::class.java)
                    datas.add(data)
                }

                if (datas.size > 0) {
                    //Set the recycler view adapter
                    val recyclerView: RecyclerView = findViewById(R.id.main_recycler_view)
                    recyclerView.layoutManager = LinearLayoutManager(baseContext)
                    recyclerView.animation = null
                    recyclerView.setHasFixedSize(true)

                    //Set the recycler view adapter
                    recyclerView.adapter =
                        ImageFeedAdapter(this, datas)
                }
            } catch (e: JsonSyntaxException) {
                println("Json Parse -> $p")
            }
        })

        //Start a new coroutine
        CoroutineScope(Dispatchers.Default).launch {
            val data: String? =
                BlazeDownloader.instance.getJsonData("https://pastebin.com/raw/wgkJgazE")
            mutData.postValue(data)
        }

//        val data: String? =
//            BlazeDownloader.instance.fetchJsonData("https://pastebin.com/raw/wgkJgazE")
//        var parseData: ImageFeed? = null
//        try {
//            var gson = Gson()
//            parseData = gson.fromJson(data, ImageFeed::class.java)
//        } catch (e: JsonSyntaxException) {
//            println("Json Parse -> $parseData")
//        }
    }
}