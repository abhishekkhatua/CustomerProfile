package `in`.co.ngcapp.customaccount

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager

class  SplashActivity :AppCompatActivity(){
    var TIME_SPLASH_SCREEN = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_activity)
        Handler().postDelayed({


                startActivity(Intent(this@SplashActivity, MainActivity::class.java))

                finish()
        }, TIME_SPLASH_SCREEN.toLong())

    }
}