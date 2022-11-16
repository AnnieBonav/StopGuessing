package anniebonav.stopguessing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MealPlansActivity : AppCompatActivity() {
    private var myDb = DatabaseHandler(this)

    fun saveMessage(view: View){
        myDb.insertData(findViewById<EditText>(R.id.ingredientNameInput).text.toString())
        startActivity(Intent(this,TestDatabaseActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_plans)
    }
}