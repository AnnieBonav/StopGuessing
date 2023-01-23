package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.Blueprints.BlueprintDAO
import com.anniebonav.stopguessingm3.databinding.FragmentViewBlueprintBinding

class ViewBlueprintFragment : Fragment() {
    private var _binding: FragmentViewBlueprintBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.
    private lateinit var _blueprintDAO: BlueprintDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as MainActivity
        var selectedBlueprintId = arguments?.getInt("selectedBlueprint")

        _binding = FragmentViewBlueprintBinding.inflate(inflater, container, false)

        _blueprintDAO = StopGuessingDatabase.getDatabase(context).blueprintDao()

        binding.goBackButton.setOnClickListener(){
            findNavController().navigateUp()
        }

        Thread{
            Log.d("Data", "$selectedBlueprintId")
            val selectedBlueprint = _blueprintDAO.getBlueprint(selectedBlueprintId!!)
            binding.blueprintName.text = selectedBlueprint.name
            binding.breakfastUnits.text = selectedBlueprint.breakfastUnits.toString()
            binding.lunchUnits.text = selectedBlueprint.lunchUnits.toString()
            binding.dinnerUnits.text = selectedBlueprint.dinnerUnits.toString()
            binding.morningSnackUnits.text = selectedBlueprint.morningSnackUnits.toString()
            binding.eveningSnackUnits.text = selectedBlueprint.eveningSnackUnits.toString()
        }.start()

        binding.editBlueprintButton.setOnClickListener(){
            editBlueprint(selectedBlueprintId!!)
        }

        return binding.root
    }

    private fun editBlueprint(blueprintId: Int){
        val bundle = bundleOf("selectedBlueprint" to blueprintId)
        findNavController().navigate(R.id.action_ViewBlueprintFragment_to_InteractsBlueprintsFragment, bundle)
    }
}