package com.anniebonav.stopguessingm3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MealPlansAdapter (private var ingredientsNames: List<String>, private val onIngredientClick: MPClickFunction): RecyclerView.Adapter<ViewHolder>(){
    fun addIngredient(ingredientName: String){
        ingredientsNames = ingredientsNames.plus(ingredientName)
        val position = ingredientsNames.count() -1
        this.notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_meal_plan, parent, false)
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