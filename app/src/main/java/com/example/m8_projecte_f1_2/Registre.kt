package com.example.m8_projecte_f1_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.DateFormat.getDateInstance
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.log

class Registre : AppCompatActivity() {
    //Definim les variables que farem servir
    //lateinit ens permet no inicialitzar-les encara
    lateinit var correoEt : EditText
    lateinit var passEt :EditText
    lateinit var nombreEt :EditText
    lateinit var fechaTxt :TextView
    lateinit var Registrar :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registre)

        // Busquem a R els elements als que apunten les variables
        correoEt =findViewById<EditText>(R.id.correoEt)
        passEt =findViewById<EditText>(R.id.passEt)
        nombreEt =findViewById<EditText>(R.id.nombreEt)
        fechaTxt =findViewById<TextView>(R.id.fechaEt)
        Registrar =findViewById<Button>(R.id.Registrar)

        Log.d("Jonathan","Inici")

        //carreguem la data al TextView
        //Utilitzem calendar (hi ha moltes altres opcions)
        val date = Calendar.getInstance().time
        Log.d("Jonathan",date.toString())
        Toast.makeText(this, date.toString(),Toast.LENGTH_LONG).show();

        val formatter = SimpleDateFormat.getDateInstance() //or use getDateInstance()
        val formatedDate = formatter.format(date)
        //ara la mostrem al TextView
        fechaTxt.setText(formatedDate)




    }


}