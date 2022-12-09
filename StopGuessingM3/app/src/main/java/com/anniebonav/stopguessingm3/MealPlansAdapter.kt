package com.anniebonav.stopguessingm3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MealPlansAdapter (private val context: Context, mealplansArrayList: ArrayList<MealPlanModel>): RecyclerView.Adapter<ViewHolder>(){
    private val mealplansArrayList: ArrayList<MealPlanModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val context = parent.context
        //val inflater = LayoutInflater.from(context)
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_mealplan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: MealPlanModel = mealplansArrayList[position]
        holder.mealplanNameTV.setText(model.getName())
        holder.mealplanDescriptionTV.setText(model.getDescription())
        holder.mealplanEditButton.setText("Edit")
        holder.mealplanDeleteButton.setText("Delete")
    }

    init {
        this.mealplansArrayList = mealplansArrayList
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
        return mealplansArrayList.size
    }
}