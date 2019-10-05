package com.example.uzair.blazeimageloader.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uzair.blazeimageloader.repository.IWallPostsRepository
import com.example.uzair.blazeimageloader.repository.WallPostsRepository

/**
 * This is the viewmodel for the home fragment and does all the processing for the home UI data here.
 */
class HomeViewModel : ViewModel() {
    /*Get the Json from the web server using the blaze downloader with the
    provided url.*/
    val wallPostsRepository: IWallPostsRepository = WallPostsRepository()

//    wallPostsRepository.getAllWallPosts()
}