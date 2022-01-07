package com.example.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var i: Int = 0
    private lateinit var text: TextView
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.textV)
        button = findViewById(R.id.button)

        text.text = i.toString()

        button.setOnClickListener {
            ++i
            text.text = i.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("i", i)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        i = savedInstanceState.getInt("i")
        text.text = i.toString()
    }
}