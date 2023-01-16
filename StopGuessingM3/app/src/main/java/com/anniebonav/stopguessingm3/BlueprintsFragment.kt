package com.anniebonav.stopguessingm3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anniebonav.stopguessingm3.databinding.FragmentBlueprintsBinding
import com.anniebonav.stopguessingm3.databinding.FragmentMealPlansBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class BlueprintsFragment : Fragment() {
    private lateinit var _bottomNavigationView: BottomNavigationView
    private var _binding: FragmentBlueprintsBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as MainActivity
        _binding = FragmentBlueprintsBinding.inflate(inflater, container, false)

        _bottomNavigationView = binding.bottomNavigation

        _bottomNavigationView.setOnClickListener{ item ->
            when(item.id){
                R.id.page1->{
                    true
                }

                R.id.page2->{
                    true
                }

                R.id.page3->{
                    true
                }
                else ->{
                    false
                }
            }
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blueprints, container, false)

    }
}