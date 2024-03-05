package com.example.m8_projecte_f1_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var BTMLOGIN = findViewById<Button>(R.id.BTMLOGIN);
        var BTMREGISTRO = findViewById<Button>(R.id.BTMREGISTRO);
        BTMLOGIN.setOnClickListener(){
            Toast.makeText(this, "click botó login",Toast.LENGTH_LONG).show();
            val intent= Intent(this, Login::class.java)
            startActivity(intent)
        }
        BTMREGISTRO.setOnClickListener(){
            Toast.makeText(this, "click botó Registre",Toast.LENGTH_LONG).show();
            val intent = Intent(this,Registre::class.java)
            startActivity(intent)
        }
    }
    // Aquest mètode s'executarà quan s'obri el menu
    override fun onStart() {
        usuariLogejat()
        super.onStart()
    }
    private fun usuariLogejat() {
        if (user !=null)
        {
            val intent= Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }
    }
}