package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.Blueprints.Blueprint
import com.anniebonav.stopguessingm3.data.Blueprints.BlueprintDAO
import com.anniebonav.stopguessingm3.data.UIViewModelInteractBlueprint
import com.anniebonav.stopguessingm3.databinding.FragmentInteractsBlueprintsBinding

class InteractsBlueprintsFragment : Fragment() {
    private var _binding: FragmentInteractsBlueprintsBinding? = null
    private val binding get() = _binding!!
    private lateinit var _context: MainActivity

    private val _blueprintViewModel: UIViewModelInteractBlueprint by viewModels()
    private lateinit var _blueprintDao: BlueprintDAO //Can do code cleanup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _context = activity as MainActivity
        _binding = FragmentInteractsBlueprintsBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.blueprintsViewModel = _blueprintViewModel

        _blueprintDao = StopGuessingDatabase.getDatabase(_context).blueprintDao()



        if(arguments != null){ //If I am sending arguments, it means that I am updating a blueprint and not adding one. This way I reuse my View Model.
            var selectedBlueprintId = arguments?.getInt("selectedBlueprint")
            binding.crudActionButton.text = "Update Blueprint"
            binding.ingredientsInstruction.text = "Update Blueprint"

            Thread{
                val selectedBlueprint = _blueprintDao.getBlueprint(selectedBlueprintId!!)
                Handler(Looper.getMainLooper()).post {
                    //.name and .breakfast come from the UIViewModelInteractBlueprint = and .name .breakfastUnits (from selectedBlueprint) are the Blueprint.kt values
                    _blueprintViewModel.name.value = selectedBlueprint.name
                    _blueprintViewModel.description.value = selectedBlueprint.description
                    _blueprintViewModel.breakfastUnits.value = selectedBlueprint.breakfastUnits.toString()
                    _blueprintViewModel.lunchUnits.value = selectedBlueprint.lunchUnits.toString()
                    _blueprintViewModel.dinnerUnits.value = selectedBlueprint.dinnerUnits.toString()
                    _blueprintViewModel.morningSnackUnits.value = selectedBlueprint.morningSnackUnits.toString()
                    _blueprintViewModel.eveningSnackUnits.value = selectedBlueprint.eveningSnackUnits.toString()
                }
            }.start()

            binding.crudActionButton.setOnClickListener(){
                CRUDBlueprint(selectedBlueprintId!!)
            }
        }else{
            binding.crudActionButton.text = "Add Blueprint"

            binding.crudActionButton.setOnClickListener(){
                CRUDBlueprint(null)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goBackButton.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    fun CRUDBlueprint(selectedBlueprintId: Int?){ //If it exists, it will update and not create
        var nameInput = "No name"
        var descriptionInput = "No description"

        var breakfastUnitsInput = 0
        var lunchUnitsInput = 0
        var dinnerUnitsInput = 0
        var morningSnackUnitsInput = 0
        var eveningSnackUnitsInput = 0

        if(_blueprintViewModel.name.value != null && _blueprintViewModel.name.value != ""){
            nameInput = _blueprintViewModel.name.value.toString()
        }

        if(_blueprintViewModel.description.value != null && _blueprintViewModel.description.value != ""){
            descriptionInput = _blueprintViewModel.description.value.toString()
        }

        // I need to check that it is not null because when you just deleted the number it appears as ""
        if(_blueprintViewModel.breakfastUnits.value != null && _blueprintViewModel.breakfastUnits.value != ""){
            breakfastUnitsInput = _blueprintViewModel.breakfastUnits.value!!.toInt()
        }

        if(_blueprintViewModel.lunchUnits.value != null  && _blueprintViewModel.lunchUnits.value != ""){
            lunchUnitsInput = _blueprintViewModel.lunchUnits.value!!.toInt()
        }

        if(_blueprintViewModel.dinnerUnits.value != null && _blueprintViewModel.dinnerUnits.value != ""){
            dinnerUnitsInput = _blueprintViewModel.dinnerUnits.value!!.toInt()
        }

        if(_blueprintViewModel.morningSnackUnits.value != null && _blueprintViewModel.morningSnackUnits.value != ""){
            morningSnackUnitsInput = _blueprintViewModel.morningSnackUnits.value!!.toInt()
        }

        if(_blueprintViewModel.eveningSnackUnits.value != null && _blueprintViewModel.eveningSnackUnits.value != ""){
            eveningSnackUnitsInput = _blueprintViewModel.eveningSnackUnits.value!!.toInt()
        }


        val blueprint = Blueprint(selectedBlueprintId, nameInput, descriptionInput, breakfastUnitsInput, lunchUnitsInput, dinnerUnitsInput, morningSnackUnitsInput, eveningSnackUnitsInput)

        if(selectedBlueprintId != null){ //Update
            Thread {
                _blueprintDao.update(blueprint)
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(activity as MainActivity, "${blueprint.name} successfully updated!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_InteractsBlueprintsFragment_to_BlueprintsFragment)
                }
            }.start()
        }else{
            Thread {
                _blueprintDao.insertAll(blueprint)
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(activity as MainActivity, "${blueprint.name} successfully created!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_InteractsBlueprintsFragment_to_BlueprintsFragment)
                }
                }.start()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}