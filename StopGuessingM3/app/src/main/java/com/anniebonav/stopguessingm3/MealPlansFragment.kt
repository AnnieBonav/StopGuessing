package com.anniebonav.stopguessingm3

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.databinding.FragmentMealPlansBinding

//Database stuff
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

class MealPlansFragment : Fragment() {
    private var _binding: FragmentMealPlansBinding? = null
    private lateinit var myDb: DatabaseHandler


    //Recycler
    private lateinit var mealPlansRecycler: RecyclerView
    val mealplansArrayList: ArrayList<MealPlanModel> = ArrayList<MealPlanModel>()
    //private val initialMealPlans = listOf("MealPlan1", "MealPlan2", "MealPlan3")

    //New database
    private val DATABASE_MP: String = "USER_DATABASE"

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myContext = activity as MainActivity

        _binding = FragmentMealPlansBinding.inflate(inflater, container, false)
        myDb = DatabaseHandler(myContext)

        val addMealPlan = binding.addMealPlan
        addMealPlan?.setOnClickListener(){
            SaveMealPlan(binding.root)
        }

        //RECYCLER
        mealPlansRecycler = binding.mpRecycler
        //mealPlansRecycler.adapter = mpAdapter

        //New database

        val db = Room.databaseBuilder(
            myContext, UserDatabase::class.java, DATABASE_MP
        ).build()

        val userDao = db.userDao()
        val myUser = User(0, "Annie", "Bonavides")

        //

        Thread {
            userDao.insertAll(myUser)
            val users: List<User> = userDao.getAll()
            Log.d("User", "MyUser: $myUser   Users: $users")
        }.start()

        mealplansArrayList.add(MealPlanModel("Annie", "Bonavides", 1, 2))
        mealplansArrayList.add(MealPlanModel("Ana", "Aguilar", 3, 4))
        val mpAdapter = MealPlansAdapter(myContext, mealplansArrayList)
        val linearLayoutManager = LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false)
        mealPlansRecycler.layoutManager = linearLayoutManager
        mealPlansRecycler.adapter = mpAdapter

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

    fun SaveMealPlan(view: View){
        myDb.insertData(view.findViewById<EditText>(R.id.mpNameInput).text.toString())
        var currentMPNumber = myDb.GetMPAmount()
        var newMealPlan = myDb.getMealPlan(currentMPNumber)
        duplicateIngredient(newMealPlan)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.seeMealPlansButton.setOnClickListener {
            findNavController().navigate(R.id.action_mealPlansFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}