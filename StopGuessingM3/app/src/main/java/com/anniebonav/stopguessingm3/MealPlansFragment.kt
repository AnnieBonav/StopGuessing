package com.anniebonav.stopguessingm3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.databinding.FragmentMealPlansBinding

class MealPlansFragment : Fragment() {
    private var _binding: FragmentMealPlansBinding? = null
    private lateinit var myDb: DatabaseHandler

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

        return binding.root
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