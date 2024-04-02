package com.example.m8_projecte_f1_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SeleccioNivel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccio_nivel)


        val buttonNivel1: Button = findViewById(R.id.buttonNivel1)
        val buttonNivel2: Button = findViewById(R.id.buttonNivel2)
        val buttonNivel3: Button = findViewById(R.id.buttonNivel3)

        buttonNivel1.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("NIVEL", 0)
            startActivity(intent)

        }

        buttonNivel2.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("NIVEL", 1)
            startActivity(intent)
        }

        buttonNivel3.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("NIVEL", 2)
            startActivity(intent)
        }
    }
}
