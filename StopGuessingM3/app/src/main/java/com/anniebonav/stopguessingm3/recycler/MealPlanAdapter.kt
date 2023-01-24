package com.anniebonav.stopguessingm3.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.MPDeleteClickFunction
import com.anniebonav.stopguessingm3.MPFavoriteClickFunction
import com.anniebonav.stopguessingm3.MPOpenClickFunction
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.MealPlan.MealPlan

class MealPlanAdapter (private val context: Context, _mealPlansArrayList: List<MealPlan>, private val onDeleteClick: MPDeleteClickFunction, private val onCardClick: MPOpenClickFunction, private val onFavoriteClick: MPFavoriteClickFunction): RecyclerView.Adapter<MealPlanViewHolder>(){
    private val _mealPlansArrayList: List<MealPlan>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealPlanViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_mealplan, parent, false)
        return MealPlanViewHolder(view, onDeleteClick, onCardClick, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: MealPlanViewHolder, position: Int) {
        val model: MealPlan = _mealPlansArrayList[position]
        holder.mealPlanNameLabel.setText(model.mealPlanName)
        holder.mealPlanBlueprintLabel.setText(model.mealPlanDescription)
        if(model.mealPlanIsSelected == 0){
            holder.mealPlanStarButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_border_24, 0, 0, 0)
        }else{
            holder.mealPlanStarButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_24, 0, 0, 0)
        }
        holder.deleteMealPlan(model.uid!!) //I know it is going to be an Int
        holder.openMealPlan(model.uid)
        holder.favoriteMealPlan(model.uid)
    }

    init {
        this._mealPlansArrayList = _mealPlansArrayList
    }

    override fun getItemCount(): Int {
        return _mealPlansArrayList.size
    }
}