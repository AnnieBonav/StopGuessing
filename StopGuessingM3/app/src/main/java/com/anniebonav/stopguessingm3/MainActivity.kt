package com.anniebonav.stopguessingm3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import android.view.View
import android.widget.TextView
import androidx.navigation.NavController
import com.anniebonav.stopguessingm3.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var _bottomNavigationView: BottomNavigationView
    private lateinit var _tabsNavigationView: TabLayout
    private lateinit var _topBarView: TextView
    private lateinit var _navController: NavController
    private lateinit var _aboutButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        _navController = findNavController(R.id.nav_host_fragment_content_main)

        _topBarView = binding.topBar

        _aboutButton = binding.aboutButton
        _aboutButton.setOnClickListener{ _navController.navigate(R.id.action_global_aboutFragment) }

        _tabsNavigationView = binding.mealsTabs
        _tabsNavigationView.visibility = View.GONE //Tabs from meals start hidden

        _tabsNavigationView.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.text.toString() == "Meal Plans"){
                    _navController.navigate(R.id.MealPlansFragment)
                }
                else if(tab?.text.toString() == "Blueprints"){
                    _navController.navigate(R.id.BlueprintsFragment)
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
            when(item.itemId){
                R.id.home->{
                    _navController.navigate(R.id.action_global_HomeFragment)
                    true
                }
                R.id.meals->{
                    val selectedTab =_tabsNavigationView.selectedTabPosition
                    if(selectedTab == 0){
                        _navController.navigate(R.id.action_global_BlueprintsFragment)
                    }else{
                        _navController.navigate(R.id.action_global_MealPlansFragment)
                    }
                    true
                }
                R.id.ingredients->{
                    _navController.navigate(R.id.action_global_IngredientsFragment)
                    true
                }
                else ->{
                    false
                }
            }
        }
    }

    fun openedAbout(){
        _topBarView.text = "About"
        _tabsNavigationView.visibility = View.GONE
        _bottomNavigationView.visibility = View.GONE
        _aboutButton.visibility = View.GONE
    }

    fun openedHome(){
        _topBarView.text = "Home"
        _tabsNavigationView.visibility = View.GONE
        _bottomNavigationView.visibility = View.VISIBLE
        _aboutButton.visibility = View.VISIBLE
    }

    fun openedIngredients(){
        _topBarView.text = "Ingredients"
        _tabsNavigationView.visibility = View.GONE
        _bottomNavigationView.visibility = View.VISIBLE
        _aboutButton.visibility = View.VISIBLE
    }

    fun openedMeals(){
        _topBarView.text = "Meals"
        _tabsNavigationView.visibility = View.VISIBLE
        _bottomNavigationView.visibility = View.VISIBLE
        _aboutButton.visibility = View.VISIBLE
    }
}