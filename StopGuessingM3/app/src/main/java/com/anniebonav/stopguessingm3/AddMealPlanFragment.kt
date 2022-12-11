package com.anniebonav.stopguessingm3

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.anniebonav.stopguessingm3.databinding.ActivityMainBinding
import com.anniebonav.stopguessingm3.databinding.FragmentAddMealPlanBinding
import com.anniebonav.stopguessingm3.databinding.FragmentMealplansBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mpViewModel = _addMPViewModel

        val db = Room.databaseBuilder(
            context, MealPlanDatabase::class.java, _mealPlansDatabase
        ).build()

        val mealPlanDao = db.mealPlanDao()

        val addMealPlan = binding.addMealPlan
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}