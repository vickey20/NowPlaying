package com.vikram.nowplaying.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.vikram.nowplaying.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, SongsFragment.newInstance())
                    .commit()
        }
    }
}
