package com.anniebonav.stopguessingm3.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.BlueprintDeleteClickFunction
import com.anniebonav.stopguessingm3.BlueprintEditClickFunction
import com.anniebonav.stopguessingm3.BlueprintOpenClickFunction
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.Blueprints.Blueprint
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlan

class BlueprintAdapter (private val context: Context, _blueprintsArrayList: List<Blueprint>, private val onDeleteClick: BlueprintDeleteClickFunction, private val onCardClick: BlueprintOpenClickFunction, private val onEditClicked: BlueprintEditClickFunction): RecyclerView.Adapter<BlueprintViewHolder>(){
    private val _blueprintsArrayList: List<Blueprint>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlueprintViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_blueprint, parent, false)
        return BlueprintViewHolder(view, onDeleteClick, onCardClick, onEditClicked)
    }

    override fun onBindViewHolder(holder: BlueprintViewHolder, position: Int) {
        val model: Blueprint = _blueprintsArrayList[position]
        //holder.something comes from ViewHolder variables, that refer to the XML
        //model.something refers to the Blueprint data type
        holder.nameLabel.setText(model.name)
        holder.descriptionLabel.setText(model.description)
        holder.editButton.setText("Edit")
        holder.deleteButton.setText("Delete")

        holder.deleteBlueprint(model.uid!!) //I know it is going to be an Int, these are the functions in my holder
        holder.openBlueprint(model.uid)
        holder.editBlueprint(model.uid)
    }

    init {
        this._blueprintsArrayList = _blueprintsArrayList
    }

    override fun getItemCount(): Int {
        return _blueprintsArrayList.size
    }
}