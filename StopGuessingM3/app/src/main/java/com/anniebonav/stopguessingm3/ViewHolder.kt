package com.anniebonav.stopguessingm3

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class ViewHolder (view: View, private val onClick: MPClickFunction): RecyclerView.ViewHolder(view) {
    private val ingredientButton: Button
    init{
        ingredientButton = view as Button
    }

    fun display(ingredientName: String){
        ingredientButton.text = ingredientName
        ingredientButton.setOnClickListener{
            onClick(ingredientName)
        }
    }
}