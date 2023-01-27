package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.Blueprints.BlueprintDAO
import com.anniebonav.stopguessingm3.databinding.FragmentViewBlueprintBinding

class ViewBlueprintFragment : Fragment() {
    private var _binding: FragmentViewBlueprintBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.
    private lateinit var _context: MainActivity

    private lateinit var _blueprintDAO: BlueprintDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _context = activity as MainActivity
        var selectedBlueprintId = arguments?.getInt("selectedBlueprint")

        _binding = FragmentViewBlueprintBinding.inflate(inflater, container, false)

        _blueprintDAO = StopGuessingDatabase.getDatabase(_context).blueprintDao()

        fillInformation(selectedBlueprintId!!)

        return binding.root
    }

    private fun fillInformation(selectedBlueprintId: Int){
        Thread{
            val selectedBlueprint = _blueprintDAO.getBlueprint(selectedBlueprintId!!)
            binding.blueprintName.text = selectedBlueprint.name
            binding.breakfastUnits.text = selectedBlueprint.breakfastUnits.toString()
            binding.lunchUnits.text = selectedBlueprint.lunchUnits.toString()
            binding.dinnerUnits.text = selectedBlueprint.dinnerUnits.toString()
            binding.morningSnackUnits.text = selectedBlueprint.morningSnackUnits.toString()
            binding.eveningSnackUnits.text = selectedBlueprint.eveningSnackUnits.toString()
        }.start()
    }
}