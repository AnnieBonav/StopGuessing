package com.anniebonav.stopguessingm3.recycler

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.MPDeleteClickFunction
import com.anniebonav.stopguessingm3.MPOpenClickFunction
import com.anniebonav.stopguessingm3.R

class MealPlanViewHolder (view: View, private val onDeleteClick: MPDeleteClickFunction, private val onCardClick: MPOpenClickFunction): RecyclerView.ViewHolder(view) {
    val mealPlanCard: CardView
    val mealPlanNameLabel: TextView
    val mealPlanBlueprintLabel: TextView
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

    init{
        mealPlanCard = view.findViewById(R.id.mealplanCard)
        mealPlanNameLabel = view.findViewById(R.id.mealplanTitle)
        mealPlanBlueprintLabel = view.findViewById(R.id.mealplanBlueprint)
        mealPlanDeleteButton = view.findViewById(R.id.mealplanDelete)
    }
}