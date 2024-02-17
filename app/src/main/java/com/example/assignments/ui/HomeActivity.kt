package com.example.assignments.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.assignments.R
import com.example.assignments.databinding.ActivityHomeBinding
import com.example.assignments.db.ArticleDataBase
import com.example.assignments.repository.NRepository
import com.example.assignments.ui.fragments.DeadlinesFragment
import com.example.assignments.ui.fragments.FavouriteFragment
import com.example.assignments.ui.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
   // lateinit var newsNavHostFragment: NavHostFragment
    lateinit var  newViewModels: NewViewModels
    lateinit var  binding: ActivityHomeBinding






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)





        val nRepository = NRepository(ArticleDataBase.invoke(this))
        val viewModelProviderFectory = NewsViewModelProviderFectory(application,nRepository)
        newViewModels = ViewModelProvider(this,viewModelProviderFectory).get(NewViewModels::class.java)

         bottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        // Your code to set up the bottom navigation

       val navController = navHostFragment.navController
       binding.bottomNavigationView.setupWithNavController(navController)


    }

}