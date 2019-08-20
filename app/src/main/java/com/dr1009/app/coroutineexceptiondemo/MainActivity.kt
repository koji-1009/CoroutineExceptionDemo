package com.dr1009.app.coroutineexceptiondemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dr1009.app.coroutineexceptiondemo.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}
