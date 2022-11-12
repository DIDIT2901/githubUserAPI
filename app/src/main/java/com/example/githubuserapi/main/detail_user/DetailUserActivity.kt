package com.example.githubuserapi.main.detail_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapi.R
import com.example.githubuserapi.databinding.ActivityDetailUserBinding
import com.example.githubuserapi.main.data.DetailUserResponse
import com.example.githubuserapi.main.database.FavoriteUser
import com.example.githubuserapi.main.favorite_user.FavoriteUserViewModel
import com.example.githubuserapi.main.favorite_user.FavoriteUserViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel
    private lateinit var detailUserViewModel : DetailUserViewModel

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.tab_1,
        R.string.tab_2
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.extras?.get(EXTRA_USERNAME).toString()
        val favorite = intent.getParcelableExtra<FavoriteUser>(EXTRA_FAVORITE)
        val usernameData: String = favorite?.login ?: username
        val viewModelFactory = ViewModelFactory(this@DetailUserActivity.application, usernameData)
        detailUserViewModel = ViewModelProvider(this@DetailUserActivity, viewModelFactory)[DetailUserViewModel::class.java]

        val sectionPagerAdapter = SectionPagerAdapter(this, username)
        binding.apply {
            val viewPager: ViewPager2 = binding.viewPager
            viewPager.offscreenPageLimit = 2
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = binding.tabLayout
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            supportActionBar?.elevation = 0f
        }

        detailUserViewModel.userDetail.observe(this){
            detailUserData(it)
        }
        detailUserViewModel.isLoading.observe(this){
            loading(it)
        }

        favoriteUserViewModel = getFavoriteViewModel(this@DetailUserActivity)

        detailUserViewModel.getUsersDetail(username)

        detailUserViewModel.userLogin.observe(this){ isFavorited ->
            if (isFavorited){
                binding.toggleFavorite.isChecked = true
            } else {
                binding.toggleFavorite.isChecked = false
            }

            binding.toggleFavorite.setOnClickListener{
                val dataBeforeInsert = detailUserViewModel.userDetail.value
                if (dataBeforeInsert != null){
                    val dataInsert = favorite?: FavoriteUser(dataBeforeInsert.id, dataBeforeInsert.login, dataBeforeInsert.avatarUrl)
                    if (detailUserViewModel.checkFavorite()!!) {
                        detailUserViewModel.delete(dataInsert)
                        binding.toggleFavorite.isChecked = false
                        Log.d("FavBut", "done delete favorite")
                    } else {
                        detailUserViewModel.insert(dataInsert)
                        binding.toggleFavorite.isChecked = true
                        Log.d("FavBut", "done insert favorite")
                    }
                }
            }
        }
    }

    private fun getFavoriteViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = FavoriteUserViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserViewModel::class.java)
    }

    private fun detailUserData(user: DetailUserResponse){
        binding.apply {
            tvDetailUsername.text = user.login
            tvName.text = user.name
            tvFollowers.text = "${user.followers} Followers"
            tvFollowing.text = "${user.following} Following"
            tvRepository.text = "${user.publicRepos} Repository"
            tvCompany.text = "${user.company ?: "Unknown Company"}"
            tvLocation.text = "${user.location ?: "Unknown Location"}"
            Glide.with(this@DetailUserActivity).load(user.avatarUrl).circleCrop().into(ivAvatar)
            toggleFavorite.visibility = View.VISIBLE
        }
    }


    private fun loading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_FAVORITE = "extra_favorite"
    }

}