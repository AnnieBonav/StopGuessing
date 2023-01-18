package com.anniebonav.stopguessingm3

import android.os.Bundle
import android.service.controls.actions.FloatAction
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.navigation.NavDirections
import com.anniebonav.stopguessingm3.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var _bottomNavigationView: BottomNavigationView
    private lateinit var _tabsNavigationView: TabLayout
    private lateinit var _topBarView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        //setSupportActionBar(binding.toolbar) //Can access toolbar without FindViewById because we have defined binding as the bind with AppVarConfiguration
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)


        //Personal Top Bar
        _topBarView = binding.topBar

        findViewById<FloatingActionButton>(R.id.aboutButton).setOnClickListener(){
            navController.navigate(R.id.action_global_aboutFragment)
            _topBarView.text = "About"
        }

        //Tabs navigation
        _tabsNavigationView = binding.mealsTabs
        _tabsNavigationView.visibility = View.GONE
        _tabsNavigationView.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.text.toString() == "Meal Plans"){
                    navController.navigate(R.id.MealPlansFragment)
                }
                else if(tab?.text.toString() == "Blueprints"){
                    navController.navigate(R.id.BlueprintsFragment)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })

        //Bottom navigation
        _bottomNavigationView = binding.bottomNavigation
        _bottomNavigationView.setOnItemSelectedListener{ item ->
            Log.d("Navigation", "Inside setOnClick")
            when(item.itemId){
                R.id.home->{
                    _tabsNavigationView.visibility = View.GONE
                    navController.navigate(R.id.action_global_HomeFragment)
                    _topBarView.text = "Home"
                    true
                }

                R.id.meals->{
                    var selectedTab =_tabsNavigationView.selectedTabPosition
                    if(selectedTab == 0){
                        navController.navigate(R.id.action_global_BlueprintsFragment) // I know 0 is blueprint
                    }else{
                        navController.navigate(R.id.action_global_MealPlansFragment) // I know 0 is blueprint
                    }

                    _tabsNavigationView.visibility = View.VISIBLE
                    _topBarView.text = "Meals"

                    true
                }

                R.id.ingredients->{
                    _tabsNavigationView.visibility = View.GONE
                    navController.navigate(R.id.action_global_IngredientsFragment)
                    _topBarView.text = "Ingredients"
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