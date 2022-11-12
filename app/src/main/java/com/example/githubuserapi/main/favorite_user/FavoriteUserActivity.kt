package com.example.githubuserapi.main.favorite_user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.R
import com.example.githubuserapi.databinding.ActivityFavoriteUserBinding
import com.example.githubuserapi.main.FavoriteUserAdapter
import com.example.githubuserapi.main.database.FavoriteUser
import com.example.githubuserapi.main.detail_user.DetailUserActivity

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteUserBinding
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_user)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite Users"

        loading(true)
        favoriteUserViewModel = getViewModel(this)
        favoriteUserViewModel.favoriteListUser.observe(this){listUser ->
            Log.d("listUser Val", listUser.toString())
            loading(false)
            favoriteUserViewModel.favoriteListUser.observe(this){
                showFavoriteData(listUser)
            }
        }
    }

    private fun showFavoriteData(userFav: List<FavoriteUser>){
        binding.apply {
            rvUserFavorite.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            val adapter = FavoriteUserAdapter(userFav)
            rvUserFavorite.setHasFixedSize(true)
            rvUserFavorite.adapter = adapter
            adapter.setOnItemClickCallBack(object : FavoriteUserAdapter.OnItemClickCallBack{
                override fun onItemClicked(data: FavoriteUser) {
                    Intent(this@FavoriteUserActivity, DetailUserActivity::class.java).also {
                        it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                        startActivity(it)
                    }
                }
            })
        }
    }

    private fun getViewModel(activity: AppCompatActivity): FavoriteUserViewModel{
        val factory = FavoriteUserViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserViewModel::class.java)
    }

    private fun loading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}