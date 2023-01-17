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
import com.anniebonav.stopguessingm3.data.Ingredients.Ingredient
import com.anniebonav.stopguessingm3.data.Ingredients.IngredientDAO
import com.anniebonav.stopguessingm3.data.UIViewModelIngredients
import com.anniebonav.stopguessingm3.data.ViewModelFactoryIngredientsUI
import com.anniebonav.stopguessingm3.databinding.FragmentIngredientsBinding
import com.anniebonav.stopguessingm3.recycler.IngredientAdapter

class IngredientsFragment : Fragment() {
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    private lateinit var _ingredientsRecycler: RecyclerView
    private lateinit var _ingredientDAO: IngredientDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as MainActivity
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        _ingredientsRecycler = binding.ingredientsRecycler

        val ingredientsFactory = ViewModelFactoryIngredientsUI(context);
        val model = ViewModelProvider(context, ingredientsFactory).get(UIViewModelIngredients::class.java)

        _ingredientDAO = StopGuessingDatabase.getDatabase(context).ingredientDao()
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _ingredientsRecycler.layoutManager = linearLayoutManager

        model.currentIngredients.observe(context, Observer { ingredients ->
            _ingredientsRecycler.adapter = IngredientAdapter(context, ingredients, this::onIngredientDeleteClicked, this::onIngredientCardClicked, this::onIngredientEditClicked)
        })

        Thread{
            val currentIngredients = _ingredientDAO.getIngredients()
            if(currentIngredients.isEmpty()){
                createInitialIngredients()
            }else{
                //_ingredientDAO.delete(_ingredientDAO.getIngredient(1))
            }
        }.start()
        return binding.root

    }

    private fun createInitialIngredients(){
        _ingredientDAO.insertAll(
            Ingredient(null, "Chicken", "protein", 100, "grams"),
            Ingredient(null, "Ham", "protein", 150, "grams"),
            Ingredient(null, "Cottage cheese", "protein", 4, "tbsp"),

            Ingredient(null, "Strawberries", "carbs", 2, "cups"),
            Ingredient(null, "Rice", "carbs", 500, "grams"),
            Ingredient(null, "Beans", "carbs", 10, "ounces"),

            Ingredient(null, "Avocado", "fat", 100, "grams"),
            Ingredient(null, "Olive Oil", "fat", 2, "tsp"),
            Ingredient(null, "Coconut Oil", "fat", 10, "ml")
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.action_IngredientsFragment_to_InteractsIngredientFragment)
        }
    }

    private fun onIngredientCardClicked(ingredientId: Int){
        openIngredient(ingredientId)
    }

    private fun openIngredient(ingredientId: Int){
        val bundle = bundleOf("selectedIngredient" to ingredientId)
        findNavController().navigate(R.id.action_MealPlansFragment_to_ViewMealPlanFragment, bundle)
    }

    private fun onIngredientEditClicked(ingredientId: Int ){
        editIngredient(ingredientId)
    }

    private fun editIngredient(ingredientId: Int){
        val bundle = bundleOf("selectedIngredient" to ingredientId)
        findNavController().navigate(R.id.action_MealPlansFragment_to_InteractsMealPlanFragment, bundle)
    }

    private fun onIngredientDeleteClicked(ingredientId: Int){
        deleteIngredient(ingredientId)
    }

    private fun deleteIngredient(ingredientId: Int){
        Thread{
            val deletedIngredient = _ingredientDAO.getIngredient(ingredientId)
            _ingredientDAO.delete(deletedIngredient)

            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity as MainActivity, "${deletedIngredient.ingredientName} successfully deleted!", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}