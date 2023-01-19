package com.anniebonav.stopguessingm3.data.Blueprints

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Blueprint (
    @PrimaryKey(autoGenerate = true )val uid: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "breakfast_units") val breakfastUnits: Int?,
    @ColumnInfo(name = "lunch_units") val lunchUnits: Int?,
    @ColumnInfo(name = "dinner_units") val dinnerUnits: Int?,
    @ColumnInfo(name = "morning_snack_units") val morningSnackUnits: Int?,
    @ColumnInfo(name = "evening_snack_units") val eveningSnackUnits: Int?,
)