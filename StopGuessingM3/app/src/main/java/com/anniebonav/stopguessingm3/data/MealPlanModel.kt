package com.anniebonav.stopguessingm3.data

class MealPlanModel (private var mealPlanName: String, private var mealPlanDescription: String, private var mealsAmount: Int, private var snacksAmount: Int) {
    fun getName(): String {
        return mealPlanName
    }

    fun setName(name: String) {
        this.mealPlanName = name
    }

    fun getDescription(): String {
        return mealPlanDescription
    }

    fun setDescription(description: String) {
        this.mealPlanDescription = description
    }

    fun getMealsAmount():Int {
        return mealsAmount
    }

    fun setMealsAmount(amount: Int) {
        this.mealsAmount = amount
    }

    fun getSnacksAmount(): Int {
        return snacksAmount
    }

    fun setSnacksAmount(amount: Int){
        this.snacksAmount = amount
    }
}