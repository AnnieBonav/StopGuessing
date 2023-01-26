package com.anniebonav.stopguessingm3.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.Ingredients.Ingredient
import com.anniebonav.stopguessingm3.data.Ingredients.IngredientDAO
import com.anniebonav.stopguessingm3.data.UIViewModel
import com.anniebonav.stopguessingm3.data.ViewModelFactoryUI
import com.anniebonav.stopguessingm3.databinding.FragmentIngredientsBinding
import com.anniebonav.stopguessingm3.recycler.IngredientAdapter
import kotlinx.coroutines.launch

class IngredientsFragment : Fragment() {
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!
    private lateinit var _context: MainActivity

    private lateinit var _ingredientsRecycler: RecyclerView
    private lateinit var _ingredientDAO: IngredientDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _context = activity as MainActivity
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        _ingredientsRecycler = binding.ingredientsRecycler

        val ingredientsFactory = ViewModelFactoryUI(_context)
        val model = ViewModelProvider(_context, ingredientsFactory).get(UIViewModel::class.java)

        _ingredientDAO = StopGuessingDatabase.getDatabase(_context).ingredientDao()
        val linearLayoutManager = LinearLayoutManager(_context, LinearLayoutManager.VERTICAL, false)
        _ingredientsRecycler.layoutManager = linearLayoutManager

        model.currentIngredients.observe(_context, Observer { ingredients ->
            var sortedIngredients = ingredients.sortedBy { it.ingredientName }

            _ingredientsRecycler.adapter = IngredientAdapter(_context, sortedIngredients, this::onIngredientCardClicked, this::onIngredientEditClicked, this::onIngredientDeleteClicked)
        })

        binding.actionButton.setOnClickListener(){
            val builder = AlertDialog.Builder(_context)
            builder.setMessage("Are you sure you want to delete this ingredient?")
            builder.setPositiveButton("Yes"){ p0, p1 ->
                Log.d("Annie", "Before")
                lifecycleScope.launch {
                    Log.d("Annie", "Deleted")
                    onResume()
                }
                p0.dismiss()
            }
            builder.setNegativeButton("No"){p0, p1 ->
                Log.d("Annie", "Dismiss")
                p0.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        _context.openedIngredients()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    //Not currently using card Clicked
    private fun onIngredientCardClicked(ingredientId: Int){
        openIngredient(ingredientId)
    }

    private fun openIngredient(ingredientId: Int){
        val bundle = bundleOf("selectedIngredient" to ingredientId)
        findNavController().navigate(R.id.action_IngredientsFragment_to_InteractsIngredientFragment, bundle)
    }

    private fun onIngredientEditClicked(ingredientId: Int ){
        editIngredient(ingredientId)
    }

    private fun editIngredient(ingredientId: Int){
        val bundle = bundleOf("selectedIngredient" to ingredientId)
        findNavController().navigate(R.id.action_IngredientsFragment_to_InteractsIngredientFragment, bundle)
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