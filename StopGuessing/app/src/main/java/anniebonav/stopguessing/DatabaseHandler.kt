package anniebonav.stopguessing

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, dbName, null, 1 ) {
    companion object MessageDatabase{
        private const val dbName:String = "MessageDatabase"
        private const val tableName = "messages"
        private const val id = "_id"
        private const val msg = "text"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $tableName ($id INTEGER PRIMARY KEY, $msg VARCHAR(30));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun insertData(message: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(msg, message)
        db.insert(tableName, null, contentValues)
    }

    fun listOfMessages(): MutableList<String>  {
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from $tableName", null)
        val allMessages: MutableList<String> = mutableListOf()
        while (cursor.moveToNext()) {
            val i = cursor.getColumnIndex(msg);
            if (i >= 0)
                allMessages.add(cursor.getString(i))
        }
        return allMessages
    }

    fun updateData(id: String, message: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(msg, message)
        return db.update(tableName, contentValues, "_id = ?", arrayOf(id))
    }

    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(tableName,"_id = ?", arrayOf(id))
    }
}