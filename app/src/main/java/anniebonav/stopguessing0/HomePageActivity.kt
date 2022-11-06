package anniebonav.stopguessing0

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class HomePageActivity : AppCompatActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        //ADDING IMAGE TO BUTTON
        // Declaring and initializing the button from the layout file
        val seeIngredientsBtn = findViewById<Button>(R.id.seeIngredientsButton)
        val seeMealPlansBtn = findViewById<Button>(R.id.seeMealPlansButton)

        // Storing the drawable location in a local value
        val ingredientsImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getDrawable(R.drawable.ingredients_vector)
        } else {
            TODO()
        }

        val mealPlansImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getDrawable(R.drawable.recipes1)
        } else {
            TODO()
        }

        // Compounding the button with drawables Here image will be at the left of the text.
        seeIngredientsBtn.setCompoundDrawablesWithIntrinsicBounds(ingredientsImage, null, null, null)
        seeMealPlansBtn.setCompoundDrawablesWithIntrinsicBounds(mealPlansImage, null, null, null)

    }

    fun goToSeeIngredients(view: View){
        startActivity(Intent(this, IngredientsActivity::class.java))
    }
}