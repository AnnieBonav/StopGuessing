package com.anniebonav.stopguessingm3.recycler

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.BlueprintDeleteClickFunction
import com.anniebonav.stopguessingm3.BlueprintEditClickFunction
import com.anniebonav.stopguessingm3.BlueprintOpenClickFunction
import com.anniebonav.stopguessingm3.R

class BlueprintViewHolder (view: View, private val onDeleteClick: BlueprintDeleteClickFunction, private val onCardClick: BlueprintOpenClickFunction, private val onEditClick: BlueprintEditClickFunction): RecyclerView.ViewHolder(view) {
    val card: CardView
    val nameLabel: TextView
    val breakfastUnitsLabel: TextView
    val lunchUnitsLabel: TextView
    val dinnerUnitsLabel: TextView
    val deleteButton: Button
    val editButton: Button

    fun deleteBlueprint(blueprintId: Int){
        deleteButton.setOnClickListener{
            onDeleteClick(blueprintId)
        }
    }

    fun openBlueprint(blueprintId: Int){
        card.setOnClickListener{
            onCardClick(blueprintId)
        }
    }

    fun editBlueprint(blueprintId: Int){
        editButton.setOnClickListener{
            onEditClick(blueprintId)
        }
    }

    init{
        card = view.findViewById(R.id.card)
        nameLabel = view.findViewById(R.id.name)
        breakfastUnitsLabel = view.findViewById(R.id.breakfastUnits)
        lunchUnitsLabel = view.findViewById(R.id.lunchUnits)
        dinnerUnitsLabel = view.findViewById(R.id.dinnerUnits)
        deleteButton = view.findViewById(R.id.deleteButton)
        editButton = view.findViewById(R.id.editButton)
    }
}