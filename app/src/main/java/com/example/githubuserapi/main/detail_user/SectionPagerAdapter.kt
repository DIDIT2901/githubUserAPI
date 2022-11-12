package com.example.githubuserapi.main.detail_user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity, private val username: String) :  FragmentStateAdapter(activity){
    override fun getItemCount(): Int{
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> {
                FollowersFragments().apply {
                    arguments = Bundle().apply {
                        putString(USER, username)
                    }
                }
            }
            else -> {
                FollowingFragments().apply {
                    arguments = Bundle().apply {
                        putString(USER, username)
                    }
                }
            }
        }
    }

    companion object {
        const val USER = "DIDIT2901"
    }
}