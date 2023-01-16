package com.anniebonav.stopguessingm3

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private lateinit var _bottomNavigationView: BottomNavigationView
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val context = activity as MainActivity
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        _bottomNavigationView = binding.bottomNavigation

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            Log.d("Navigation", "I am here")
            findNavController().navigate(R.id.action_HomeFragment_to_MealPlansFragment)
        }

        _bottomNavigationView.setOnItemSelectedListener{ item ->
            Log.d("Navigation", "Inside setOnClick")
            when(item.itemId){
                R.id.home->{
                    Log.d("Navigation", "Navigating home")
                    true
                }

                R.id.meals->{
                    Log.d("Navigation", "Navigating")
                    findNavController().navigate(R.id.action_HomeFragment_to_BlueprintsFragment)
                    true
                }

                R.id.ingredients->{
                    true
                }
                else ->{
                    false
                }
            }
        }
    }
}