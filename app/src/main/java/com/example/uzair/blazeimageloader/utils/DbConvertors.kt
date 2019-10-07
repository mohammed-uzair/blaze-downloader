package com.example.uzair.blazeimageloader.utils

import androidx.room.TypeConverter
import com.example.uzair.blazeimageloader.models.Categories
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DbConvertors {
    @TypeConverter
    fun convertStringToListOfString(value: String): List<String> {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun convertListOfStringToString(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun convertListOfCategoriesToString(value: List<Categories>): String = Gson().toJson(value)

    @TypeConverter
    fun convertStringToListOfCategories(value: String): List<Categories> {
        val listType = object : TypeToken<ArrayList<Categories>>() {}.type
        return Gson().fromJson(value, listType)
    }
}