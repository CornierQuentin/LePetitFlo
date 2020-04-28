package fr.cornier.lepetitflo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val set = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun test (button: View) {
        val result = (1..24).random()

        set.clone(constraintLayout)
        set.connect(player1.id, ConstraintSet.TOP, case21.id, ConstraintSet.TOP, 25)
        set.connect(player1.id, ConstraintSet.START, case21.id, ConstraintSet.START, 20)
        set.applyTo(constraintLayout)
    }

}
