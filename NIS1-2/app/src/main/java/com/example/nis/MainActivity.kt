package com.example.nis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userTextInput: EditText = findViewById(R.id.inputText)
        val userTextOutput: TextView = findViewById(R.id.userText)
        val userTextButton: Button = findViewById(R.id.inputButton)
        userTextButton.setOnClickListener(View.OnClickListener() {
            userTextOutput.text = if (userTextInput.text.toString() != "") {
                userTextInput.text.toString()
            } else {
                "Шляпа пуста :("
            }
        })

//        input = findViewById(R.id.inputText)
//        output = findViewById(R.id.userText)
    }

//    fun sendText(view: View) {
//        output.text = if (input.text.toString() != "") {
//            input.text.toString()
//        } else {
//            "Шляпа пуста :("
//        }
//    }
}