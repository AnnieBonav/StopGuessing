package com.anniebonav.stopguessingm3.recycler

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.IngredientOpenClickFunction
import com.anniebonav.stopguessingm3.R

class IngredientViewHolder (view: View, private val onCardClick: IngredientOpenClickFunction,private val onEditClick: IngredientOpenClickFunction, private val onDeleteClick: IngredientOpenClickFunction): RecyclerView.ViewHolder(view) {
    val ingredientCard: CardView //Not using it at the moment
    val ingredientNameLabel: TextView
    val ingredientCategoryLabel: TextView
    val ingredientAmountLabel: TextView
    val ingredientUnitLabel: TextView

    val editIngredientButton: Button
    val deleteIngredientButton: Button

    /*
    fun openIngredient(ingredientId: Int){
        ingredientCard.setOnClickListener{
            onCardClick(ingredientId)
        }
    }*/
    fun editIngredient(ingredientId: Int){
        editIngredientButton.setOnClickListener{
            onEditClick(ingredientId)
        }
    }

    fun deleteIngredient(ingredientId: Int){
        deleteIngredientButton.setOnClickListener{
            onDeleteClick(ingredientId)
        }
    }

    init{
        ingredientCard = view.findViewById(R.id.ingredientCard)
        ingredientNameLabel = view.findViewById(R.id.ingredientName)
        ingredientCategoryLabel = view.findViewById(R.id.ingredientCategory)
        ingredientAmountLabel = view.findViewById(R.id.ingredientAmount)
        ingredientUnitLabel = view.findViewById(R.id.ingredientUnit)

        editIngredientButton = view.findViewById(R.id.editIngredientButton)
        deleteIngredientButton = view.findViewById(R.id.deleteIngredientButton)
    }
}