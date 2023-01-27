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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.Blueprints.BlueprintDAO
import com.anniebonav.stopguessingm3.databinding.FragmentViewBlueprintBinding
import kotlinx.coroutines.launch

class ViewBlueprintFragment : Fragment() {
    private var _binding: FragmentViewBlueprintBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.
    private lateinit var _context: MainActivity

    private lateinit var _blueprintDAO: BlueprintDAO
    private var _selectedBlueprintId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _context = activity as MainActivity
        _selectedBlueprintId = arguments?.getInt("selectedBlueprint")!!

        _binding = FragmentViewBlueprintBinding.inflate(inflater, container, false)

        _blueprintDAO = StopGuessingDatabase.getDatabase(_context).blueprintDao()

        fillInformation()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editBlueprintButton.setOnClickListener(){
            editBlueprint()
        }

        binding.goBackButton.setOnClickListener(){
            findNavController().navigateUp()
        }

        binding.deleteBlueprintButton.setOnClickListener{
            deleteBlueprint()

        }
    }

    private fun deleteBlueprint(){
        val builder = AlertDialog.Builder(_context)
        builder.setMessage("Are you sure you want to delete this Blueprint?")
        builder.setPositiveButton("Yes"){ p0, p1 ->
            lifecycleScope.launch {
                Thread{
                    val deletedBlueprint = _blueprintDAO.getBlueprint(_selectedBlueprintId)
                    _blueprintDAO.delete(deletedBlueprint)

                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(activity as MainActivity, "${deletedBlueprint.name} successfully deleted!", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                }.start()
                onResume()
            }
            p0.dismiss()
        }
        builder.setNegativeButton("No"){p0, p1 ->
            p0.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun editBlueprint(){
        val bundle = bundleOf("selectedBlueprint" to _selectedBlueprintId)
        findNavController().navigate(R.id.action_ViewBlueprintFragment_to_InteractsBlueprintsFragment, bundle)
    }

    private fun fillInformation(){
        Thread{
            val selectedBlueprint = _blueprintDAO.getBlueprint(_selectedBlueprintId!!)
            binding.blueprintName.text = selectedBlueprint.name
            binding.breakfastUnits.text = selectedBlueprint.breakfastUnits.toString()
            binding.lunchUnits.text = selectedBlueprint.lunchUnits.toString()
            binding.dinnerUnits.text = selectedBlueprint.dinnerUnits.toString()
            binding.morningSnackUnits.text = selectedBlueprint.morningSnackUnits.toString()
            binding.eveningSnackUnits.text = selectedBlueprint.eveningSnackUnits.toString()
        }.start()
    }
}