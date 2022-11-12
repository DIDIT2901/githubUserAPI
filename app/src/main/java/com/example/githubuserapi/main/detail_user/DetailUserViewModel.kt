package com.example.githubuserapi.main.detail_user

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserapi.main.api.ApiConfig
import com.example.githubuserapi.main.data.DetailUserResponse
import com.example.githubuserapi.main.database.FavoriteUser
import com.example.githubuserapi.main.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application, username: String): AndroidViewModel(application) {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail : LiveData<DetailUserResponse> = _userDetail

    init {
        getUsersDetail(username = String())
    }

    val userLogin : LiveData<Boolean> = mFavoriteRepository.check(username)

    fun getUsersDetail(username: String){
        val client = ApiConfig.getApiService().getDetailUser(username)
        _isLoading.value = true
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _userDetail.value = response.body()
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private val _userFav = MutableLiveData<FavoriteUser>()

    fun insert(favoriteUser: FavoriteUser){
        mFavoriteRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser){
        mFavoriteRepository.delete(favoriteUser)
    }

    fun checkFavorite(): Boolean? {
        return userLogin.value
    }

    companion object {
        const val TAG = "DetailUserViewModel"
    }
}