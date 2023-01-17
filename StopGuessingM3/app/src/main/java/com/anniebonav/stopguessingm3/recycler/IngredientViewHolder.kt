package com.anniebonav.stopguessingm3.recycler

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.IngredientOpenClickFunction
import com.anniebonav.stopguessingm3.R

class IngredientViewHolder (view: View, private val onCardClick: IngredientOpenClickFunction): RecyclerView.ViewHolder(view) {
    val ingredientCard: CardView
    val ingredientNameLabel: TextView
    val ingredientCategoryLabel: TextView
    val ingredientAmountLabel: TextView
    val ingredientMeasurementLabel: TextView

    fun openIngredient(ingredientId: Int){
        ingredientCard.setOnClickListener{
            onCardClick(ingredientId)
        }
    }

    init{
        ingredientCard = view.findViewById(R.id.ingredientCard)
        ingredientNameLabel = view.findViewById(R.id.ingredientName)
        ingredientCategoryLabel = view.findViewById(R.id.ingredientCategory)
        ingredientAmountLabel = view.findViewById(R.id.ingredientAmount)
        ingredientMeasurementLabel = view.findViewById(R.id.ingredientMeasurement)
    }
}