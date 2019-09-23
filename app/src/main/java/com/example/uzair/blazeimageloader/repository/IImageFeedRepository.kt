package com.example.uzair.blazeimageloader.repository

import com.example.uzair.blazeimageloader.model.ImageFeedModel

interface IImageFeedRepository {
    fun getAllImageFeeds(): List<ImageFeedModel>
}