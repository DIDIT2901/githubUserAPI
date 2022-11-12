package com.example.githubuserapi.main.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_user")
@Parcelize
data class FavoriteUser(
    @field:ColumnInfo(name = "id")
    @field:SerializedName("id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "login")
    @field:SerializedName("login")
    val login: String,

    @field:ColumnInfo(name = "avatar_url")
    @field:SerializedName("avatar_url")
    val avatar_url: String,
): Parcelable
