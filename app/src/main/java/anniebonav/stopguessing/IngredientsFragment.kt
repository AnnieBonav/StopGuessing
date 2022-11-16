package anniebonav.stopguessing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlin.coroutines.coroutineContext

class IngredientsFragment : Fragment() {
    private lateinit var ingredientsList: RecyclerView
    private val initialIngredientsNames = listOf("Apple")
    private val ingredientsAdapter = IngredientsListAdapter(initialIngredientsNames, this::onIngredientClicked)

    //private var myDb = DatabaseHandler(requireContext())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ingredientsList = view.findViewById(R.id.ingredientsRecycler)
        ingredientsList.adapter = ingredientsAdapter

        /*
        val messagesText = view.findViewById<TextView>(R.id.messagesTextView).apply {
            text = myDb.listOfMessages().toString()
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)

        val addIngredientsButton = view?.findViewById<Button>(R.id.addIngredientButton)
        addIngredientsButton?.setOnClickListener{
            findNavController().navigate(R.id.action_ingredientsFragment_to_addIngredientFragment)
        }

        val deleteIngredientsButton = view?.findViewById<Button>(R.id.goToDeleteIngredientsFragment)
        deleteIngredientsButton?.setOnClickListener{
            findNavController().navigate(R.id.action_ingredientsFragment_to_deleteIngredientFragment)
        }
        // Inflate the layout for this fragment
        return view
    }


    private fun onIngredientClicked(ingredientName: String){
        duplicateIngredient(ingredientName)
    }

    private fun duplicateIngredient(ingredientName: String){
        toast("Duplicated $ingredientName")
        ingredientsAdapter.addIngredient(ingredientName)
    }

    private fun toast(text: String){
        //val context = this
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }

}