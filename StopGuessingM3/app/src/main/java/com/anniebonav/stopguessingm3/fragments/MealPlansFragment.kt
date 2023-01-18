package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.*
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlan
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlanDao
import com.anniebonav.stopguessingm3.recycler.MealPlanAdapter
import com.anniebonav.stopguessingm3.databinding.FragmentMealPlansBinding


class MealPlansFragment : Fragment() {
    private var _binding: FragmentMealPlansBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.

    private lateinit var _mealPlansRecycler: RecyclerView
    private lateinit var _mealPlanDAO: MealPlanDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as MainActivity
        _binding = FragmentMealPlansBinding.inflate(inflater, container, false)
        _mealPlansRecycler = binding.mpRecycler

        val mealPlansFactory = ViewModelFactoryUI(context);
        val model = ViewModelProvider(context, mealPlansFactory).get(UIViewModel::class.java)

        _mealPlanDAO = StopGuessingDatabase.getDatabase(context).mealPlanDao()
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        _mealPlansRecycler.layoutManager = linearLayoutManager

        model.currentMealPlans.observe(context, Observer { mealPlans ->
            _mealPlansRecycler.adapter = MealPlanAdapter(context, mealPlans, this::onMealPlanDeleteClicked, this::onMealPlanCardClicked, this::onMealPlanEditClicked, "this is just a test")
        })

        Thread{
            val currentMealPlans = _mealPlanDAO.getMealPlans()
            if(currentMealPlans.isEmpty()){
                createInitialMealPlans()
            }
        }.start()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.action_MealPlansFragment_to_InteractsMealPlanFragment)
        }
    }

    private fun createInitialMealPlans(){
        val initialMealPlan = MealPlan(null,1, "This is an initial meal plan",  "If you want to add another one, click on the + button!", "Lunches", "Dinners")
        _mealPlanDAO.insertAll(initialMealPlan)
    }

    private fun onMealPlanCardClicked(mealPlanId: Int){
        openMealPlan(mealPlanId)
    }

    private fun openMealPlan(mealPlanId: Int){
        val bundle = bundleOf("selectedMealPlan" to mealPlanId)
        findNavController().navigate(R.id.action_MealPlansFragment_to_ViewMealPlanFragment, bundle)
    }

    private fun onMealPlanEditClicked(mealPlanId: Int ){
        editMealPlan(mealPlanId)
    }

    private fun editMealPlan(mealPlanId: Int){
        val bundle = bundleOf("selectedMealPlan" to mealPlanId)
        findNavController().navigate(R.id.action_MealPlansFragment_to_InteractsMealPlanFragment, bundle)
    }

    private fun onMealPlanDeleteClicked(mealPlanId: Int){
        deleteMealPlan(mealPlanId)
    }

    private fun deleteMealPlan(mealPlanId: Int){
        Thread{
            val deletedMealPlan = _mealPlanDAO.getMealPlan(mealPlanId)
            _mealPlanDAO.delete(deletedMealPlan)

            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity as MainActivity, "${deletedMealPlan.mealPlanName} successfully deleted!", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}