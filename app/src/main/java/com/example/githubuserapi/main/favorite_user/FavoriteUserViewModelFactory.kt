package com.example.githubuserapi.main.favorite_user

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FavoriteUserViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)){
            return FavoriteUserViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: "+modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteUserViewModelFactory{
            if (INSTANCE == null){
                synchronized(FavoriteUserViewModelFactory::class.java){
                    INSTANCE = FavoriteUserViewModelFactory(application)
                }
            }
            return INSTANCE as FavoriteUserViewModelFactory
        }
    }
}