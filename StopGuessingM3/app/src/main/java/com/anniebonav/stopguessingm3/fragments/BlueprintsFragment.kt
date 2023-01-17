package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.Blueprints.Blueprint
import com.anniebonav.stopguessingm3.data.Blueprints.BlueprintDAO
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlan
import com.anniebonav.stopguessingm3.data.UIViewModel
import com.anniebonav.stopguessingm3.data.ViewModelFactoryUI
import com.anniebonav.stopguessingm3.databinding.FragmentBlueprintsBinding
import com.anniebonav.stopguessingm3.recycler.BlueprintAdapter

class BlueprintsFragment : Fragment() {
    private var _binding: FragmentBlueprintsBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.

    private lateinit var _blueprintsRecycler: RecyclerView
    private lateinit var _blueprintDAO: BlueprintDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as MainActivity
        _binding = FragmentBlueprintsBinding.inflate(inflater, container, false)
        _blueprintsRecycler = binding.blueprintsRecycler

        val ingredientsFactory = ViewModelFactoryUI(context);
        val model = ViewModelProvider(context, ingredientsFactory).get(UIViewModel::class.java)

        _blueprintDAO = StopGuessingDatabase.getDatabase(context).blueprintDao()
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _blueprintsRecycler.layoutManager = linearLayoutManager

        model.currentBlueprints.observe(context, Observer { blueprints ->
            _blueprintsRecycler.adapter = BlueprintAdapter(context, blueprints, this::onBlueprintDeleteClicked, this::onBlueprintCardClicked, this::onBlueprintEditClicked)
        })

        Thread{
            val currentIngredients = _blueprintDAO.getBlueprints()
            if(currentIngredients.isEmpty()){
                createInitialBlueprint()
            }else{
                //_ingredientDAO.delete(_ingredientDAO.getIngredient(1))
            }
        }.start()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.action_BlueprintsFragment_to_InteractsBlueprintsFragment)
        }
    }

    private fun createInitialBlueprint(){
        val initialBlueprint = Blueprint(null, "This is an initial Blueprint", 3, 2, 1, 0, 1)
        _blueprintDAO.insertAll(initialBlueprint)
    }

    private fun onBlueprintCardClicked(mealPlanId: Int){
        openBlueprint(mealPlanId)
    }

    private fun openBlueprint(blueprintId: Int){
        val bundle = bundleOf("selectedBlueprint" to blueprintId)
        findNavController().navigate(R.id.action_MealPlansFragment_to_ViewMealPlanFragment, bundle) //TODO change to blueprint
    }

    private fun onBlueprintEditClicked(blueprintId: Int ){
        editBlueprint(blueprintId)
    }

    private fun editBlueprint(blueprintId: Int){
        val bundle = bundleOf("selectedMealPlan" to blueprintId)
        findNavController().navigate(R.id.action_MealPlansFragment_to_InteractsMealPlanFragment, bundle) //Change
    }

    private fun onBlueprintDeleteClicked(blueprintId: Int){
        deleteBlueprint(blueprintId)
    }

    private fun deleteBlueprint(blueprintId: Int){
        Thread{
            val deletedBlueprint = _blueprintDAO.getBlueprint(blueprintId)
            _blueprintDAO.delete(deletedBlueprint)

            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity as MainActivity, "${deletedBlueprint.name} successfully deleted!", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}