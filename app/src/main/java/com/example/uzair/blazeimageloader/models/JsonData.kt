package com.example.uzair.blazeimageloader.models

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import org.json.JSONArray

class JsonData(private val data: String, private val context: Context) {
    companion object {
        private val TAG = this::class.java.simpleName
    }

    fun getParsedData(): ArrayList<WallPost> {
        //Create an empty list
        val dataList: ArrayList<WallPost> = ArrayList()

        try {
            //Convert the JSON String to JSON array(We received JSON array only from web service)
            val jsonArray = JSONArray(data)

            //Loop in the JSON array
            for (i in 0 until jsonArray.length()) {
                //Add each wall post object to the list
                dataList.add(
                    Gson().fromJson(jsonArray.getJSONObject(i).toString(), WallPost::class.java)
                )
            }
        } catch (exception: Exception) {
            Log.e(TAG, exception.message)
        }

        //Return the final list with all wall posts
        return dataList
    }
}