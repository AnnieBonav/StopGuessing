package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private lateinit var context: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context = activity as MainActivity
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        binding.closeButton.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.openIngredients.setOnClickListener{
            findNavController().navigate(R.id.action_AboutFragment_to_IngredientsFragment)
        }

        binding.openBlueprints.setOnClickListener{
            findNavController().navigate(R.id.action_AboutFragment_to_BlueprintsFragment)
        }

        binding.openMealPlans.setOnClickListener{
            findNavController().navigate(R.id.action_AboutFragment_to_MealPlansFragment)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        context.openedAbout()
    }

}