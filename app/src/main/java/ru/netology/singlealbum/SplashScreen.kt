package ru.netology.singlealbum

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.netology.singlealbum.databinding.SplashScreenBinding

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {


    private val binding by lazy { SplashScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val backgroundImage: ImageView = binding.logoIcon
        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        backgroundImage.startAnimation(sideAnimation)

        val titleView: TextView = binding.title
        val artistView: TextView = binding.artist
        val fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_up)

        titleView.startAnimation(fadeAnimation)
        artistView.startAnimation(fadeAnimation)


        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)

    }


}