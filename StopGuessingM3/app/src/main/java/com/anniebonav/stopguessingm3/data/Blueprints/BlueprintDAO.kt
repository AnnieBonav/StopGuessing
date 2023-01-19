package com.anniebonav.stopguessingm3.data.Blueprints

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BlueprintDAO {
    @Query("SELECT * FROM blueprint")
    fun getBlueprints(): List<Blueprint>

    @Query("SELECT * FROM blueprint WHERE uid = :blueprintId")
    fun getBlueprint(blueprintId: Int): Blueprint

    @Query("SELECT * FROM blueprint WHERE name = :blueprintName")
    fun getBlueprint(blueprintName: String): Blueprint

    @Query("SELECT * FROM blueprint")
    fun getAllLiveData(): LiveData<List<Blueprint>>

    @Query("SELECT * FROM blueprint WHERE uid IN (:blueprintIds)")
    fun loadAllByIds(blueprintIds: IntArray): List<Blueprint>

    @Insert
    fun insertAll(vararg blueprint: Blueprint)

    @Delete
    fun delete(blueprint: Blueprint)

    @Update
    fun update(vararg blueprint: Blueprint)
}