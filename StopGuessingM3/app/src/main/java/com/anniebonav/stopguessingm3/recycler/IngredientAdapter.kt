package com.anniebonav.stopguessingm3.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.IngredientOpenClickFunction
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.Ingredients.Ingredient


class IngredientAdapter (private val context: Context, _ingredientsArrayList: List<Ingredient>, private val onCardClick: IngredientOpenClickFunction): RecyclerView.Adapter<IngredientViewHolder>(){
    private val _ingredientsArrayList: List<Ingredient>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view, onCardClick)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val model: Ingredient = _ingredientsArrayList[position]
        holder.ingredientNameLabel.setText(model.ingredientName)
        holder.ingredientCategoryLabel.setText(model.ingredientCategory)
        holder.ingredientAmountLabel.setText(model.ingredientAmount.toString())
        holder.ingredientMeasurementLabel.setText(model.ingredientMeasurement)
        holder.openIngredient(model.uid!!)
    }

    init {
        this._ingredientsArrayList = _ingredientsArrayList
    }

    override fun getItemCount(): Int {
        return _ingredientsArrayList.size
    }

}



