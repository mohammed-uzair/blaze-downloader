package com.example.uzair.blaze_downloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class AsyncImageDownloader(private val url: String) {
    private val TAG = javaClass::getSimpleName.name

    fun getImageFromCdn(): Bitmap? {
        var bitmap: Bitmap? = null

        try {
            //Create a URL from the mentioned URL
            val url = URL(url)

            //Create a HTTP Connection
            val httpConnection = url.openConnection()

            //Connect to the url
            httpConnection.connect()

            //Get the URL data in an input stream
            val inputStream = httpConnection.getInputStream()

            bitmap = BitmapFactory.decodeStream(inputStream)

        } catch (e: IOException) {
            Log.e(TAG, "Could not connect to internet", e)
        } catch (e: MalformedURLException) {
            Log.e(TAG, "Invalid URL address", e)
        }

        return bitmap
    }
}