package com.anniebonav.stopguessingm3

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
//private val onClick: MPClickFunction
class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    public val mealplanNameTV: TextView
    public val mealplanDescriptionTV: TextView
    public val mealplanEditButton: Button
    public val mealplanDeleteButton: Button
    //private val mealplanCard: MaterialCardView
    init{
        mealplanNameTV = itemView.findViewById(R.id.mealplanTitle)
        mealplanDescriptionTV = itemView.findViewById(R.id.mealplanDescription)
        mealplanEditButton = itemView.findViewById(R.id.mealplanEdit)
        mealplanDeleteButton = itemView.findViewById(R.id.mealplanDelete)
    }
}