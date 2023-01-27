package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.Blueprints.BlueprintDAO
import com.anniebonav.stopguessingm3.data.Ingredients.IngredientDAO
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlan
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlanDao
import com.anniebonav.stopguessingm3.data.UIViewModelAddMealPlan
import com.anniebonav.stopguessingm3.databinding.FragmentInteractsMealPlanBinding
import kotlin.random.Random

class InteractsMealPlanFragment : Fragment() {
    private var _binding: FragmentInteractsMealPlanBinding? = null
    private val binding get() = _binding!!
    private lateinit var _context: MainActivity
    private lateinit var _mealPlanDAO: MealPlanDao
    private lateinit var _blueprintDAO: BlueprintDAO
    private lateinit var _ingredientDAO: IngredientDAO

    private val _mealPlanViewModel: UIViewModelAddMealPlan by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _context = activity as MainActivity
        _binding = FragmentInteractsMealPlanBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.mpViewModel = _mealPlanViewModel

        _mealPlanDAO = StopGuessingDatabase.getDatabase(_context).mealPlanDao()
        _blueprintDAO = StopGuessingDatabase.getDatabase(_context).blueprintDao()
        _ingredientDAO = StopGuessingDatabase.getDatabase(_context).ingredientDao()

        if(arguments != null){ //If I am sending arguments, it means that I am updating a meal pLan and not adding one. This way I reusse my View Model.
            var selectedMealPlanId = arguments?.getInt("selectedMealPlan")
            binding.mealPlanInstruction.text = "Update Meal Plan"
            binding.crudActionButton.text = "Update Meal Plan"
            binding.blueprintSelector.visibility = View.GONE

            Thread{
                val selectedMealPlan = _mealPlanDAO.getMealPlan(selectedMealPlanId!!)
                val blueprint = _blueprintDAO.getBlueprint(selectedMealPlan.blueprintId!!)
                Handler(Looper.getMainLooper()).post {
                    _mealPlanViewModel.currentMealPlanName.value = selectedMealPlan.mealPlanName
                    _mealPlanViewModel.currentMealPlanDescription.value = selectedMealPlan.mealPlanDescription
                    _mealPlanViewModel.currentMealPlanBlueprint.value = blueprint.name
                }
            }.start()

            binding.crudActionButton.setOnClickListener(){
                UpdateMealPlan(selectedMealPlanId!!)
            }
        }else{
            binding.crudActionButton.text = "Create Meal Plan"

            binding.crudActionButton.setOnClickListener(){
                CreateMealPlan()
            }

            Thread{
                val existingBlueprints = _blueprintDAO.getBlueprints()
                val existingBlueprintsNames = ArrayList<String>()

                for(blueprint in existingBlueprints){
                    existingBlueprintsNames.add(blueprint.name!!)
                }

                val categoriesAdapter = ArrayAdapter(_context, android.R.layout.simple_list_item_1, existingBlueprintsNames)
                Handler(Looper.getMainLooper()).post {
                    binding.selectedBlueprintInput.setAdapter(categoriesAdapter)
                    binding.selectedBlueprintInput.threshold = 1
                    binding.selectedBlueprintInput.setAdapter(categoriesAdapter)
                    binding.selectedBlueprintInput.setOnItemClickListener{ parent, _, position, id -> //parent, view, position, id
                        var selectedItem = parent.getItemAtPosition(position).toString()
                        _mealPlanViewModel.currentMealPlanBlueprint.value = selectedItem
                    }
                }
            }.start()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goBackButton.setOnClickListener(){
            findNavController().navigateUp()
        }
    }

    fun CreateMealPlan(){
        Thread {
            val existingMealPlans = _mealPlanDAO.getMealPlans()
            Log.d("UniqueMP", "Unique: $existingMealPlans")
            var uniqueMealPlan = 0
            try {
                val firstMealPlan = existingMealPlans[0].mealPlanName
                uniqueMealPlan = 0
                Log.d("UniqueMP", "False Unique: $uniqueMealPlan")
            }
            catch(e: Exception){
                uniqueMealPlan = 1
                Log.d("UniqueMP", "True Unique: $uniqueMealPlan")
            }

            val selectedBlueprint = _blueprintDAO.getBlueprint(_mealPlanViewModel.currentMealPlanBlueprint.value!!)

            val breakfastNeededUnits = selectedBlueprint.breakfastUnits
            val lunchNeededUnits = selectedBlueprint.lunchUnits
            val dinnerNeededUnits = selectedBlueprint.dinnerUnits
            val morningSnackNeededUnits = selectedBlueprint.morningSnackUnits
            val eveningSnackNeededUnits = selectedBlueprint.eveningSnackUnits
            Log.d("Meal", "Blueprint information: B:$breakfastNeededUnits L:$lunchNeededUnits D:$dinnerNeededUnits, MS:$morningSnackNeededUnits, ES:$eveningSnackNeededUnits")


            var createdBreakfast = createMeal(breakfastNeededUnits!!)
            var createdLunch = createMeal(lunchNeededUnits!!)
            var createdDinner = createMeal(dinnerNeededUnits!!)
            var createdMorningSnack = createMeal(morningSnackNeededUnits!!)
            var createdEveningSnack = createMeal(eveningSnackNeededUnits!!)

            val mealPlan = MealPlan(null, selectedBlueprint.uid, _mealPlanViewModel.currentMealPlanName.value.toString(), selectedBlueprint.name, createdBreakfast, createdLunch, createdDinner, createdMorningSnack, createdEveningSnack, uniqueMealPlan)

            _mealPlanDAO.insertAll(mealPlan)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(_context, "${mealPlan.mealPlanName} successfully created!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_InteractsMealPlanFragment_to_MealPlansFragment)
            }
        }.start()
    }

    fun createMeal(neededUnits: Int): String{
        var finalBlockString = ""
        for(i in 0 until neededUnits!!){
            val blockString = createBlocksUnit()
            if(i == neededUnits!! -1){
                finalBlockString += blockString
            }else{
                finalBlockString += "$blockString,"
            }
            //Log.d("Meal", "BlockString: $blockString")
        }

        Log.d("Meal", "FinalBlockString: $finalBlockString")
        return finalBlockString
    }

    fun createBlocksUnit(): String{
        val selectedProteinId = selectRandomIngredient("protein")
        val selectedCarbsId = selectRandomIngredient("carbs")
        val selectedFatsId = selectRandomIngredient("fats")
        //Log.d("Meal", "Protein: $selectedProteinId  Carbs: $selectedCarbsId   Fats: $selectedFatsId")
        val blockString = "$selectedProteinId,$selectedCarbsId,$selectedFatsId"
        return blockString
    }

    fun selectRandomIngredient(category: String): Int{
        val categoryIngredients = _ingredientDAO.getIngredientsByCategory(category)
        val randomNumber = Random.nextInt(0,categoryIngredients.count())
        val selectedIngredient = categoryIngredients[randomNumber].ingredientName
        Log.d("Meal", "$selectedIngredient")
        return categoryIngredients[randomNumber].uid!!
    }

    fun UpdateMealPlan(selectedMealPLanId: Int){
        Thread {
            val currentMealPlan = _mealPlanDAO.getMealPlan(selectedMealPLanId)
            val updatedMealPlan = MealPlan(currentMealPlan.uid, currentMealPlan.blueprintId, _mealPlanViewModel.currentMealPlanName.toString(), _mealPlanViewModel.currentMealPlanDescription.toString(), currentMealPlan.mealPlanBreakfasts, currentMealPlan.mealPlanLunches, currentMealPlan.mealPlanDinners, currentMealPlan.mealPlanMorningSnacks, currentMealPlan.mealPlanEveningSnacks, currentMealPlan.mealPlanIsSelected)

            _mealPlanDAO.update(currentMealPlan)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity as MainActivity, "${updatedMealPlan.mealPlanName} successfully updated!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_InteractsMealPlanFragment_to_MealPlansFragment)
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}