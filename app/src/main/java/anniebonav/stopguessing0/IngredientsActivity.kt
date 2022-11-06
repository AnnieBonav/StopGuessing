package anniebonav.stopguessing0


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
//Added tutorial
import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class IngredientsActivity : AppCompatActivity() {
    private lateinit var ingredientsList: RecyclerView
    private val initialIngredientsNames = listOf("Apple", "Pear", "Cucumber", "Salad", "Chicken")
    private val ingredientsAdapter = IngredientsAdapter(initialIngredientsNames, this::onIngredientClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_ingredients)
        ingredientsList = findViewById(R.id.ingredientsList)
        ingredientsList.adapter = ingredientsAdapter
    }

    private fun onIngredientClicked(ingredientName: String){
        duplicateIngredient(ingredientName)
    }

    private fun duplicateIngredient(ingredientName: String){
        toast("Duplicated $ingredientName")
        ingredientsAdapter.addIngredient(ingredientName)
    }

    private fun toast(text: String){
        val context = this
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }
}