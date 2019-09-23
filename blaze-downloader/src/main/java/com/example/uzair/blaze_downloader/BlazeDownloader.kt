package com.example.uzair.blaze_downloader

import android.graphics.Bitmap
import android.widget.ImageView
import com.example.uzair.blaze_downloader.cache.LruCache
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class BlazeDownloader {
    private val TAG = javaClass::getSimpleName.name
    var lruCacheSize = 8

    //Our in memory cache mechanism
    private val lruCache = LruCache(this)

    companion object {
        val instance = BlazeDownloader()
    }

    fun downloadImage(url: String, imageView: ImageView) {
        imageView.setImageBitmap(null)

        //Check if the image is already in the cache
        val imageModel = lruCache.getEntry(url)

        if (imageModel?.bitmap == null) {
            var job: Job? = null
            var bitmap: Bitmap? = null

            val newImageModel = ImageModel(url, bitmap, job)

            //Add the image model to the local cache
            lruCache.putEntry(url, newImageModel)
            job = CoroutineScope(IO).launch {
                delay(10_000)
                bitmap = AsyncImageDownloader(url).getImageFromCdn()

                if (bitmap != null) {
                    newImageModel.bitmap = bitmap

                    //Since the job is completed, make it null
                    newImageModel.job = null

                    //Set image in the cache
                    lruCache.putEntry(url, newImageModel)

                    withContext(Main) {
                        println("$TAG DEBUG : URL Download : Adding to -> $imageView the image from $url")
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }

            //Download from the internet

            newImageModel.job = job
        } else {
            println("$TAG DEBUG : CACHE : Adding to -> $imageView the image from $url")
            imageView.setImageBitmap(imageModel.bitmap)
        }
    }

    fun cancelDownloadingImage(url: String) {
        lruCache.getEntry(url)?.job?.cancel("Image downloading is cancelled")

        println("Blaze Downloader : Image downloading was cancelled")
    }
}