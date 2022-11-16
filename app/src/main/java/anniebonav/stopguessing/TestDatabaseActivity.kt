package anniebonav.stopguessing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TestDatabaseActivity : AppCompatActivity() {
    private var myDb = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_database)

        findViewById<TextView>(R.id.displayedIngredients).apply {
            text = myDb.listOfMessages().toString()
        }
    }
}