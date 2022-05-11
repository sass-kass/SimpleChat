package com.example.simplechat.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.simplechat.R
import com.example.simplechat.databinding.ChatActivityBinding
import com.example.simplechat.databinding.ChatsFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity: AppCompatActivity() {

    private var _binding: ChatActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.chat_activity)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_chat) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            onDestinationChanged(destination.id)
        }
        binding.bottomNavigation.setOnNavigationItemSelectedListener(onBottomNavigationListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_chat) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun onDestinationChanged(currentDestination: Int) {
        when (currentDestination) {
            R.id.chats_fragment_nav -> {
                binding.bottomNavigation.selectedItemId = R.id.chats_fragment
            }
            R.id.users_fragment_nav -> {
                binding.bottomNavigation.selectedItemId = R.id.users_fragment
            }
            R.id.profile_fragment_nav -> {
                binding.bottomNavigation.selectedItemId = R.id.profile_fragment
            }
            else -> {
                binding.bottomNavigation.visibility = View.GONE
            }
        }
    }

    private val onBottomNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.chats_fragment -> {
                val navOptions =
                    NavOptions.Builder().setPopUpTo(R.id.nav_host_fragment_chat, true).build()
                if (isNotSameDestination(R.id.chats_fragment_nav)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment_chat)
                        .navigate(R.id.chats_fragment_nav, null, navOptions)
                }
                true
            }
            R.id.users_fragment -> {
                val navOptions =
                    NavOptions.Builder().setPopUpTo(R.id.nav_host_fragment_chat, true).build()
                if (isNotSameDestination(R.id.users_fragment_nav)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment_chat)
                        .navigate(R.id.users_fragment_nav, null, navOptions)
                }
                true
            }
            R.id.profile_fragment -> {
                val navOptions =
                    NavOptions.Builder().setPopUpTo(R.id.nav_host_fragment_chat, true).build()
                if (isNotSameDestination(R.id.profile_fragment_nav)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment_chat)
                        .navigate(R.id.profile_fragment_nav, null, navOptions)
                }
                true
            }
            else -> {
                true
            }
        }
    }

    private fun isNotSameDestination(destination: Int): Boolean {
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment_chat)
            .currentDestination!!.id
    }
}