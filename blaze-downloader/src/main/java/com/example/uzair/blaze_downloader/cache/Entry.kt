package com.example.uzair.blaze_downloader.cache

import com.example.uzair.blaze_downloader.DataModel

/**
 * This class is used for custom LRU cache algorithm
 */
internal class Entry {
    var value: DataModel? = null
    var key: String? = null
    var left: Entry? = null
    var right: Entry? = null
}