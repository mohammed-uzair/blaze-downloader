package com.example.uzair.blaze_downloader

import android.graphics.Bitmap
import android.widget.ImageView
import com.example.uzair.blaze_downloader.cache.LruCache
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class BlazeDownloader {
    private val TAG = javaClass::getSimpleName.name
    private var lruCacheSize = 8

    //Our in memory cache mechanism
    private val lruCache = LruCache().builder().setLruCache(lruCacheSize).build()

    companion object {
        val instance = BlazeDownloader()
    }

    fun downloadImage(url: String, imageView: ImageView) {
        //Check if the image is already in the cache
        val imageModel = lruCache.getEntry(url)

        if (imageModel?.bitmap == null) {
            var job: Job? = null
            var bitmap: Bitmap? = null

            val newImageModel = ImageModel(url, bitmap, job)

            //Add the image model to the local cache
            lruCache.putEntry(url, newImageModel)

            //Download from the internet
            job = CoroutineScope(IO).launch {
                bitmap = AsyncImageDownloader(url).getImageFromCdn()

                if (bitmap != null) {
                    newImageModel.bitmap = bitmap

                    //Since the job is completed, make it null
                    newImageModel.job = null

                    //Set image in the cache
                    lruCache.putEntry(url, newImageModel)

                    withContext(Main) {
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }

            newImageModel.job = job
        } else {
            imageView.setImageBitmap(imageModel.bitmap)
        }
    }

    fun cancelDownloadingImage(url: String) {
        lruCache.getEntry(url)?.job?.cancel("Image downloading is cancelled")

        println("Blaze Downloader : Image downloading was cancelled")
    }

    public fun setLruCache(lruCacheSize: Int) = apply { this.lruCacheSize = lruCacheSize }
}