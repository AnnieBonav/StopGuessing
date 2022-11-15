package anniebonav.stopguessing


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//Added tutorial
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerActivity : AppCompatActivity() {
    private lateinit var ingredientsList: RecyclerView
    private val initialIngredientsNames = listOf("Apple", "Pear", "Cucumber", "Salad", "Chicken")
    private val adapter = Adapter(initialIngredientsNames, this::onIngredientClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)
        ingredientsList = findViewById(R.id.ingredientsList)
        ingredientsList.adapter = adapter
    }

    private fun onIngredientClicked(ingredientName: String){
        duplicateIngredient(ingredientName)
    }

    private fun duplicateIngredient(ingredientName: String){
        toast("Duplicated $ingredientName")
        adapter.addIngredient(ingredientName)
    }

    private fun toast(text: String){
        val context = this
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }


}