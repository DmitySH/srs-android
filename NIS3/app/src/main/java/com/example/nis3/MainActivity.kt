package com.example.nis3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.lang.Exception

class MainActivity : AppCompatActivity() {
//    companion object {
//        var rememberText: String = ""
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonNext: Button = findViewById(R.id.buttonNext)
        val input: EditText = findViewById(R.id.input)

//        val prevText: String = intent.getStringExtra("textForPrint").toString()
//        if (prevText == "null") {
//            input.setText("")
//        } else {
//            input.setText(prevText)
//        }

//            input.setText(rememberText)

        buttonNext.setOnClickListener(View.OnClickListener {
//            rememberText = input.text.toString()
            val intent: Intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("textForPrint", input.text.toString())
            startActivity(intent)
        })
    }
}