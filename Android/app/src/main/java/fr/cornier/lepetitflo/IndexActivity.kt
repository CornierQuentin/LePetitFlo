package fr.cornier.lepetitflo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class IndexActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
    }

    fun onBackButtonClick(button: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
