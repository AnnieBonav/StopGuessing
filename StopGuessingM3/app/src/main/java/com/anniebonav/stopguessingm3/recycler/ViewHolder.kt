package com.anniebonav.stopguessingm3.recycler

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.R

//private val onClick: MPClickFunction
class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    val mealPlanNameLabel: TextView
    val mealPlanDescriptionLabel: TextView
    val mealPlanEditButton: Button
    val mealPlanDeleteButton: Button

    init{
        mealPlanNameLabel = itemView.findViewById(R.id.mealplanTitle)
        mealPlanDescriptionLabel = itemView.findViewById(R.id.mealplanDescription)
        mealPlanEditButton = itemView.findViewById(R.id.mealplanEdit)
        mealPlanDeleteButton = itemView.findViewById(R.id.mealplanDelete)
    }
}