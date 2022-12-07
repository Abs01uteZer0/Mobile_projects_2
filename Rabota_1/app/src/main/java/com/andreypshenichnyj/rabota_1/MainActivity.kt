package com.andreypshenichnyj.rabota_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.andreypshenichnyj.rabota_1.practice.StringsExercise

class MainActivity : AppCompatActivity() {
    var exercise: StringsExercise = StringsExercise()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val runButton = findViewById(R.id.runButton) as Button
        val inputView = findViewById(R.id.inputView) as TextView
        val outputView = findViewById(R.id.outputView) as TextView
        outputView.setText("")

        inputView.setText(exercise.inputData)

        runButton.setOnClickListener(){
            outputView.setText(exercise.result)
        }
    }
}