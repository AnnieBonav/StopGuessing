package anniebonav.stopguessing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class AddIngredientFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ingredientNameInput = view.findViewById(R.id.ingredientNameInput)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_ingredient, container, false)


        val button = view.findViewById<Button>(R.id.goBackIngredientsFromAdd)
        button.setOnClickListener{
            findNavController().navigate(R.id.action_addIngredientFragment_to_ingredientsFragment)
        }

        return view
    }

    fun toastMe(){
        val toast = Toast.makeText(context, "I am a toast", Toast.LENGTH_SHORT)
        toast.show()
    }

    /*
    fun saveMessage(view: View){
        myDb.insertData(findViewById<EditText>(R.id.insertText).text.toString())
        startActivity(Intent(this,MainActivity2::class.java))
    }*/
}