package com.anniebonav.stopguessingm3.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.IngredientDeleteClickFunction
import com.anniebonav.stopguessingm3.IngredientEditClickFunction
import com.anniebonav.stopguessingm3.IngredientOpenClickFunction
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.Ingredients.Ingredient


class IngredientAdapter (private val context: Context, _ingredientsArrayList: List<Ingredient>, private val onDeleteClick: IngredientDeleteClickFunction, private val onCardClick: IngredientOpenClickFunction, private val onEditClicked: IngredientEditClickFunction): RecyclerView.Adapter<IngredientViewHolder>(){
    private val _ingredientsArrayList: List<Ingredient>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view, onDeleteClick, onCardClick, onEditClicked)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val model: Ingredient = _ingredientsArrayList[position]
        holder.ingredientNameLabel.setText(model.ingredientName)
        holder.ingredientCategoryLabel.setText(model.ingredientCategory)
        holder.ingredientEditButton.setText("Edit")
        holder.ingredientDeleteButton.setText("Delete")
        holder.deleteIngredient(model.uid!!) //I know it is going to be an Int
        holder.openIngredient(model.uid)
        holder.editIngredient(model.uid)
    }

    init {
        this._ingredientsArrayList = _ingredientsArrayList
    }

    override fun getItemCount(): Int {
        return _ingredientsArrayList.size
    }

}



