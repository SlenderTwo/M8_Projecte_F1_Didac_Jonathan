package com.example.m8_projecte_f1_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
    lateinit var auth: FirebaseAuth //FIREBASE AUTENTIFICACIO




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registre)

        // Busquem a R els elements als que apunten les variables
        correoEt = findViewById<EditText>(R.id.correoEt)
        passEt = findViewById<EditText>(R.id.passEt)
        nombreEt = findViewById<EditText>(R.id.nombreEt)
        fechaTxt = findViewById<TextView>(R.id.fechaEt)
        Registrar = findViewById<Button>(R.id.Registrar)


        //carreguem la data al TextView
        //Utilitzem calendar (hi ha moltes altres opcions)
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance() //or use getDateInstance()
        val formatedDate = formatter.format(date)
        //ara la mostrem al TextView
        fechaTxt.setText(formatedDate)

        //Instanciem el firebaseAuth
        auth = FirebaseAuth.getInstance()

        Registrar.setOnClickListener() {
            //Abans de fer el registre validem les dades
            33
            var email: String = correoEt.getText().toString()
            var pass: String = passEt.getText().toString()
            // validació del correu
            // si no es de tipus correu
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                correoEt.setError("Invalid Mail")
            } else if (pass.length <6) {
                passEt.setError("Password less than 6 chars")
            } else {
                RegistrarJugador(email, pass)
            }
        }
    }
    fun RegistrarJugador(email:String, passw:String){
        auth.createUserWithEmailAndPassword(email, passw)
            .addOnCompleteListener(this) { task ->
                34
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        this,"createUserWithEmail:success",Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }





    }
    fun updateUI(user: FirebaseUser?){
        //hi ha un interrogant perquè podria ser null
        if (user!=null)
        {
            var puntuacio: Int = 0
            var uidString: String = user.uid
            var correoString: String = correoEt.getText().toString()
            var passString: String = passEt.getText().toString()
            var nombreString: String = nombreEt.getText().toString()
            var fechaString: String= fechaTxt.getText().toString()

            //AQUI GUARDA EL CONTINGUT A LA BASE DE DADES
            // Utilitza un HashMap
            var dadesJugador : HashMap<String,String> = HashMap<String, String>()
            dadesJugador.put ("Uid",uidString)
            dadesJugador.put ("Email",correoString)
            dadesJugador.put ("Password",passString)
            dadesJugador.put ("Nom",nombreString)
            dadesJugador.put ("Data",fechaString)
            dadesJugador.put ("Puntuacio", puntuacio.toString())

            // Creem un punter a la base de dades i li donem un nom
            var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://m8-projecte-f1-2-default-rtdb.europe-west1.firebasedatabase.app/")
            var reference: DatabaseReference = database.getReference("DATA BASE JUGADORS")
            if(reference!=null) {
                //crea un fill amb els valors de dadesJugador
                reference.child(uidString).setValue(dadesJugador)
                Toast.makeText(this, "USUARI BEN REGISTRAT",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "ERROR BD", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
        else
        {
            Toast.makeText( this,"ERROR CREATE USER",Toast.LENGTH_SHORT).show()
        }



    }

}