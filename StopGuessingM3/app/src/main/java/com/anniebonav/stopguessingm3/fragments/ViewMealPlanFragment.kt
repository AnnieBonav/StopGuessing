package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.Blueprints.BlueprintDAO
import com.anniebonav.stopguessingm3.data.Ingredients.IngredientDAO
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlanDao
import com.anniebonav.stopguessingm3.databinding.FragmentViewMealPlanBinding

class ViewMealPlanFragment : Fragment() {
    private var _binding: FragmentViewMealPlanBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.
    private lateinit var _mealPlanDao: MealPlanDao
    private lateinit var _blueprintDAO: BlueprintDAO
    private lateinit var _ingredientDAO: IngredientDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var selectedMealPlanId = arguments?.getInt("selectedMealPlan")

        val context = activity as MainActivity
        _binding = FragmentViewMealPlanBinding.inflate(inflater, container, false)

        _mealPlanDao = StopGuessingDatabase.getDatabase(context).mealPlanDao()
        _blueprintDAO = StopGuessingDatabase.getDatabase(context).blueprintDao()
        _ingredientDAO = StopGuessingDatabase.getDatabase(context).ingredientDao()

        Thread{
            val selectedMealPlan = _mealPlanDao.getMealPlan(selectedMealPlanId!!)
            val blueprint = _blueprintDAO.getBlueprint(selectedMealPlan.blueprintId!!)
            binding.mealPlanName.text = selectedMealPlan.mealPlanName
            binding.blueprintName.text = blueprint.name

            val breakfastText = createText(selectedMealPlan.mealPlanBreakfasts.toString().split(","))
            val lunchText = createText(selectedMealPlan.mealPlanLunches.toString().split(","))
            val dinnerText = createText(selectedMealPlan.mealPlanDinners.toString().split(","))

            binding.breakfastInformation.text = breakfastText
            binding.lunchInformation.text = lunchText
            binding.dinnerInformation.text = dinnerText
        }.start()

        return binding.root
    }

    fun createText(ingredients : List<String>): String {
        //Log.d("MealPlan", "$ingredients")
        var ingredients = _ingredientDAO.loadIngredientsByIds(ingredients)
        //Log.d("MealPlan", "$ingredients")

        var ingredientsList = ""
        var i = 0
        var units = ingredients.count()/3 //TODO: Make it appear titles
        for(ingredient in ingredients){
            if(i ==  0%3){

            }
            ingredientsList += ingredient.ingredientAmount.toString() + " " + ingredient.ingredientUnit + " of " + ingredient.ingredientName + "\n"
        }
        //Log.d("MealPlan", "$ingredientsList")
        return ingredientsList
    }

}