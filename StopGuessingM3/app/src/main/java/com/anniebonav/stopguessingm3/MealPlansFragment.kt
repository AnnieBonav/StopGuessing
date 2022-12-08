package com.anniebonav.stopguessingm3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.databinding.FragmentMealPlansBinding

class MealPlansFragment : Fragment() {
    private var _binding: FragmentMealPlansBinding? = null
    private lateinit var myDb: DatabaseHandler


    private lateinit var mealPlansRecycler: RecyclerView
    private val initialMealPlans = listOf("MealPlan1", "MealPlan2", "MealPlan3")
    private val mpAdapter = MealPlansAdapter(initialMealPlans, this::onMPClicked)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealPlansBinding.inflate(inflater, container, false)
        myDb = DatabaseHandler(activity as MainActivity)

        val addMealPlan = binding.addMealPlan
        addMealPlan?.setOnClickListener(){
            SaveMealPlan(binding.root)
        }

        //RECYCLER
        mealPlansRecycler = binding.mpRecycler
        mealPlansRecycler.adapter = mpAdapter

        return binding.root
    }

    private fun onMPClicked(ingredientName: String){
        duplicateIngredient(ingredientName)
    }

    private fun duplicateIngredient(ingredientName: String){
        //toast("Duplicated $ingredientName")
        mpAdapter.addIngredient(ingredientName)
    }

    private fun toast(text: String){
        val context = this
        //val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        //toast.show()
    }

    fun SaveMealPlan(view: View){
        myDb.insertData(view.findViewById<EditText>(R.id.mpNameInput).text.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.seeMealPlansButton.setOnClickListener {
            findNavController().navigate(R.id.action_mealPlansFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}