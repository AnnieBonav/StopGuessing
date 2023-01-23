package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.R
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

            var inside = selectedMealPlan.mealPlanBreakfasts.toString().split(',')
            Log.d("MealPlan", "$inside")

            val breakfastText = createText(selectedMealPlan.mealPlanBreakfasts.toString().split(","))
            val lunchText = createText(selectedMealPlan.mealPlanLunches.toString().split(","))
            val dinnerText = createText(selectedMealPlan.mealPlanDinners.toString().split(","))
            val morningSnackText = createText(selectedMealPlan.mealPlanMorningSnacks.toString().split(","))
            val eveningSnackText = createText(selectedMealPlan.mealPlanEveningSnacks.toString().split(","))

            Handler(Looper.getMainLooper()).post {

                binding.breakfastInformation.text = breakfastText
                binding.lunchInformation.text = lunchText
                binding.dinnerInformation.text = dinnerText
                binding.morningSnackInformation.text = morningSnackText
                binding.eveningSnackInformation.text = eveningSnackText
            }
        }.start()

        binding.editMealPlanButton.setOnClickListener(){
            editMealPlan(selectedMealPlanId!!)
        }

        binding.goBackButton.setOnClickListener(){
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun editMealPlan(mealPlanId: Int){
        val bundle = bundleOf("selectedMealPlan" to mealPlanId)
        findNavController().navigate(R.id.action_ViewMealPlanFragment_to_InteractsMealPlanFragment, bundle)
    }

    fun createText(ingredients : List<String>): String {
        var myCount = ingredients.count() //NoteToSelf: WHen the db sends an empty query, there is an element [0] that = ""
        var counter = 0
        var ingredientsList = ""

        if(ingredients[0] != ""){
            var ingredientsCounter = ingredients.count()
            while(counter < ingredients.count()){
                var currentUnit = listOf(ingredients[counter], ingredients[counter+1], ingredients[counter+2])
                var ingredients = _ingredientDAO.loadIngredientsByIds(currentUnit)

                for(ingredient in ingredients){
                    ingredientsList += ingredient.ingredientAmount.toString() + " " + ingredient.ingredientUnit + " of " + ingredient.ingredientName + "\n"
                }

                counter += 3
            }
            //Log.d("MealPlan", "$ingredients")
            //var units = ingredients.count()/3 //TODO: Make it appear titles
        }

        Log.d("MealPlan", "$ingredientsList")
        return ingredientsList
    }

}