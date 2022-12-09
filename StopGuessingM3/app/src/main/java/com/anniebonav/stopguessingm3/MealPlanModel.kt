package com.anniebonav.stopguessingm3

class MealPlanModel (private var mealplanName: String, private var mealplanDescription: String, private var mealsAmount: Int, private var snacksAmount: Int) {
    fun getName(): String {
        return mealplanName
    }

    fun setName(name: String) {
        this.mealplanName = name
    }

    fun getDescription(): String {
        return mealplanDescription
    }

    fun setDescription(description: String) {
        this.mealplanDescription = description
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