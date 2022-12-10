package com.anniebonav.stopguessingm3

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.anniebonav.stopguessingm3.databinding.FragmentAddMealPlanBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddMealPlanFragment : Fragment() {
    private var _binding: FragmentAddMealPlanBinding? = null
    private val binding get() = _binding!!

    private val _mealPlansDatabase: String = "MEALPLANS_DATABASE"

    //LIVE DATA
    private val model: UIViewModelAddMealPlan by viewModels()

    //private lateinit var _mealPlanNameInput: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myContext = activity as MainActivity

        _binding = FragmentAddMealPlanBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_add_meal_plan, container, false)
        //Database
        val db = Room.databaseBuilder(
            myContext, MealPlanDatabase::class.java, _mealPlansDatabase
        ).build()

        val mealPlanDao = db.mealPlanDao()

        val addMealPlan = binding.addMealPlan
        addMealPlan?.setOnClickListener(){
            SaveMealPlan(view, myContext, mealPlanDao)
        }



        //LIVE DATA

        val testTextView = binding.testTextView
        val nameObserver = Observer<String> { newMealPlanName ->
            // Update the UI, in this case, a TextView.
            testTextView.text = newMealPlanName
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.currentMealPlanName.observe(myContext, nameObserver)
        return binding.root
    }

    fun SaveMealPlan(view: View, context: Context, mealPlanDao: MealPlanDao){
        val changeName = "Now I am super cool"
        model.currentMealPlanName.value = changeName



        /*Log.d("Meal Plan", "Entered meal plan")
        val mealPlanName = view.findViewById<TextInputLayout>(R.id.mealPlanNameInput)
        val textEdit = mealPlanName.editText?.text.toString()

        val actualText = view.findViewById<TextInputEditText>(R.id.actualText)
        var smt = actualText.text.toString()
        //smt = mealPlanName.editText?.text.toString()

        val toast = Toast.makeText(context, textEdit, Toast.LENGTH_SHORT)
        toast.show()
        Log.d("Meal Plans", textEdit)
        //val newMealPlan = MealPlan(0, mealPlanName, "Aguilar", 1, 1)
*/
        /*
        Thread {
            mealPlanDao.insertAll(newMealPlan)
            val users: List<MealPlan> = mealPlanDao.getAll()
            Log.d("User", "MyUser: $newMealPlan   Users: $users")
        }.start()*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Maybe reuse for dab
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}