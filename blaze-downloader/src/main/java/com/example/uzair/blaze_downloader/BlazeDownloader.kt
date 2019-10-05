package com.example.uzair.blaze_downloader

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.example.uzair.blaze_downloader.cache.LruCache
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class BlazeDownloader {
    private val TAG = javaClass::getSimpleName.name
    var lruCacheSize = 8

    //Our in memory cache mechanism
    private val jobMap = HashMap<ImageView, Job>()
    private val lruCache = LruCache(this)

    companion object {
        val instance = BlazeDownloader()
    }

    fun downloadImage(url: String, imageView: ImageView) {
        println("My Debug : $imageView")
//        imageView.setImageBitmap(null)

        //Check if the image is already in the cache
        val imageModel = lruCache.getEntry(url)

        if (imageModel?.bitmap == null) {
            var bitmap: Bitmap? = null

            val newImageModel = ImageModel(url, bitmap)

            //Add the image model to the local cache
            lruCache.putEntry(url, newImageModel)
            val job = CoroutineScope(IO).launch {
                delay(10_000)
                bitmap = ImageDownloader(url).getImageFromCdn()

                if (bitmap != null) {
                    newImageModel.bitmap = bitmap

                    //Set image in the cache
                    lruCache.putEntry(url, newImageModel)

                    //Remove the job from the job map
                    jobMap.remove(imageView)

                    withContext(Main) {
                        println("$TAG DEBUG : URL Download : Adding to -> $imageView the image from $url")
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }

            //Add the image view in the job list
            jobMap[imageView] = job
        } else {
            println("$TAG DEBUG : CACHE : Adding to -> $imageView the image from $url")
            imageView.setImageBitmap(imageModel.bitmap)
        }
    }

    fun cancelDownloadingImage(imageView: ImageView) {
        jobMap[imageView]?.cancel("The job is cancelled")

        //Remove the job from the job map
        jobMap.remove(imageView)
    }

//    fun fetchJsonData(url: String): String? {
//        CoroutineScope(IO).launch {
//            val one = withContext(IO) { getJsonData(url) }
////        one.await()
//            return@runBlocking one
//        }
//    }

    public suspend fun getJsonData(url: String): String? {
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null

        try {
            val url = URL(url)
            connection = url.openConnection() as HttpURLConnection
            connection.connect()


            val stream = connection.inputStream

            reader = BufferedReader(InputStreamReader(stream))

            val buffer = StringBuffer()
            var line = reader.readLine()

            while (line != null) {
                buffer.append(line + "\n")
//                Log.d("Response: ", "> $line")   //here you will get whole response...... :-)

                line = reader.readLine()
            }

            return buffer.toString()


        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return null
    }
}