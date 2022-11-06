package anniebonav.stopguessing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class IngredientsAdapter (private var ingredientsNames: List<String>, private val onIngredientClick: IngredientClickFunction): RecyclerView.Adapter<IngredientViewHolder>(){
    fun addIngredient(ingredientName: String){
        ingredientsNames = ingredientsNames.plus(ingredientName)
        val position = ingredientsNames.count() -1
        this.notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view, onIngredientClick)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredientName = ingredientsNames[position]
        holder.display(ingredientName)
    }

    override fun getItemCount(): Int {
        return ingredientsNames.count()
    }
}