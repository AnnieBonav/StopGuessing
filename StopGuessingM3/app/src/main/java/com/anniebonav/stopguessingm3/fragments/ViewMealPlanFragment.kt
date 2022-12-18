package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.MealPlanDatabase
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.MealPlanDao
import com.anniebonav.stopguessingm3.databinding.FragmentViewMealPlanBinding
import kotlinx.coroutines.selects.select

class ViewMealPlanFragment : Fragment() {
    private var _binding: FragmentViewMealPlanBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.
    private lateinit var _mealPlanDAO: MealPlanDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var selectedMealPlanId = arguments?.getInt("selectedMealPlan")

        val context = activity as MainActivity
        _binding = FragmentViewMealPlanBinding.inflate(inflater, container, false)

        _mealPlanDAO = MealPlanDatabase.getDatabase(context).mealPlanDao()

        Thread{
            val selectedMealPlan = _mealPlanDAO.getMealPlan(selectedMealPlanId!!)
            binding.mealPlanName.text = selectedMealPlan.mealPlanName
            binding.mealPlanDescription.text = selectedMealPlan.mealPlanDescription
            binding.mealsNumber.text = selectedMealPlan.mealsAmount.toString()
            binding.snacksNumber.text = selectedMealPlan.snacksAmount.toString()
        }.start()

        return binding.root
    }

    private fun toast(text: String){
        val context = activity as MainActivity
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }
}