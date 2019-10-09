package com.example.uzair.blazeimageloader.utils

import androidx.room.TypeConverter
import com.example.uzair.blazeimageloader.models.Categories
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * This class is responsible for converting the data objects that needs to be added into the
 * db which SQLite dose'nt support into formats that it supports and vice versa.
 */
class DbConverters {
    /**
     * Convert a flat string of json of lists back to list of strings object
     */
    @TypeConverter
    fun convertStringToListOfString(value: String): List<String> {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    /**
     * Convert a list of string objects into a flat json string to be
     * added into the db
     */
    @TypeConverter
    fun convertListOfStringToString(list: List<String>): String = Gson().toJson(list)

    /**
     * Convert a list of {@link Categories} objects into a flat json string to be
     * added into the db
     */
    @TypeConverter
    fun convertListOfCategoriesToString(value: List<Categories>): String = Gson().toJson(value)

    /**
     * Convert a flat string of json of lists of categories back to list of categories object
     */
    @TypeConverter
    fun convertStringToListOfCategories(value: String): List<Categories> {
        val listType = object : TypeToken<ArrayList<Categories>>() {}.type
        return Gson().fromJson(value, listType)
    }
}