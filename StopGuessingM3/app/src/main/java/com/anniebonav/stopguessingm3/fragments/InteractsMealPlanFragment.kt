package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.MealPlan
import com.anniebonav.stopguessingm3.data.MealPlanDao
import com.anniebonav.stopguessingm3.data.UIViewModelAddMealPlan
import com.anniebonav.stopguessingm3.databinding.FragmentInteractsMealPlanBinding

class InteractsMealPlanFragment : Fragment() {
    private var _binding: FragmentInteractsMealPlanBinding? = null
    private val binding get() = _binding!!
    private val _mealPlanViewModel: UIViewModelAddMealPlan by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as MainActivity
        _binding = FragmentInteractsMealPlanBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.mpViewModel = _mealPlanViewModel

        val mealPlanDao = StopGuessingDatabase.getDatabase(context).mealPlanDao()

        if(arguments != null){ //If I am sending arguments, it means that I am updating a meal pLan and not adding one. This way I reusse my View Model.
            var selectedMealPlanId = arguments?.getInt("selectedMealPlan")
            binding.crudActionButton.text = "Update Meal Plan"
            Thread{
                val selectedMealPlan = mealPlanDao.getMealPlan(selectedMealPlanId!!)
                Handler(Looper.getMainLooper()).post {
                    _mealPlanViewModel.currentMealPlanName.value = selectedMealPlan.mealPlanName
                    _mealPlanViewModel.currentMealPlanDescription.value = selectedMealPlan.mealPlanDescription
                    _mealPlanViewModel.currentMealsAmount.value = selectedMealPlan.mealsAmount.toString()
                    _mealPlanViewModel.currentSnacksAmount.value = selectedMealPlan.snacksAmount.toString()
                }
            }.start()

            binding.crudActionButton.setOnClickListener(){
                UpdateMealPlan(mealPlanDao, selectedMealPlanId!!)
            }
        }else{
            binding.crudActionButton.text = "Create Meal Plan"

            binding.crudActionButton.setOnClickListener(){
                CreateMealPlan(mealPlanDao)
            }
        }

        return binding.root
    }

    fun CreateMealPlan(mealPlanDao: MealPlanDao){
        val mealPlan = MealPlan(null, _mealPlanViewModel.currentMealPlanName.value.toString(), _mealPlanViewModel.currentMealPlanDescription.value.toString(), _mealPlanViewModel.currentMealsAmount.value!!.toInt(), _mealPlanViewModel.currentSnacksAmount.value!!.toInt())

        Thread {
            mealPlanDao.insertAll(mealPlan)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity as MainActivity, "${mealPlan.mealPlanName} successfully created!", Toast.LENGTH_SHORT).show()
            }
        }.start()

        findNavController().navigate(R.id.action_InteractsMealPlanFragment_to_MealPlansFragment)
    }

    fun UpdateMealPlan(mealPlanDao: MealPlanDao, selectedMealPLanId: Int){
        val mealPlan = MealPlan(selectedMealPLanId, _mealPlanViewModel.currentMealPlanName.value.toString(), _mealPlanViewModel.currentMealPlanDescription.value.toString(), _mealPlanViewModel.currentMealsAmount.value!!.toInt(), _mealPlanViewModel.currentSnacksAmount.value!!.toInt())

        Thread {
            mealPlanDao.update(mealPlan)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity as MainActivity, "${mealPlan.mealPlanName} successfully updated!", Toast.LENGTH_SHORT).show()
            }
        }.start()

        //TODO: IN general, add data proof and stuff related to the database

        findNavController().navigate(R.id.action_InteractsMealPlanFragment_to_MealPlansFragment)
    }

    //TODO: Need to fix the size of teh card, because it changes iwth the description (as it should) but it makes it cropped after a few lines

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}