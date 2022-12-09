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
    //private val mealplanCard: MaterialCardView
    init{
        mealplanNameTV = itemView.findViewById(R.id.mealplanTitle)
    }
}