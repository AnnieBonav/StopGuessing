package com.anniebonav.stopguessingm3.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.Ingredients.Ingredient
import com.anniebonav.stopguessingm3.data.Ingredients.IngredientDAO
import com.anniebonav.stopguessingm3.data.UIViewModelInteractIngredient
import com.anniebonav.stopguessingm3.databinding.FragmentInteractsIngredientBinding


class InteractsIngredientFragment : Fragment() {
    private var _binding: FragmentInteractsIngredientBinding? = null
    private val binding get() = _binding!!
    private val _ingredientViewModel: UIViewModelInteractIngredient by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as MainActivity
        _binding = FragmentInteractsIngredientBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.ingredientsViewModel = _ingredientViewModel

        val ingredientDao = StopGuessingDatabase.getDatabase(context).ingredientDao()

        //Category Selector
        val categories = arrayOf("Protein", "Carbs", "Fats")
        val categoriesAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, categories)
        binding.ingredientCategoryInput.setAdapter(categoriesAdapter)
        binding.ingredientCategoryInput.threshold = 1
        binding.ingredientCategoryInput.setAdapter(categoriesAdapter)
        binding.ingredientCategoryInput.setOnItemClickListener{ _, _, position, _ -> //parent, view, position, id
            if(position == 0){
                _ingredientViewModel.currentIngredientCategory.value = "protein"
            }else if (position == 1){
                _ingredientViewModel.currentIngredientCategory.value = "carbs"
            }else if (position == 2){
                _ingredientViewModel.currentIngredientCategory.value = "fats"
            }
        }

        //Measurements

        //0 Cup - cup
        //1 Gram - g
        //2 Kilogram - kg
        //3 Miligram - mg
        //4 Mililiter - ml
        //5 Ounce - oz
        //6 Tablespoon Tbsp
        //7 Teaspoon - Tsp

        //Measurement selector
        val measurements = arrayOf("Cup(s)", "Gram(s)", "Kilogram(s)", "Miligram(s)", "Mililiter(s)", "Ounce(s)", "Tablespoon(s)", "Teaspoon(s)")
        val measurementsAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, measurements)
        binding.ingredientMeasurementInput.setAdapter(measurementsAdapter)
        binding.ingredientMeasurementInput.threshold = 1
        binding.ingredientMeasurementInput.setAdapter(measurementsAdapter)
        binding.ingredientMeasurementInput.setOnItemClickListener{ _, _, position, _ ->
            if(position == 0){
                _ingredientViewModel.currentIngredientUnit.value = "cup"
            }else if (position == 1){
                _ingredientViewModel.currentIngredientUnit.value = "g"
            }else if (position == 2){
                _ingredientViewModel.currentIngredientUnit.value = "kg"
            }else if (position == 3){
                _ingredientViewModel.currentIngredientUnit.value = "mg"
            }else if (position == 4){
                _ingredientViewModel.currentIngredientUnit.value = "ml"
            }else if (position == 5){
                _ingredientViewModel.currentIngredientUnit.value = "oz"
            }else if (position == 6){
                _ingredientViewModel.currentIngredientUnit.value = "tbsp"
            }else if (position == 7){
                _ingredientViewModel.currentIngredientUnit.value = "tsp"
            }
        }

        if(arguments != null){ //If I am sending arguments, it means that I am updating an ingredient and not adding one. This way I reuse my View Model.
            var selectedIngredientId = arguments?.getInt("selectedIngredient")
            binding.crudActionButton.text = "Update Ingredient"
            binding.ingredientsInstruction.text = "Update Ingredient"
            Thread{
                val selectedIngredient = ingredientDao.getIngredient(selectedIngredientId!!)
                Handler(Looper.getMainLooper()).post {
                    _ingredientViewModel.currentIngredientName.value = selectedIngredient.ingredientName
                    _ingredientViewModel.currentIngredientCategory.value = selectedIngredient.ingredientCategory
                    _ingredientViewModel.currentIngredientAmount.value = selectedIngredient.ingredientAmount.toString()
                    _ingredientViewModel.currentIngredientUnit.value = selectedIngredient.ingredientUnit
                }
            }.start()

            binding.crudActionButton.setOnClickListener(){
                UpdateIngredient(ingredientDao, selectedIngredientId!!)
            }
        }else{
            binding.crudActionButton.text = "Add Ingredient"
            binding.ingredientsInstruction.text = "Add Ingredient"

            binding.crudActionButton.setOnClickListener(){
                CreateIngredient(ingredientDao)
            }
        }

        binding.goBackButton.setOnClickListener({
            findNavController().navigate(R.id.action_InteractsIngredientFragment_to_IngredientsFragment)
        })
        return binding.root
    }



    /*
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.ingredients_categories_menu, menu)
    }

    // Then, to handle clicks:
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.option_protein -> {
                Log.d("Selector", "Protein")
                true
            }
            R.id.option_carbs -> {
                Log.d("Selector", "Carbs")
                true
            }
            R.id.option_fats -> {
                Log.d("Selector", "Protein")
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
*/
    fun CreateIngredient(ingredientDAO: IngredientDAO){
        Log.d("Database", "Enters create ingredient")
        val ingredient = Ingredient(null, _ingredientViewModel.currentIngredientName.value.toString(), _ingredientViewModel.currentIngredientCategory.value.toString(), _ingredientViewModel.currentIngredientAmount.value!!.toInt(), _ingredientViewModel.currentIngredientUnit.value.toString())

        Thread {
            ingredientDAO.insertAll(ingredient)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity as MainActivity, "${ingredient.ingredientName} successfully created!", Toast.LENGTH_SHORT).show()
            }
        }.start()

        findNavController().navigate(R.id.action_InteractsIngredientFragment_to_IngredientsFragment)
    }

    fun UpdateIngredient(ingredientDAO: IngredientDAO, selectedIngredientId: Int){
        val ingredient = Ingredient(selectedIngredientId, _ingredientViewModel.currentIngredientName.value.toString(), _ingredientViewModel.currentIngredientCategory.value.toString(), _ingredientViewModel.currentIngredientAmount.value!!.toInt(), _ingredientViewModel.currentIngredientUnit.value.toString())

        Thread {
            ingredientDAO.update(ingredient)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity as MainActivity, "${ingredient.ingredientName} successfully updated!", Toast.LENGTH_SHORT).show()
            }
        }.start()

        //TODO: IN general, add data proof and stuff related to the database

        findNavController().navigate(R.id.action_InteractsIngredientFragment_to_IngredientsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}