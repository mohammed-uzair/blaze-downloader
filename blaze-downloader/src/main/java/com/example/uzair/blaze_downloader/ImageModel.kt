package com.example.uzair.blaze_downloader

import android.graphics.Bitmap
import kotlinx.coroutines.Job

data class ImageModel(val url : String, var bitmap: Bitmap?, var job: Job?)