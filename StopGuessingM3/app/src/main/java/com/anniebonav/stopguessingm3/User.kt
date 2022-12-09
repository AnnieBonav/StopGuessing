package com.anniebonav.stopguessingm3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "name") val firstName: String?,
    @ColumnInfo(name = "city") val lastName: String?
)
