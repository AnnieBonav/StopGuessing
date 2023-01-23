package com.anniebonav.stopguessingm3.data.MealPlan

import androidx.room.*
import com.anniebonav.stopguessingm3.data.Blueprints.Blueprint

@Entity(
        foreignKeys = [ForeignKey(
        entity = Blueprint::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("blueprintId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)]
)
data class MealPlan(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    val blueprintId: Int?,
    @ColumnInfo(name = "name") val mealPlanName: String?,
    @ColumnInfo(name = "description") val mealPlanDescription: String?, //Which Blueprint it is based on
    @ColumnInfo(name = "breakfasts") val mealPlanBreakfasts: String?,
    @ColumnInfo(name = "lunches") val mealPlanLunches: String?,
    @ColumnInfo(name = "dinners") val mealPlanDinners: String?,
    @ColumnInfo(name = "morning_snacks") val mealPlanMorningSnacks: String?,
    @ColumnInfo(name = "evening_snacks") val mealPlanEveningSnacks: String?,
    @ColumnInfo(name = "is_selected") val mealPlanIsSelected: Int?, //Int is either 1 or 0 //TODO: Check if defaultValue = "0" can be used
)

data class BlueprintAndMealPlans(
    @Embedded val blueprint: Blueprint,
    @Relation(
        parentColumn = "uid",
        entityColumn = "blueprintId"
    )
    val mealPlans: List<MealPlan>
)
