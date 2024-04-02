package com.example.m8_projecte_f1_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Login : AppCompatActivity() {
    //Despleguem les variables que farem servir
    lateinit var correoLogin: EditText
    lateinit var passLogin: EditText
    lateinit var BtnLogin: Button
    lateinit var auth: FirebaseAuth //FIREBASE AUTENTIFICACIO



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Busquem a R els elements als que apunten les variables
        correoLogin = findViewById<EditText>(R.id.correoLogin)
        passLogin = findViewById<EditText>(R.id.passLogin)
        BtnLogin = findViewById<Button>(R.id.BtnLogin)
        //Instanciem el firebaseAuth
        auth = FirebaseAuth.getInstance()

        BtnLogin.setOnClickListener(){
            //Abans de fer el registre validem les dades
            val email:String = correoLogin.getText().toString()
            val passw:String = passLogin.getText().toString()
            // validació del correu
            // si no es de tipus correu
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                correoLogin.setError("Invalid Mail")
            }
            else if (passw.length<6) {
                passLogin.setError("Password less than 6 chars")
            }
            else
            {
                // aquí farem LOGIN al jugador
                LogindeJugador(email, passw)

            }
        }

    }
    private fun LogindeJugador(email: String, passw: String) {
        auth.signInWithEmailAndPassword(email, passw)
            .addOnCompleteListener(this)
            { task ->
                if (task.isSuccessful) {
                    val tx: String = "Benvingut "+ email
                    Toast.makeText(this, tx, Toast.LENGTH_LONG).show()
                    val user = auth.currentUser
                    Toast.makeText(this, "NOOOOOOOOOOOOOOO ERRORRR",
                        Toast.LENGTH_LONG).show()
                    updateUI(user)
                    Toast.makeText(this, "ERRORRR",
                        Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "ERROR Autentificació",
                        Toast.LENGTH_LONG).show()
                }
            }
    }
    fun updateUI(user: FirebaseUser?)
    {
        val intent= Intent(this, Menu::class.java)
        startActivity(intent)
        //finish()
    }

}
