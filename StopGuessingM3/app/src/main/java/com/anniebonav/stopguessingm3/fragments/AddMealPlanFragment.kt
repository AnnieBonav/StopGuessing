package com.anniebonav.stopguessingm3.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.MealPlanDatabase
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.MealPlan
import com.anniebonav.stopguessingm3.data.MealPlanDao
import com.anniebonav.stopguessingm3.data.UIViewModelAddMealPlan
import com.anniebonav.stopguessingm3.databinding.FragmentAddMealPlanBinding

class AddMealPlanFragment : Fragment() {
    private var _binding: FragmentAddMealPlanBinding? = null
    private val binding get() = _binding!!

    private val _mealPlansDatabase: String = "MEALPLANS_DATABASE"

    private val _addMPViewModel: UIViewModelAddMealPlan by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as MainActivity
        val mainViewModel = ViewModelProvider(context)
        _binding = FragmentAddMealPlanBinding.inflate(inflater, container,false)

        if(arguments != null){ //If I am sending arguments, it means that I am updating a meal pLan and not adding one. This way I reusse my View Model.
            binding.crudActionButton.text = "Update Meal Plan"
        }else{
            binding.crudActionButton.text = "Create Meal Plan"
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.mpViewModel = _addMPViewModel

        val mealPlanDao = MealPlanDatabase.getDatabase(context).mealPlanDao()

        val addMealPlan = binding.crudActionButton
        addMealPlan?.setOnClickListener(){
            SaveMealPlan(binding.root, context, mealPlanDao)
        }

        return binding.root
    }

    fun SaveMealPlan(view: View, context: Context, mealPlanDao: MealPlanDao){
        val mealPlan = MealPlan(null, _addMPViewModel.currentMealPlanName.value.toString(), _addMPViewModel.currentMealPlanDescription.value.toString(), _addMPViewModel.currentMealsAmount.value!!.toInt(), _addMPViewModel.currentSnacksAmount.value!!.toInt())
        Thread {
            mealPlanDao.insertAll(mealPlan)
            val users: List<MealPlan> = mealPlanDao.getAll()
            Log.d("User", "MyUser: $mealPlan   Users: $users")
        }.start()

        findNavController().navigate(R.id.action_AddMealPlanFragment_to_MealPlansFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    //TODO: Need to fix the size of teh card, because it changes iwth the description (as it should) but it makes it cropped after a few lines

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}