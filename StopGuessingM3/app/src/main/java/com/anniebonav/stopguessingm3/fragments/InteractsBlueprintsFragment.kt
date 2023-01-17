package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.UIViewModelInteractBlueprint
import com.anniebonav.stopguessingm3.databinding.FragmentInteractsBlueprintsBinding

class InteractsBlueprintsFragment : Fragment() {
    private var _binding: FragmentInteractsBlueprintsBinding? = null
    private val binding get() = _binding!!
    private val _blueprintViewModel: UIViewModelInteractBlueprint by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as MainActivity
        _binding = FragmentInteractsBlueprintsBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.blueprintsViewModel = _blueprintViewModel

        val blueprintDao = StopGuessingDatabase.getDatabase(context).ingredientDao()
        /*
        if(arguments != null){ //If I am sending arguments, it means that I am updating an ingredient and not adding one. This way I reuse my View Model.
            var selectedIngredientId = arguments?.getInt("selectedIngredient")
            binding.crudActionButton.text = "Update Ingredient"
            Thread{
                val selectedIngredient = ingredientDao.getIngredient(selectedIngredientId!!)
                Handler(Looper.getMainLooper()).post {
                    _ingredientViewModel.currentIngredientName.value = selectedIngredient.ingredientName
                    _ingredientViewModel.currentIngredientCategory.value = selectedIngredient.ingredientCategory
                    _ingredientViewModel.currentIngredientAmount.value = selectedIngredient.ingredientAmount.toString()
                    _ingredientViewModel.currentIngredientMeasurement.value = selectedIngredient.ingredientMeasurement
                }
            }.start()

            binding.crudActionButton.setOnClickListener(){
                UpdateIngredient(ingredientDao, selectedIngredientId!!)
            }
        }else{
            binding.crudActionButton.text = "Add Ingredient"

            binding.crudActionButton.setOnClickListener(){
                CreateIngredient(ingredientDao)
            }
        }*/
        return binding.root
    }
}