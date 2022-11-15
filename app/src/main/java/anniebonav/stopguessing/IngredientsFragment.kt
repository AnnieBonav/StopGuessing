package anniebonav.stopguessing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

class IngredientsFragment : Fragment() {
    private lateinit var ingredientsList: RecyclerView
    private val initialIngredientsNames = listOf("Apple", "Pear", "Cucumber", "Salad", "Chicken")
    private val ingredientsAdapter = IngredientsListAdapter(initialIngredientsNames, this::onIngredientClicked)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ingredientsList = view.findViewById(R.id.ingredientsList)
        ingredientsList.adapter = ingredientsAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)

        val addIngredientsButton = view?.findViewById<Button>(R.id.goToAddIngredientsFragment)
        addIngredientsButton?.setOnClickListener{
            val textBoolean = view?.findViewById<TextView>(R.id.boolView)
            if(textBoolean?.text == "false"){
                textBoolean?.text = "true";
            }else{
                textBoolean?.text = "false";
            }

            findNavController().navigate(R.id.action_ingredientsFragment_to_addIngredientFragment)
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