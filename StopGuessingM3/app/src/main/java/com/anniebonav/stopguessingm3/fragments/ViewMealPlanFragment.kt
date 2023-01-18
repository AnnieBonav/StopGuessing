package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.Blueprints.BlueprintDAO
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlanDao
import com.anniebonav.stopguessingm3.databinding.FragmentViewMealPlanBinding

class ViewMealPlanFragment : Fragment() {
    private var _binding: FragmentViewMealPlanBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.
    private lateinit var _mealPlanDao: MealPlanDao
    private lateinit var _blueprintDAO: BlueprintDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var selectedMealPlanId = arguments?.getInt("selectedMealPlan")

        val context = activity as MainActivity
        _binding = FragmentViewMealPlanBinding.inflate(inflater, container, false)

        _mealPlanDao = StopGuessingDatabase.getDatabase(context).mealPlanDao()
        _blueprintDAO = StopGuessingDatabase.getDatabase(context).blueprintDao()

        Thread{
            val selectedMealPlan = _mealPlanDao.getMealPlan(selectedMealPlanId!!)
            val blueprint = _blueprintDAO.getBlueprint(selectedMealPlan.blueprintId!!)
            binding.mealPlanName.text = selectedMealPlan.mealPlanName
            binding.blueprintName.text = blueprint.name
            //binding.breakfastInformation.text = selectedMealPlan.mealsAmount.toString()
        }.start()

        return binding.root
    }
}