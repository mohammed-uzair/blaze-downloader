package com.example.uzair.blazeimageloader.repository

import ImageFeed

interface IImageFeedRepository {
    fun getAllImageFeeds(): List<ImageFeed>
}