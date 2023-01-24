package com.anniebonav.stopguessingm3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.data.Ingredients.Ingredient
import com.anniebonav.stopguessingm3.data.Ingredients.IngredientDAO
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlanDao
import com.anniebonav.stopguessingm3.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.selects.select
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var _bottomNavigationView: BottomNavigationView
    private lateinit var _tabsNavigationView: TabLayout
    private lateinit var _topBarView: TextView
    private lateinit var _titleLayoutView: LinearLayout
    private lateinit var _navController: NavController
    private lateinit var _aboutButton: FloatingActionButton
    private lateinit var _ingredientDAO: IngredientDAO
    private lateinit var _mealPlanDAO: MealPlanDao


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        _navController = findNavController(R.id.nav_host_fragment_content_main)

        _topBarView = binding.topBar

        _aboutButton = binding.aboutButton
        _aboutButton.setOnClickListener{ _navController.navigate(R.id.action_global_AboutFragment) }

        _tabsNavigationView = binding.mealsTabs
        addsTabsNavigation()

        _bottomNavigationView = binding.bottomNavigation
        addsBottomNavigationListener()

        _ingredientDAO = StopGuessingDatabase.getDatabase(applicationContext).ingredientDao()
        Thread{
            addInitialIngredients()
        }.start()

        _titleLayoutView = binding.titleLayout
        setDayText()

        _mealPlanDAO = StopGuessingDatabase.getDatabase(applicationContext).mealPlanDao()
        Thread{
            handleFirstFragment()
        }.start()

    }

    private fun addsTabsNavigation(){
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
    }

    private fun addsBottomNavigationListener(){
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

    private fun addInitialIngredients(){
        val currentIngredients = _ingredientDAO.getIngredients(true)
        if(currentIngredients.isEmpty()){
            createInitialIngredients()
        }
    }

    private fun handleFirstFragment(){
        val currentMealPlans = _mealPlanDAO.getMealPlans()
        if(currentMealPlans.isNotEmpty()){ //It always starts in Home, so, if it is empty, it does nothing, but if there are meal plans it changes to selected meal plan view
            val selectedMealPlan = _mealPlanDAO.getSelectedMealPlan()
            Handler(Looper.getMainLooper()).post {
                openSelectedMealPlan(selectedMealPlan.uid!!)
            }
            _titleLayoutView.visibility = View.GONE
        }
    }

    private fun openSelectedMealPlan(mealPlanId: Int){
        val openedFromHome = 1
        val bundle = bundleOf("selectedMealPlan" to mealPlanId, "openedFromHome" to openedFromHome)
        _navController.navigate(R.id.action_HomeFragment_to_ViewMealPlanFragment, bundle)
    }

    private fun setDayText(){
        val dayView = binding.homeTitleDay
        val calendar = Calendar.getInstance()
        val title: String = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Monday!"
            Calendar.TUESDAY -> "Tuesday!"
            Calendar.WEDNESDAY -> "Wednesday!"
            Calendar.THURSDAY -> "Thursday!"
            Calendar.FRIDAY -> "Friday!"
            Calendar.SATURDAY -> "Saturday!"
            Calendar.SUNDAY -> "Sunday!"
            else -> "Invalid Day"
        }
        dayView.text = title
    }

    private fun createInitialIngredients(){
        _ingredientDAO.insertAll(
            Ingredient(null, "Chicken", "protein", 100, "grams"),
            Ingredient(null, "Ham", "protein", 150, "grams"),
            Ingredient(null, "Cottage cheese", "protein", 4, "tbsp"),

            Ingredient(null, "Strawberries", "carbs", 2, "cups"),
            Ingredient(null, "Rice", "carbs", 500, "grams"),
            Ingredient(null, "Beans", "carbs", 10, "ounces"),

            Ingredient(null, "Avocado", "fats", 100, "grams"),
            Ingredient(null, "Olive Oil", "fats", 2, "tsp"),
            Ingredient(null, "Coconut Oil", "fats", 10, "ml")
        )
    }

    private fun createInitialBlueprint(){//TODO: change creation here

    }

    fun openedAbout(){
        _topBarView.text = "About"
        _tabsNavigationView.visibility = View.GONE
        _bottomNavigationView.visibility = View.GONE
        _aboutButton.visibility = View.GONE
    }

    fun openedHome(){
        _topBarView.text = "Home"
        _titleLayoutView.visibility = View.VISIBLE
        _tabsNavigationView.visibility = View.GONE
        _bottomNavigationView.visibility = View.VISIBLE
        _aboutButton.visibility = View.VISIBLE
    }

    fun openedIngredients(){
        _topBarView.text = "Ingredients"
        _titleLayoutView.visibility = View.GONE
        _tabsNavigationView.visibility = View.GONE
        _bottomNavigationView.visibility = View.VISIBLE
        _aboutButton.visibility = View.VISIBLE
    }

    fun openedMeals(){
        _topBarView.text = "Meals"
        _titleLayoutView.visibility = View.GONE
        _tabsNavigationView.visibility = View.VISIBLE
        _bottomNavigationView.visibility = View.VISIBLE
        _aboutButton.visibility = View.VISIBLE
    }
}