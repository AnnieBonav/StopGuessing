package com.anniebonav.stopguessingm3.fragments

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController

//Database stuff
import androidx.room.Room
import com.anniebonav.stopguessingm3.MainActivity
import com.anniebonav.stopguessingm3.MealPlanDatabase
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.MealPlan
import com.anniebonav.stopguessingm3.recycler.MealPlanAdapter
import com.anniebonav.stopguessingm3.data.MealPlanModel
import com.anniebonav.stopguessingm3.databinding.FragmentMealPlansBinding

class MealPlansFragment : Fragment() {
    private var _binding: FragmentMealPlansBinding? = null
    private val _mealPlansDatabase: String = "MEALPLANS_DATABASE"

    private lateinit var _mealPlansRecycler: RecyclerView

    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myContext = activity as MainActivity
        _binding = FragmentMealPlansBinding.inflate(inflater, container, false)
        _mealPlansRecycler = binding.mpRecycler

        val mpDao = MealPlanDatabase.getDatabase(myContext).mealPlanDao()
        /*val db = Room.databaseBuilder(
            myContext, MealPlanDatabase::class.java, _mealPlansDatabase
        ).build()
        val mealPlanDao = db.mealPlanDao()*/

        Thread {
            val currentMealPlans = mpDao.getAll()
            Log.d("Please", "Meals: $currentMealPlans")
        }.start()


        val mealPlansArrayList: ArrayList<MealPlanModel> = ArrayList<MealPlanModel>()

        mealPlansArrayList.add(MealPlanModel("Gym Time plan", "This is the plan I use on the days I go to the gym.", 1, 2))
        mealPlansArrayList.add(MealPlanModel("Day to day plan", "This is the plan I use on the days I do not go to the gym.", 3, 4))
        mealPlansArrayList.add(MealPlanModel("Summer body", "This is the plan I use on to cut and cardio.", 2, 4))
        mealPlansArrayList.add(MealPlanModel("Mexican ingredients", "Esta es la versión mexicana de mi plan.", 3, 4))
        mealPlansArrayList.add(MealPlanModel("Gym Time plan", "This is the plan I use on the days I go to the gym.", 1, 2))


        val mpAdapter = MealPlanAdapter(myContext, mealPlansArrayList)
        val linearLayoutManager = LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false)

        _mealPlansRecycler.layoutManager = linearLayoutManager
        _mealPlansRecycler.adapter = mpAdapter

        return binding.root
    }

    private fun onMPClicked(ingredientName: String){
        duplicateIngredient(ingredientName)
    }

    private fun duplicateIngredient(ingredientName: String){
        //toast("Duplicated $ingredientName")
        //mpAdapter.addIngredient(ingredientName)
    }

    private fun toast(text: String){
        val context = this
        //val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        //toast.show()
    }

    /*
    fun SaveMealPlan(view: View){
        myDb.insertData(view.findViewById<EditText>(R.id.mpNameInput).text.toString())
        var currentMPNumber = myDb.GetMPAmount()
        var newMealPlan = myDb.getMealPlan(currentMPNumber)
        duplicateIngredient(newMealPlan)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.action_MealPlansFragment_to_AddMealPlanFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}