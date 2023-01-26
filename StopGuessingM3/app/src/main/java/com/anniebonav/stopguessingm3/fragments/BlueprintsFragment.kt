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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.Blueprints.Blueprint
import com.anniebonav.stopguessingm3.data.Blueprints.BlueprintDAO
import com.anniebonav.stopguessingm3.data.UIViewModel
import com.anniebonav.stopguessingm3.data.ViewModelFactoryUI
import com.anniebonav.stopguessingm3.databinding.FragmentBlueprintsBinding
import com.anniebonav.stopguessingm3.recycler.BlueprintAdapter
import kotlinx.coroutines.launch

class BlueprintsFragment : Fragment() {
    private var _binding: FragmentBlueprintsBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.
    private lateinit var _context: MainActivity

    private lateinit var _blueprintsRecycler: RecyclerView
    private lateinit var _blueprintDAO: BlueprintDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _context = activity as MainActivity
        _binding = FragmentBlueprintsBinding.inflate(inflater, container, false)


        _blueprintsRecycler = binding.blueprintsRecycler

        val factory = ViewModelFactoryUI(_context);
        val model = ViewModelProvider(_context, factory).get(UIViewModel::class.java)

        _blueprintDAO = StopGuessingDatabase.getDatabase(_context).blueprintDao()
        val linearLayoutManager = LinearLayoutManager(_context, LinearLayoutManager.VERTICAL, false)
        _blueprintsRecycler.layoutManager = linearLayoutManager

        model.currentBlueprints.observe(_context, Observer { blueprints ->
            _blueprintsRecycler.adapter = BlueprintAdapter(_context, blueprints, this::onBlueprintDeleteClicked, this::onBlueprintCardClicked, this::onBlueprintEditClicked)
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        _context.openedBlueprints()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.action_BlueprintsFragment_to_InteractsBlueprintsFragment)
        }
    }

    private fun onBlueprintCardClicked(blueprintId: Int){
        openBlueprint(blueprintId)
    }

    private fun openBlueprint(blueprintId: Int){
        val bundle = bundleOf("selectedBlueprint" to blueprintId)
        findNavController().navigate(R.id.action_BlueprintsFragment_to_ViewBlueprintFragment, bundle)
    }

    private fun onBlueprintEditClicked(blueprintId: Int ){
        editBlueprint(blueprintId)
    }

    private fun editBlueprint(blueprintId: Int){
        val bundle = bundleOf("selectedBlueprint" to blueprintId)
        findNavController().navigate(R.id.action_BlueprintsFragment_to_InteractsBlueprintsFragment, bundle)
    }

    private fun onBlueprintDeleteClicked(blueprintId: Int){
        deleteBlueprint(blueprintId)
    }

    private fun deleteBlueprint(blueprintId: Int){
        val builder = AlertDialog.Builder(_context)
        builder.setMessage("Are you sure you want to delete this Blueprint?")
        builder.setPositiveButton("Yes"){ p0, p1 ->
            lifecycleScope.launch {
                Thread{
                    val deletedBlueprint = _blueprintDAO.getBlueprint(blueprintId)
                    _blueprintDAO.delete(deletedBlueprint)

                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(activity as MainActivity, "${deletedBlueprint.name} successfully deleted!", Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}