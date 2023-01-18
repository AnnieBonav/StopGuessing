package com.anniebonav.stopguessingm3.data.Ingredients

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true )val uid: Int?,
    @ColumnInfo(name = "name") val ingredientName: String?,
    @ColumnInfo(name = "category") val ingredientCategory: String?,
    @ColumnInfo(name = "amount") val ingredientAmount: Int?,
    @ColumnInfo(name = "measurement") val ingredientUnit: String?,
)