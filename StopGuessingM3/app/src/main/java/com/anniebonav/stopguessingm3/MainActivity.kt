package com.anniebonav.stopguessingm3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.navigation.NavDirections
import com.anniebonav.stopguessingm3.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var _bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        //setSupportActionBar(binding.toolbar) //Can access toolbar without FindViewById because we have defined binding as the bind with AppVarConfiguration
        val topBar = findViewById<TextView>(R.id.topBar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)

        //Bottom navigation
        _bottomNavigationView = binding.bottomNavigation
        _bottomNavigationView.setOnItemSelectedListener{ item ->
            Log.d("Navigation", "Inside setOnClick")
            when(item.itemId){
                R.id.home->{
                    navController.navigate(R.id.HomeFragment)
                    topBar.text = "Home"
                    true
                }

                R.id.meals->{
                    navController.navigate(R.id.BlueprintsFragment)
                    topBar.text = "Meals"
                    true
                }

                R.id.ingredients->{
                    navController.navigate(R.id.IngredientsFragment)
                    topBar.text = "Ingredients"
                    true
                }
                else ->{
                    false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/
}