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
import androidx.room.Dao
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
    private val _blueprintViewModel: UIViewModelInteractBlueprint by viewModels()
        //private lateinit var blueprintDao: BlueprintDAO //Can do code cleanup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as MainActivity
        _binding = FragmentInteractsBlueprintsBinding.inflate(inflater, container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.blueprintsViewModel = _blueprintViewModel

        val blueprintDao = StopGuessingDatabase.getDatabase(context).blueprintDao()

        binding.goBackButton.setOnClickListener(){
            findNavController().navigateUp()
            Log.d("Blueprint", "Going up?")
        }

        if(arguments != null){ //If I am sending arguments, it means that I am updating a blueprint and not adding one. This way I reuse my View Model.
            var selectedBlueprintId = arguments?.getInt("selectedBlueprint")
            binding.crudActionButton.text = "Update Blueprint"
            Thread{
                val selectedBlueprint = blueprintDao.getBlueprint(selectedBlueprintId!!)
                Handler(Looper.getMainLooper()).post {
                    //.name and .breakfast come from the UIViewModelInteractBlueprint = and .name .breakfastUnits (from selectedBlueprint) are the Blueprint.kt values
                    _blueprintViewModel.name.value = selectedBlueprint.name
                    _blueprintViewModel.breakfastUnits.value = selectedBlueprint.breakfastUnits.toString()
                    _blueprintViewModel.lunchUnits.value = selectedBlueprint.lunchUnits.toString()
                    _blueprintViewModel.dinnerUnits.value = selectedBlueprint.dinnerUnits.toString()
                    _blueprintViewModel.morningSnackUnits.value = selectedBlueprint.morningSnackUnits.toString()
                    _blueprintViewModel.eveningSnackUnits.value = selectedBlueprint.eveningSnackUnits.toString()
                }
            }.start()

            binding.crudActionButton.setOnClickListener(){
                UpdateBlueprint(blueprintDao, selectedBlueprintId!!)
            }
        }else{
            binding.crudActionButton.text = "Add Blueprint"

            binding.crudActionButton.setOnClickListener(){
                CreateBlueprint(blueprintDao)
            }
        }
        return binding.root
    }

    fun CreateBlueprint(blueprintDAO: BlueprintDAO){
        var nameInput = "No name"
        var descriptionInput = "No description"

        var breakfastUnitsInput = 0
        var lunchUnitsInput = 0
        var dinnerUnitsInput = 0
        var morningSnackUnitsInput = 0
        var eveningSnackUnitsInput = 0

        if(_blueprintViewModel.name.value != null){
            nameInput = _blueprintViewModel.name.value.toString()
        }

        if(_blueprintViewModel.description.value != null){
            descriptionInput = _blueprintViewModel.description.value.toString()
        }

        if(_blueprintViewModel.breakfastUnits.value != null){
            breakfastUnitsInput = _blueprintViewModel.breakfastUnits.value!!.toInt()
        }

        if(_blueprintViewModel.lunchUnits.value != null){
            lunchUnitsInput = _blueprintViewModel.lunchUnits.value!!.toInt()
        }

        if(_blueprintViewModel.dinnerUnits.value != null){
            dinnerUnitsInput = _blueprintViewModel.dinnerUnits.value!!.toInt()
        }

        if(_blueprintViewModel.morningSnackUnits.value != null){
            morningSnackUnitsInput = _blueprintViewModel.morningSnackUnits.value!!.toInt()
        }

        if(_blueprintViewModel.eveningSnackUnits.value != null){
            eveningSnackUnitsInput = _blueprintViewModel.eveningSnackUnits.value!!.toInt()
        }


        val blueprint = Blueprint(null, nameInput, descriptionInput, breakfastUnitsInput, lunchUnitsInput, dinnerUnitsInput, morningSnackUnitsInput, eveningSnackUnitsInput)

        Thread {
            blueprintDAO.insertAll(blueprint)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity as MainActivity, "${blueprint.name} successfully created!", Toast.LENGTH_SHORT).show()
            }
        }.start()

        findNavController().navigate(R.id.action_InteractsBlueprintsFragment_to_BlueprintsFragment)
    }

    fun UpdateBlueprint(blueprintDAO: BlueprintDAO, selectedBlueprintId: Int){
        val blueprint = Blueprint(selectedBlueprintId, _blueprintViewModel.name.value.toString(), _blueprintViewModel.description.value.toString(), _blueprintViewModel.breakfastUnits.value!!.toInt(), _blueprintViewModel.lunchUnits.value!!.toInt(), _blueprintViewModel.dinnerUnits.value!!.toInt(), _blueprintViewModel.morningSnackUnits.value!!.toInt(), _blueprintViewModel.eveningSnackUnits.value!!.toInt())

        Thread {
            blueprintDAO.update(blueprint)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(activity as MainActivity, "${blueprint.name} successfully updated!", Toast.LENGTH_SHORT).show()
            }
        }.start()

        //TODO: IN general, add data proof and stuff related to the database

        findNavController().navigate(R.id.action_InteractsBlueprintsFragment_to_BlueprintsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}