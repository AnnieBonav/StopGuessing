package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.databinding.FragmentViewMealPlanBinding
import kotlinx.coroutines.selects.select

class ViewMealPlanFragment : Fragment() {
    private var _binding: FragmentViewMealPlanBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.

    private var selectedMealPlan: String? = null

    /*
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        if(arguments!=null){
            //selectedMealPlan = arguments?.getString("selectedMealPlan").toString()
            //Log.d("Tag", arguments?.getString("selectedMealPlan")!!)
            if(selectedMealPlan is String){
                Log.d("See", selectedMealPlan!!)
            }
        }
    }*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //var myArguments = arguments
        var selectedMealPlan = arguments?.getString("selectedMealPlan").toString()
        Log.d("Tags", selectedMealPlan)
        //var myArg = myArguments?.getString(selectedMealPlan)
        //var myArg = myArguments!!.getString("key1", "default")
        //Log.d("Tags", "$myArg")
        //val text = arguments?.getString("selectedMealPlan")
        //val arg = arguments?.selectedMealPlan
        val context = activity as MainActivity
        _binding = FragmentViewMealPlanBinding.inflate(inflater, container, false)

        binding.mealPlanName.text = selectedMealPlan
        //selectedMealPlan = "Annie"
        //toast(selectedMealPlan!!)
        return binding.root
    }

    private fun toast(text: String){
        val context = activity as MainActivity
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }

}