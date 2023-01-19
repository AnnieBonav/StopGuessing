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
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlan
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlanDao
import com.anniebonav.stopguessingm3.data.UIViewModelAddMealPlan
import com.anniebonav.stopguessingm3.databinding.FragmentInteractsMealPlanBinding

class InteractsMealPlanFragment : Fragment() {
    private var _binding: FragmentInteractsMealPlanBinding? = null
    private val binding get() = _binding!!
    private lateinit var _context: MainActivity

    private val _mealPlanViewModel: UIViewModelAddMealPlan by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _context = activity as MainActivity
        _binding = FragmentInteractsMealPlanBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.mpViewModel = _mealPlanViewModel

        val mealPlanDAO = StopGuessingDatabase.getDatabase(_context).mealPlanDao()
        val blueprintDAO = StopGuessingDatabase.getDatabase(_context).blueprintDao()

        if(arguments != null){ //If I am sending arguments, it means that I am updating a meal pLan and not adding one. This way I reusse my View Model.
            var selectedMealPlanId = arguments?.getInt("selectedMealPlan")
            binding.crudActionButton.text = "Update Meal Plan"

            Thread{
                val selectedMealPlan = mealPlanDAO.getMealPlan(selectedMealPlanId!!)
                val blueprint = blueprintDAO.getBlueprint(selectedMealPlan.blueprintId!!)
                Handler(Looper.getMainLooper()).post {
                    _mealPlanViewModel.currentMealPlanName.value = selectedMealPlan.mealPlanName
                    _mealPlanViewModel.currentMealPlanBlueprint.value = blueprint.name
                }
            }.start()

            binding.crudActionButton.setOnClickListener(){
                UpdateMealPlan(mealPlanDAO, selectedMealPlanId!!)
            }
        }else{
            binding.crudActionButton.text = "Create Meal Plan"

            binding.crudActionButton.setOnClickListener(){
                CreateMealPlan(mealPlanDAO)
            }

            Thread{
                val existingBlueprints = blueprintDAO.getBlueprints()
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
                        val selectedItem = parent.getItemAtPosition(position)
                        Log.d("Meals", "Slected: $selectedItem")
                    }
                }
            }.start()


        }

        return binding.root
    }

    fun CreateMealPlan(mealPlanDao: MealPlanDao){
        val mealPlan = MealPlan(null,1, _mealPlanViewModel.currentMealPlanName.value.toString(), "Breakfasts", "Lunches", "Dinners")
        Thread {
            mealPlanDao.insertAll(mealPlan)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity as MainActivity, "${mealPlan.mealPlanName} successfully created!", Toast.LENGTH_SHORT).show()
            }
        }.start()

        findNavController().navigate(R.id.action_InteractsMealPlanFragment_to_MealPlansFragment)
    }

    fun UpdateMealPlan(mealPlanDao: MealPlanDao, selectedMealPLanId: Int){
        //TODO: Implement Update MealPlan, can only update name and description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}