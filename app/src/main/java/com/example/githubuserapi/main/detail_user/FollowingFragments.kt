package com.example.githubuserapi.main.detail_user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.databinding.FragmentFollowingFragmentsBinding
import com.example.githubuserapi.main.UserAdapter
import com.example.githubuserapi.main.data.ItemsItem

class FollowingFragments : Fragment() {
    private lateinit var binding: FragmentFollowingFragmentsBinding
    private val followingViewModel by viewModels<FollowingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingFragmentsBinding.inflate(inflater, container, false)

        followingViewModel.following.observe(viewLifecycleOwner){ following ->
            if (following == null){
                val users = arguments?.getString(SectionPagerAdapter.USER)?: ""
                followingViewModel.getListFollowing(users)
            } else{
                showRecycleView(following)
            }
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner){
            loading(it)
        }

        return binding.root
    }

    private fun showRecycleView(user: List<ItemsItem>){
        binding.apply {
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            val adapter = UserAdapter(user)
            rvFollowing.setHasFixedSize(true)
            rvFollowing.adapter = adapter
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