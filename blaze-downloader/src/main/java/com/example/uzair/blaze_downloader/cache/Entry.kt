package com.example.uzair.blaze_downloader.cache

import com.example.uzair.blaze_downloader.ImageModel

internal class Entry {
    var value: ImageModel? = null
    var key: String? = null
    var left: Entry? = null
    var right: Entry? = null
}