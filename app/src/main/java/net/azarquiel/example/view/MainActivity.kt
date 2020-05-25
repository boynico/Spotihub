package net.azarquiel.example.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import net.azarquiel.example.R

class MainActivity : AppCompatActivity() {

    val FLAGS = (View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideNavigation()

        //Redirección a pantalla de login
        btn_mainActivity.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //pongo wallpaper
        //Picasso.get().load("https://farm8.staticflickr.com/7448/10050196204_4db0ee48b8_b.jpg").resize(1510, 2960).centerCrop().into(iv_mainscreen)
    }

    fun hideNavigation() {
        window.decorView.apply {
            systemUiVisibility = FLAGS
        }
    }
}



