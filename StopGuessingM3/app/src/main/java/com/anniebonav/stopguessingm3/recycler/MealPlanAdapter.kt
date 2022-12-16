package com.anniebonav.stopguessingm3.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anniebonav.stopguessingm3.MPDeleteClickFunction
import com.anniebonav.stopguessingm3.R
import com.anniebonav.stopguessingm3.data.MealPlan
import com.anniebonav.stopguessingm3.data.MealPlanModel

class MealPlanAdapter (private val context: Context, _mealPlansArrayList: List<MealPlan>, private val onDeleteClick: MPDeleteClickFunction): RecyclerView.Adapter<ViewHolder>(){
    private val _mealPlansArrayList: List<MealPlan>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_mealplan, parent, false)
        return ViewHolder(view, onDeleteClick, )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: MealPlan = _mealPlansArrayList[position]
        holder.mealPlanNameLabel.setText(model.mealPlanName)
        holder.mealPlanDescriptionLabel.setText(model.mealPlanDescription)
        holder.mealPlanEditButton.setText("Edit")
        holder.mealPlanDeleteButton.setText("Delete")
        holder.deleteMealPlan(model.uid!!) //I know it is going to be an Int
    }

    init {
        this._mealPlansArrayList = _mealPlansArrayList
    }

    /*
    //private var mpNames: List<String>, private val onIngredientClick: MPClickFunction
    fun AddIngredients(ingredients: MutableList<String>){

    }

    fun addIngredient(ingredientName: String){
        mpNames = mpNames.plus(ingredientName)
        val position = mpNames.count() -1
        this.notifyItemInserted(position)
    }
    */

    override fun getItemCount(): Int {
        return _mealPlansArrayList.size
    }
}