package com.example.laboratorsma

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var messageKeeper: MessageKeeper = MessageKeeper("Hello world!");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.textView).apply {
            text = messageKeeper.message;
        }
    }

    fun sendMessage(view: View) {
        val editText = findViewById<EditText>(R.id.editTextTextPersonName)
        messageKeeper.message = editText.text.toString()
        findViewById<TextView>(R.id.textView).apply {
            text = messageKeeper.message
        }
    }

}