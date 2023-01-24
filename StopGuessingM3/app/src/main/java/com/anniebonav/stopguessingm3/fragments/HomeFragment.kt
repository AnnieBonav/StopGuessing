package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.StopGuessingDatabase
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlanDao
import com.anniebonav.stopguessingm3.databinding.FragmentHomeBinding
import org.w3c.dom.Text
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.
    private lateinit var _context: MainActivity

    private lateinit var _mealPlanDAO: MealPlanDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _context = activity as MainActivity
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        _mealPlanDAO = StopGuessingDatabase.getDatabase(_context).mealPlanDao()

        binding.openMealPlans.setOnClickListener(){
            findNavController().navigate(R.id.action_HomeFragment_to_MealPlansFragment)
        }

        Thread{
            val currentMealPlans = _mealPlanDAO.getMealPlans()
            if(currentMealPlans.isEmpty()){ //Does check if it is empty
                binding.noMPLayout.visibility = View.VISIBLE //TODO: Add fragment here
            }else{
                binding.noMPLayout.visibility = View.GONE
            }
        }.start()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.addFab.setOnClickListener {
//            findNavController().navigate(R.id.action_HomeFragment_to_MealPlansFragment)
//        }

        val dayTitle = view.findViewById<TextView>(R.id.homeTitleDay)
        val calendar = Calendar.getInstance()
        val title: String = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Monday!"
            Calendar.TUESDAY -> "Tuesday!"
            Calendar.WEDNESDAY -> "Wednesday!"
            Calendar.THURSDAY -> "Thursday!"
            Calendar.FRIDAY -> "Friday!"
            Calendar.SATURDAY -> "Saturday!"
            Calendar.SUNDAY -> "Sunday!"
            else -> "Invalid Day"
        }
        dayTitle.text = title
    }

    override fun onStart() {
        super.onStart()
        _context.openedHome()
    }
}