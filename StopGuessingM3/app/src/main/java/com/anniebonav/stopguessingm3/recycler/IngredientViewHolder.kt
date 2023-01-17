package com.anniebonav.stopguessingm3.recycler

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.IngredientDeleteClickFunction
import com.anniebonav.stopguessingm3.IngredientEditClickFunction
import com.anniebonav.stopguessingm3.IngredientOpenClickFunction
import com.anniebonav.stopguessingm3.R

class IngredientViewHolder (view: View, private val onDeleteClick: IngredientDeleteClickFunction, private val onCardClick: IngredientOpenClickFunction, private val onEditClick: IngredientEditClickFunction): RecyclerView.ViewHolder(view) {
    val ingredientCard: CardView
    val ingredientNameLabel: TextView
    val ingredientCategoryLabel: TextView
    val ingredientEditButton: Button
    val ingredientDeleteButton: Button

    fun deleteIngredient(ingredientId: Int){
        ingredientDeleteButton.setOnClickListener{
            onDeleteClick(ingredientId)
        }
    }

    fun openIngredient(ingredientId: Int){
        ingredientCard.setOnClickListener{
            onCardClick(ingredientId)
        }
    }

    fun editIngredient(ingredientId: Int){
        ingredientEditButton.setOnClickListener{
            onEditClick(ingredientId)
        }
    }

    init{
        ingredientCard = view.findViewById(R.id.ingredientCard)
        ingredientNameLabel = view.findViewById(R.id.ingredientName)
        ingredientCategoryLabel = view.findViewById(R.id.ingredientCategory)
        ingredientEditButton = view.findViewById(R.id.ingredientEdit)
        ingredientDeleteButton = view.findViewById(R.id.ingredientDelete)
    }
}