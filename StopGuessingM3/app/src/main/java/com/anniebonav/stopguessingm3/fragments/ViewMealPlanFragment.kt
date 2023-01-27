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
    private lateinit var _context: MainActivity

    private lateinit var _mealPlanDao: MealPlanDao
    private lateinit var _blueprintDAO: BlueprintDAO
    private lateinit var _ingredientDAO: IngredientDAO

    private var _selectedMealPlanId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _selectedMealPlanId = arguments?.getInt("selectedMealPlan")!!
        var openedFromHome = arguments?.getInt("openedFromHome") //When they key is not found, the value passed is 0


        _context = activity as MainActivity
        _binding = FragmentViewMealPlanBinding.inflate(inflater, container, false)

        _mealPlanDao = StopGuessingDatabase.getDatabase(_context).mealPlanDao()
        _blueprintDAO = StopGuessingDatabase.getDatabase(_context).blueprintDao()
        _ingredientDAO = StopGuessingDatabase.getDatabase(_context).ingredientDao()

        //Removes back button if opened with Home
        if(openedFromHome == 1){
            binding.goBackButton.visibility = View.GONE
        }else{
            binding.goBackButton.visibility = View.VISIBLE
        }

        fillMealPlanInformation()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editMealPlanButton.setOnClickListener(){
            editMealPlan(_selectedMealPlanId!!)
        }

        binding.goBackButton.setOnClickListener(){
            findNavController().navigateUp()
        }
    }

    private fun fillMealPlanInformation(){
        Thread{
            val selectedMealPlan = _mealPlanDao.getMealPlan(_selectedMealPlanId!!)
            val blueprint = _blueprintDAO.getBlueprint(selectedMealPlan.blueprintId!!)

            val breakfastText = createText(selectedMealPlan.mealPlanBreakfasts.toString().split(","))
            val lunchText = createText(selectedMealPlan.mealPlanLunches.toString().split(","))
            val dinnerText = createText(selectedMealPlan.mealPlanDinners.toString().split(","))
            val morningSnackText = createText(selectedMealPlan.mealPlanMorningSnacks.toString().split(","))
            val eveningSnackText = createText(selectedMealPlan.mealPlanEveningSnacks.toString().split(","))

            Handler(Looper.getMainLooper()).post {
                binding.mealPlanName.text = selectedMealPlan.mealPlanName
                binding.blueprintName.text = blueprint.name

                binding.breakfastInformation.text = breakfastText
                binding.lunchInformation.text = lunchText
                binding.dinnerInformation.text = dinnerText
                binding.morningSnackInformation.text = morningSnackText
                binding.eveningSnackInformation.text = eveningSnackText
            }
        }.start()

    }

    private fun editMealPlan(mealPlanId: Int){
        val bundle = bundleOf("selectedMealPlan" to mealPlanId)
        findNavController().navigate(R.id.action_ViewMealPlanFragment_to_InteractsMealPlanFragment, bundle)
    }

    fun createText(ingredients : List<String>): String { //NoteToSelf: When the db sends an empty List query, there is an element [0] that = ""
        var counter = 0
        var ingredientsList = ""

        if(ingredients[0] != ""){
            while(counter < ingredients.count()){
                var currentUnit = listOf(ingredients[counter], ingredients[counter+1], ingredients[counter+2])
                var ingredients = _ingredientDAO.loadIngredientsByIds(currentUnit)

                for(ingredient in ingredients){
                    ingredientsList += ingredient.ingredientAmount.toString() + " " + ingredient.ingredientUnit + " of " + ingredient.ingredientName + "\n"
                }

                counter += 3
            }
        }
        return ingredientsList
    }

    override fun onStart() {
        super.onStart()
        //_context.openedMeals()
    }

}