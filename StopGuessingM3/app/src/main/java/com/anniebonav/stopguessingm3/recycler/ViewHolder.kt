package com.anniebonav.stopguessingm3.recycler

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.MPDeleteClickFunction
import com.anniebonav.stopguessingm3.R

class ViewHolder (view: View, private val onDeleteClick: MPDeleteClickFunction): RecyclerView.ViewHolder(view) {
    val mealPlanNameLabel: TextView
    val mealPlanDescriptionLabel: TextView
    val mealPlanEditButton: Button
    val mealPlanDeleteButton: Button

    fun deleteMealPlan(ingredientId: Int){
        mealPlanDeleteButton.setOnClickListener{
            onDeleteClick(ingredientId)
        }
    }

    init{
        mealPlanNameLabel = view.findViewById(R.id.mealplanTitle)
        mealPlanDescriptionLabel = view.findViewById(R.id.mealplanDescription)
        mealPlanEditButton = view.findViewById(R.id.mealplanEdit)
        mealPlanDeleteButton = view.findViewById(R.id.mealplanDelete)
    }
}