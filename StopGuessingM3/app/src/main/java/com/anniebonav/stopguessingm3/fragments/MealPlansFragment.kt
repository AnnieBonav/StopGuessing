package com.anniebonav.stopguessingm3.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.*
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlan
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlanDao
import com.anniebonav.stopguessingm3.recycler.MealPlanAdapter
import com.anniebonav.stopguessingm3.databinding.FragmentMealPlansBinding
import kotlinx.coroutines.launch


class MealPlansFragment : Fragment() {
    private var _binding: FragmentMealPlansBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.
    private lateinit var _context: MainActivity

    private lateinit var _mealPlansRecycler: RecyclerView
    private lateinit var _mealPlanDAO: MealPlanDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _context = activity as MainActivity
        _binding = FragmentMealPlansBinding.inflate(inflater, container, false)
        _mealPlansRecycler = binding.mpRecycler

        val mealPlansFactory = ViewModelFactoryUI(_context);
        val model = ViewModelProvider(_context, mealPlansFactory).get(UIViewModel::class.java)

        _mealPlanDAO = StopGuessingDatabase.getDatabase(_context).mealPlanDao()
        val linearLayoutManager = LinearLayoutManager(_context, LinearLayoutManager.VERTICAL, false)

        _mealPlansRecycler.layoutManager = linearLayoutManager

        model.currentMealPlans.observe(_context, Observer { mealPlans ->
            _mealPlansRecycler.adapter = MealPlanAdapter(_context, mealPlans, this::onMealPlanDeleteClicked, this::onMealPlanCardClicked, this::onMealPlanFavoriteClicked)
        })

        handleInitialMealPlan()

        return binding.root
    }

    private fun handleInitialMealPlan(){
        Thread{
            val currentMealPlans = _mealPlanDAO.getMealPlans()
            if(currentMealPlans.isEmpty()){
                Handler(Looper.getMainLooper()).post {
                    binding.initialMealPlan.visibility = View.VISIBLE
                    //binding.selectedMealPlanTitle.visibility = View.GONE
                }
            }else{
                Handler(Looper.getMainLooper()).post {
                    binding.initialMealPlan.visibility = View.GONE
                    //binding.selectedMealPlanTitle.visibility = View.VISIBLE
                }
            }
            Handler(Looper.getMainLooper()).post {
                _context.changeSelection(1) //Needs to be inside this thread cause binding fails when stuff from the parent is called before all the stuff from the fragment is finished
                //So it first needs to be after the child stuff and then it needs to be on the thread and then on a handler to be on the main loop
            }
        }.start()
    }

    override fun onStart() {
        super.onStart()
        _context.openedMealPlans()
        //Cannot call parent stuff here when child stuff is done elsewhere because it cries
        //Exampel: _context._tabsNavigationView.selectTab(_context._tabsNavigationView.getTabAt(1))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.action_MealPlansFragment_to_InteractsMealPlanFragment)
        }
    }

    private fun onMealPlanFavoriteClicked(mealPlanId: Int){
        Thread{ //TODO: change selected meal plan when current selected meal plan is deleted
            var mealPlan = _mealPlanDAO.getMealPlan(mealPlanId)
            if(mealPlan.mealPlanIsSelected == 0){
                val selectedMealPlan = _mealPlanDAO.getSelectedMealPlan()
                val changesSelectedMealPlan = MealPlan(selectedMealPlan.uid, selectedMealPlan.blueprintId, selectedMealPlan.mealPlanName, selectedMealPlan.mealPlanDescription, selectedMealPlan.mealPlanBreakfasts, selectedMealPlan.mealPlanLunches, selectedMealPlan.mealPlanDinners, selectedMealPlan.mealPlanMorningSnacks, selectedMealPlan.mealPlanEveningSnacks, 0)
                _mealPlanDAO.update(changesSelectedMealPlan) //Deselect the selected one

                //Select the new selected
                val newMealPlan = MealPlan(mealPlan.uid, mealPlan.blueprintId, mealPlan.mealPlanName, mealPlan.mealPlanDescription, mealPlan.mealPlanBreakfasts, mealPlan.mealPlanLunches, mealPlan.mealPlanDinners, mealPlan.mealPlanMorningSnacks, mealPlan.mealPlanEveningSnacks, 1)
                _mealPlanDAO.update(newMealPlan) //Deselect the selected one
            }
        }.start()
    }

    private fun onMealPlanCardClicked(mealPlanId: Int){
        openMealPlan(mealPlanId)
    }

    private fun openMealPlan(mealPlanId: Int){
        val bundle = bundleOf("selectedMealPlan" to mealPlanId)
        findNavController().navigate(R.id.action_MealPlansFragment_to_ViewMealPlanFragment, bundle)
    }

    private fun onMealPlanEditClicked(mealPlanId: Int ){
        editMealPlan(mealPlanId)
    }

    private fun editMealPlan(mealPlanId: Int){
        val bundle = bundleOf("selectedMealPlan" to mealPlanId)
        findNavController().navigate(R.id.action_MealPlansFragment_to_InteractsMealPlanFragment, bundle)
    }

    private fun onMealPlanDeleteClicked(mealPlanId: Int){
        deleteMealPlan(mealPlanId)
    }

    private fun deleteMealPlan(mealPlanId: Int){
        val builder = AlertDialog.Builder(_context)
        builder.setMessage("Are you sure you want to delete this Meal Plan?")
        builder.setPositiveButton("Yes"){ p0, p1 ->
            lifecycleScope.launch {
                Thread{
                    val deletedMealPlan = _mealPlanDAO.getMealPlan(mealPlanId)
                    _mealPlanDAO.delete(deletedMealPlan)

                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(activity as MainActivity, "${deletedMealPlan.mealPlanName} successfully deleted!", Toast.LENGTH_SHORT).show()
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