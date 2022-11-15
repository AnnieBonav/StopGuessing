package anniebonav.stopguessing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adapter (private var ingredientsNames: List<String>, private val onIngredientClick: IngredientClickFunction): RecyclerView.Adapter<ViewHolder>(){
    fun addIngredient(ingredientName: String){
        ingredientsNames = ingredientsNames.plus(ingredientName)
        val position = ingredientsNames.count() -1
        this.notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(view, onIngredientClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredientName = ingredientsNames[position]
        holder.display(ingredientName)
    }

    override fun getItemCount(): Int {
        return ingredientsNames.count()
    }
}