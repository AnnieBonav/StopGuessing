package com.anniebonav.stopguessingm3

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.databinding.FragmentMealplansBinding

//Database stuff
import androidx.room.Room

class MealPlansFragment : Fragment() {
    private var _binding: FragmentMealplansBinding? = null
    private val _mealPlansDatabase: String = "MEALPLANS_DATABASE"

    //Recycler
    private lateinit var _mealPlansRecycler: RecyclerView
    val mealPlansArrayList: ArrayList<MealPlanModel> = ArrayList<MealPlanModel>()
    //private val initialMealPlans = listOf("MealPlan1", "MealPlan2", "MealPlan3")


    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myContext = activity as MainActivity

        _binding = FragmentMealplansBinding.inflate(inflater, container, false)

        /*
        val addMealPlan = binding.addMealPlan
        addMealPlan?.setOnClickListener(){
            SaveMealPlan(binding.root)
        }*/

        //RECYCLER
        _mealPlansRecycler = binding.mpRecycler

        val db = Room.databaseBuilder(
            myContext, MealPlanDatabase::class.java, _mealPlansDatabase
        ).build()

        val mealPlanDao = db.mealPlanDao()
        val newMealPlan1 = MealPlan(0, "Annie", "Bonavides", 0, 0)
        val newMealPlan2 = MealPlan(0, "wefwfefef", "Aguilar", 1, 1)

        //

        Thread {
            mealPlanDao.insertAll(newMealPlan1, newMealPlan2)
            val users: List<MealPlan> = mealPlanDao.getAll()
            Log.d("User", "MyUser: $newMealPlan1   Users: $users")
        }.start()

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

        /*
        binding.seeMealPlansButton.setOnClickListener {
            findNavController().navigate(R.id.action_mealPlansFragment_to_FirstFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}