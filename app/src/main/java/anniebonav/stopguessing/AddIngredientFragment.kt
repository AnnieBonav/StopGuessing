package anniebonav.stopguessing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class AddIngredientFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_ingredient, container, false)

        val button = view.findViewById<Button>(R.id.goDeleteIngredientButton)
        button.setOnClickListener{
            val textBoolean = view?.findViewById<TextView>(R.id.booleanIngredients)
            if(textBoolean?.text == "false"){
                textBoolean?.text = "true";
            }else{
                textBoolean?.text = "false";
            }
            findNavController().navigate(R.id.action_addIngredientFragment_to_deleteIngredientFragment)
        }

        return view
    }

    fun toastMe(){
        val toast = Toast.makeText(context, "I am a toast", Toast.LENGTH_SHORT)
        toast.show()
    }
}