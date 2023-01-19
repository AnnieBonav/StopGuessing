package com.anniebonav.stopguessingm3.data.Ingredients

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IngredientDAO {
    @Query("SELECT * FROM ingredient")
    fun getIngredients(): List<Ingredient>

    @Query("SELECT * FROM ingredient WHERE uid = :ingredientId")
    fun getIngredient(ingredientId: Int): Ingredient

    @Query("SELECT * FROM ingredient WHERE category = :category")
    fun getIngredientsByCategory(category: String): List<Ingredient>

    @Query("SELECT * FROM ingredient")
    fun getAllLiveData(): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredient WHERE uid IN (:ingredientIds)")
    fun loadAllByIds(ingredientIds: IntArray): List<Ingredient>

    @Query("SELECT * FROM ingredient WHERE uid IN (:ingredientIds)")
    fun loadIngredientsByIds(ingredientIds: List<String>): List<Ingredient>

    @Insert
    fun insertAll(vararg ingredients: Ingredient)

    @Query("DELETE FROM ingredient WHERE uid = :ingredientId")
    fun deleteIngredient(ingredientId: Int)

    @Delete
    fun delete(ingredient: Ingredient)

    @Update
    fun update(vararg ingredients: Ingredient)
}