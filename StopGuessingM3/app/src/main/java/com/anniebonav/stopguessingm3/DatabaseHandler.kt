package com.anniebonav.stopguessingm3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context):SQLiteOpenHelper(context, dbName, null, 1) {
    companion object StopGuessingDatabase{
        private const val dbName:String = "StopGuessingDatabase"
        private const val tableName = "_tableName"
        private const val id = "_id"
        private const val objectName = "_text"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $tableName ($id INTEGER PRIMARY KEY, $objectName VARCHAR(30));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun insertData(message: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(objectName, message)
        db.insert(tableName, null, contentValues)
    }

    fun listOfMessages(): MutableList<String>  {
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from $tableName", null)
        val allMessages: MutableList<String> = mutableListOf()
        while (cursor.moveToNext()) {
            val i = cursor.getColumnIndex(objectName);
            if (i >= 0)
                allMessages.add(cursor.getString(i))
        }
        return allMessages
    }

    fun updateData(id: String, message: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(objectName, message)
        return db.update(tableName, contentValues, "_id = ?", arrayOf(id))
    }

    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(tableName,"_id = ?", arrayOf(id))
    }
}