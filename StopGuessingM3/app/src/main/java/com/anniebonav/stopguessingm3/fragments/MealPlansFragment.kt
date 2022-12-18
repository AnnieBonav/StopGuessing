package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import com.anniebonav.stopguessingm3.MealPlanDatabase
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.*
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

        val mealPlansFactory = ViewModelFactoryMealPlansUI(context);
        val model = ViewModelProvider(context, mealPlansFactory).get(UIViewModelMealPlans::class.java)
        _mealPlanDAO = MealPlanDatabase.getDatabase(context).mealPlanDao()
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        Thread {
            //TODO: Make this better than using thread everywhere
            val currentMealPlans = _mealPlanDAO.getAll()
            Log.d("Please", "Meals: $currentMealPlans")
        }.start()

        _mealPlansRecycler.layoutManager = linearLayoutManager

        model.currentMealPlans.observe(context, Observer { mealPlans ->
            _mealPlansRecycler.adapter = MealPlanAdapter(context, mealPlans, this::onMPDeleteClicked, this::onMPCardClicked, this::onMPEditClicked)
        })

        return binding.root
    }

    private fun createInitialMealPlans(){
        //TODO: whenever the app is started for the first time, there should be a baseline of meal plans to work with
        /*
        val mealPlansArrayList: ArrayList<MealPlanModel> = ArrayList<MealPlanModel>()mealPlansArrayList.add(MealPlanModel("Gym Time plan", "This is the plan I use on the days I go to the gym.", 1, 2))
        mealPlansArrayList.add(MealPlanModel("Day to day plan", "This is the plan I use on the days I do not go to the gym.", 3, 4))
        mealPlansArrayList.add(MealPlanModel("Summer body", "This is the plan I use on to cut and cardio.", 2, 4))
        mealPlansArrayList.add(MealPlanModel("Mexican ingredients", "Esta es la versión mexicana de mi plan.", 3, 4))
        mealPlansArrayList.add(MealPlanModel("Gym Time plan", "This is the plan I use on the days I go to the gym.", 1, 2))
        */
    }

    private fun onMPDeleteClicked(mealPlanId: Int){
        deleteIngredient(mealPlanId)
    }

    private fun deleteIngredient(mealPlanId: Int){
        Thread{
            val deletedMealPlan = _mealPlanDAO.getMealPlan(mealPlanId)
            _mealPlanDAO.delete(deletedMealPlan)

            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity as MainActivity, "${deletedMealPlan.mealPlanName} successfully deleted", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun onMPCardClicked(mealPlanId: Int){
        openMealPlan(mealPlanId)
    }

    private fun openMealPlan(mealPlanId: Int){
        val bundle = bundleOf("selectedMealPlan" to mealPlanId)
        findNavController().navigate(R.id.action_MealPlansFragment_to_viewMealPlanFragment, bundle)
    }

    private fun onMPEditClicked(mealPlanId: Int ){
        editMealPlan(mealPlanId)
    }

    private fun editMealPlan(mealPlanId: Int){
        val bundle = bundleOf("selectedMealPlan" to mealPlanId)
        findNavController().navigate(R.id.action_MealPlansFragment_to_AddMealPlanFragment, bundle)
    }

    private fun toast(text: String){
        val context = activity as MainActivity
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.action_MealPlansFragment_to_AddMealPlanFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}