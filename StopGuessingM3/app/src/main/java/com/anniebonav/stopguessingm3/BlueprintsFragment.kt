package com.anniebonav.stopguessingm3

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.databinding.FragmentBlueprintsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class BlueprintsFragment : Fragment() {
    private var _binding: FragmentBlueprintsBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as MainActivity
        _binding = FragmentBlueprintsBinding.inflate(inflater, container, false)


        return binding.root
    }
}