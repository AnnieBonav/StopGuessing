package com.anniebonav.stopguessingm3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MealPlansAdapter (private var mpNames: List<String>, private val onIngredientClick: MPClickFunction): RecyclerView.Adapter<ViewHolder>(){

    fun AddIngredients(ingredients: MutableList<String>){

    }

    fun addIngredient(ingredientName: String){
        mpNames = mpNames.plus(ingredientName)
        val position = mpNames.count() -1
        this.notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_meal_plan, parent, false)
        return ViewHolder(view, onIngredientClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredientName = mpNames[position]
        holder.display(ingredientName)
    }

    override fun getItemCount(): Int {
        return mpNames.count()
    }
}