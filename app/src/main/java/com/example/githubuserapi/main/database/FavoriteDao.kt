package com.example.githubuserapi.main.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: FavoriteUser)

    @Delete
    fun delete(favorite: FavoriteUser)

    @Query("SELECT * From favorite_user ORDER BY login ASC")
    fun getAllFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_user WHERE login = :login)")
    fun checkFavorite(login: String): LiveData<Boolean>
}