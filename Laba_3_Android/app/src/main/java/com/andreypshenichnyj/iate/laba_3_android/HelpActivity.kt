package com.andreypshenichnyj.iate.laba_3_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }

    fun closeActivity(view: View) {
        finish()
    }
}