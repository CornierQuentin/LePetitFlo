package fr.cornier.lepetitflo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        link.movementMethod = LinkMovementMethod.getInstance();
    }

    fun onBackButtonClick(button: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
