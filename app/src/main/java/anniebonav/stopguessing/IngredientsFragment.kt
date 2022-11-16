package anniebonav.stopguessing

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
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


    private lateinit var myDb: DatabaseHandler

    /*
    private fun saveIngredient(view: View){
        val ingredientName = view.findViewById<EditText>(R.id.ingredientNameInput).text.toString()
        myDb.insertData(ingredientName)
        toast(ingredientName);
        //startActivity(Intent(this,TestDatabaseActivity::class.java))
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ingredientsList = view.findViewById(R.id.ingredientsRecycler)
        ingredientsList.adapter = ingredientsAdapter

        /*
        view.findViewById<Button>(R.id.addIngredientButton).setOnClickListener{
            saveIngredient(view);
        }*/
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

        myDb = DatabaseHandler(activity as IngredientsActivity) //Activity exists inside of oncreate

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