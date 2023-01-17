package com.anniebonav.stopguessingm3.recycler

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.MPDeleteClickFunction
import com.anniebonav.stopguessingm3.MPEditClickFunction
import com.anniebonav.stopguessingm3.MPOpenClickFunction
import com.anniebonav.stopguessingm3.R

class MealPlanViewHolder (view: View, private val onDeleteClick: MPDeleteClickFunction, private val onCardClick: MPOpenClickFunction, private val onEditClick: MPEditClickFunction): RecyclerView.ViewHolder(view) {
    val mealPlanCard: CardView
    val mealPlanNameLabel: TextView
    val mealPlanDescriptionLabel: TextView
    val mealPlanEditButton: Button
    val mealPlanDeleteButton: Button

    fun deleteMealPlan(mealPlanId: Int){
        mealPlanDeleteButton.setOnClickListener{
            onDeleteClick(mealPlanId)
        }
    }

    fun openMealPlan(mealPlanId: Int){
        mealPlanCard.setOnClickListener{
            onCardClick(mealPlanId)
        }
    }

    fun editMealPlan(mealPlanId: Int){
        mealPlanEditButton.setOnClickListener{
            onEditClick(mealPlanId)
        }
    }

    init{
        mealPlanCard = view.findViewById(R.id.mealPlanCard)
        mealPlanNameLabel = view.findViewById(R.id.mealplanTitle)
        mealPlanDescriptionLabel = view.findViewById(R.id.mealplanDescription)
        mealPlanEditButton = view.findViewById(R.id.mealplanEdit)
        mealPlanDeleteButton = view.findViewById(R.id.mealplanDelete)
    }
}