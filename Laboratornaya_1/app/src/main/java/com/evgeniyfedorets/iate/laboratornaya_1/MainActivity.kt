package com.evgeniyfedorets.iate.laboratornaya_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.evgeniyfedorets.iate.laboratornaya_1.practice.StringsExercise

class MainActivity : AppCompatActivity() {
    var exercise: StringsExercise = StringsExercise()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val main_button = findViewById(R.id.main_button) as Button
        val input_text = findViewById(R.id.input_view) as TextView
        val output_view = findViewById(R.id.output_view) as TextView

        input_text.setText(exercise.inputData)

        main_button.setOnClickListener(){
            output_view.setText(exercise.result)
        }
    }
}