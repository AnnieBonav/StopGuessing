package com.anniebonav.stopguessingm3.recycler

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
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
    val descriptionLabel: TextView
    val deleteButton: ImageView
    val editButton: ImageView

    fun deleteBlueprint(blueprintId: Int){
        deleteButton.setOnClickListener{
            onDeleteClick(blueprintId)
        }
    }

    fun openBlueprint(blueprintId: Int){
        card.setOnClickListener{
            onCardClick(blueprintId)
            Log.d("Holder", "$blueprintId")
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
        descriptionLabel = view.findViewById(R.id.description)
        deleteButton = view.findViewById(R.id.deleteButton)
        editButton = view.findViewById(R.id.editButton)
    }
}