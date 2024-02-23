package com.example.m8_projecte_f1_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer
import java.util.Timer
import kotlin.concurrent.schedule

class Splash : AppCompatActivity() {
    private val duracio: Long=10000
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //amaguem la barra, pantalla a full
        supportActionBar?.hide()

        // Inicializar el MediaPlayer con el archivo de música
        mediaPlayer = MediaPlayer.create(this, R.raw.f1)

        // Reproducir la música
        mediaPlayer.start()

        //cridem a la funció de canviar activitat
        canviarActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Liberar recursos del MediaPlayer
        mediaPlayer.release()
    }

    private fun canviarActivity(){
        Timer().schedule(duracio){
            saltainici()
        }
    }

    private fun saltainici()
    {
        val intent=Intent(this, MainActivity::class.java)
        startActivity(intent)
        // Detener la música cuando iniciamos la próxima actividad
        mediaPlayer.stop()
        // Liberar recursos del MediaPlayer
        mediaPlayer.release()
        finish()
    }
}
