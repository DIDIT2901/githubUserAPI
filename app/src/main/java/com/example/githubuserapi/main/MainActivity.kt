package com.example.githubuserapi.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.R
import com.example.githubuserapi.databinding.ActivityMainBinding
import com.example.githubuserapi.main.data.ItemsItem
import com.example.githubuserapi.main.detail_user.DetailUserActivity
import com.example.githubuserapi.main.favorite_user.FavoriteUserActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val userViewModel by viewModels<UserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel.users.observe(this){
            usersData(it)
        }
        userViewModel.isLoading.observe(this){
            loading(it)
        }
        binding.btnSearch.setOnClickListener{view ->
            userViewModel.searchUser(binding.editTextQuery.text.toString())
            val input = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            input.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun usersData(users: List<ItemsItem>){
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            val adapter = UserAdapter(users)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
            adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallBack{
                override fun onItemClicked(data: ItemsItem) {
                    Intent(this@MainActivity, DetailUserActivity::class.java).also {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val darkModeButton = menu.findItem(R.id.dark_mode)
        val pref = SettingPreferences.getInstance(dataStore)
        val darkModeViewModel = ViewModelProvider(this, DarkModeViewModelFactory(pref)).get(DarkModeViewModel::class.java)
        darkModeViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            Log.d("isDarkModeActive", isDarkModeActive.toString())
            if (isDarkModeActive){
                darkModeButton.setIcon(R.drawable.ic_moon)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                darkModeViewModel.saveThemeSetting(true)
                darkModeButton.isChecked = isDarkModeActive
            } else {
                darkModeButton.setIcon(R.drawable.ic_day)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkModeViewModel.saveThemeSetting(false)
                darkModeButton.isChecked = isDarkModeActive
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val pref = SettingPreferences.getInstance(dataStore)
        val darkModeViewModel = ViewModelProvider(this, DarkModeViewModelFactory(pref)).get(DarkModeViewModel::class.java)
        when(item.itemId){
            R.id.favorite_menu -> {
                Intent(this, FavoriteUserActivity::class.java).also{
                    startActivity(it)
                }
            }
            R.id.dark_mode -> {
                item.isChecked = !item.isChecked
                darkModeViewModel.saveThemeSetting(item.isChecked)
                setUiMode(item, item.isChecked)
                Log.d("Switching Theme", "Done")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUiMode(item: MenuItem, isDarkModeActive: Boolean){
        if (isDarkModeActive){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            item.setIcon(R.drawable.ic_moon)
            Log.d("Dark Mode", "done dark mode")
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            item.setIcon(R.drawable.ic_day)
            Log.d("Dark Mode", "done light mode")
        }
    }
}