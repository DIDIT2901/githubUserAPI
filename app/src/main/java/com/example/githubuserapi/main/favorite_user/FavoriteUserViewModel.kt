package com.example.githubuserapi.main.favorite_user

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapi.main.database.FavoriteUser
import com.example.githubuserapi.main.repository.FavoriteRepository

class FavoriteUserViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    val favoriteListUser: LiveData<List<FavoriteUser>> = mFavoriteRepository.getAllFavorite()

}