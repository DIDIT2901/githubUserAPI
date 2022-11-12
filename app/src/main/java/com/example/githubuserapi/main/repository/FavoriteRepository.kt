package com.example.githubuserapi.main.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapi.main.database.FavoriteDao
import com.example.githubuserapi.main.database.FavoriteRoomDatabase
import com.example.githubuserapi.main.database.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavoriteDao.getAllFavorite()

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute {mFavoriteDao.insert(favoriteUser)}
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute {mFavoriteDao.delete(favoriteUser)}
    }

    fun check(login: String): LiveData<Boolean> =
        mFavoriteDao.checkFavorite(login)
}