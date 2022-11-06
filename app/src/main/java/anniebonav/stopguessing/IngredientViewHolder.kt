package anniebonav.stopguessing

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class IngredientViewHolder (view: View, private val onClick: IngredientClickFunction): RecyclerView.ViewHolder(view){
    private val ingredientButton: Button
    init{
        ingredientButton = view as Button
    }

    fun display(ingredientName: String){
        ingredientButton.text = ingredientName
        ingredientButton.setOnClickListener{
            onClick(ingredientName)
        }
    }
}