package fr.cornier.lepetitflo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.NumberPicker.OnValueChangeListener
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : AppCompatActivity() {

    private val pickerValues = arrayOf("1", "2", "3", "4", "5", "6")
    private val playerValue = arrayOf(1, 2, 3, 4, 5, 6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        numberPicker.maxValue = 5
        numberPicker.minValue = 0

        numberPicker.displayedValues = pickerValues
    }

    fun onBackButtonClick(button: View) {
        val sharedPreferences = getSharedPreferences("fr.cornier.lepetitflo", Context.MODE_PRIVATE).edit()
        sharedPreferences.putInt("numberPickerValue", playerValue[numberPicker.value])
        sharedPreferences.apply()

        val pickerValue = playerValue[numberPicker.value]
        Log.i("Test", "$pickerValue")

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun onStartButtonClick(button: View) {
        val sharedPreferences = getSharedPreferences("fr.cornier.lepetitflo", Context.MODE_PRIVATE).edit()
        for (i in 0..5) {
            sharedPreferences.putInt("score$i", 0)
        }
        sharedPreferences.putInt("playerTurn", 0)

        sharedPreferences.putInt("playerNumber", playerValue[numberPicker.value])
        sharedPreferences.apply()

        val pickerValue = playerValue[numberPicker.value]
        Log.i("Test", "$pickerValue")

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
