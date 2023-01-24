package com.anniebonav.stopguessingm3.recycler

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.MPDeleteClickFunction
import com.anniebonav.stopguessingm3.MPFavoriteClickFunction
import com.anniebonav.stopguessingm3.MPOpenClickFunction
import com.anniebonav.stopguessingm3.R

class MealPlanViewHolder (view: View, private val onDeleteClick: MPDeleteClickFunction, private val onCardClick: MPOpenClickFunction, private val onFavoriteClick: MPFavoriteClickFunction): RecyclerView.ViewHolder(view) {
    val mealPlanCard: CardView
    val mealPlanNameLabel: TextView
    val mealPlanBlueprintLabel: TextView
    val mealPlanDeleteButton: ImageView
    val mealPlanStarButton: Button

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

    fun favoriteMealPlan(mealPlanId: Int){
        mealPlanStarButton.setOnClickListener{
            onFavoriteClick(mealPlanId)
        }
    }

    init{
        mealPlanCard = view.findViewById(R.id.mealplanCard)
        mealPlanNameLabel = view.findViewById(R.id.mealplanTitle)
        mealPlanBlueprintLabel = view.findViewById(R.id.mealplanBlueprint)
        mealPlanDeleteButton = view.findViewById(R.id.mealplanDelete)
        mealPlanStarButton = view.findViewById(R.id.mealplanFavorite)
    }
}