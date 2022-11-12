package com.example.githubuserapi.main.detail_user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.databinding.FragmentFollowersFragmentsBinding
import com.example.githubuserapi.main.UserAdapter
import com.example.githubuserapi.main.data.ItemsItem
import com.example.githubuserapi.main.detail_user.SectionPagerAdapter.Companion.USER

class FollowersFragments : Fragment() {
    private lateinit var binding: FragmentFollowersFragmentsBinding

    private val followersViewModel by viewModels<FollowersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersFragmentsBinding.inflate(inflater, container, false)

        followersViewModel.followers.observe(viewLifecycleOwner){ follower ->
            if (follower == null){
                val users = arguments?.getString(USER)?: ""
                followersViewModel.getListFollowers(users)
            } else{
                showRecycleView(follower)
            }
        }

        followersViewModel.isLoading.observe(viewLifecycleOwner){
            loading(it)
        }

        return binding.root
    }


    private fun showRecycleView(user: List<ItemsItem>){
        binding.apply {
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            val adapter = UserAdapter(user)
            rvFollowers.setHasFixedSize(true)
            rvFollowers.adapter = adapter
            adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallBack{
                override fun onItemClicked(data: ItemsItem) {
                    Intent(activity, DetailUserActivity::class.java).also {
                        it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                        startActivity(it)
                    }
                }
            })
        }
    }

    private fun loading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}