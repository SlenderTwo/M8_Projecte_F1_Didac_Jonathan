package com.example.m8_projecte_f1_2

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }
}

class Quiz {
    val niveles: MutableList<MutableList<Pregunta>> = mutableListOf()

    init {
        inicializarPreguntas()
    }

    private fun inicializarPreguntas() {
        // Puedes reemplazar estas preguntas y opciones con las tuyas
        val nivel1 = mutableListOf<Pregunta>()
        nivel1.add(Pregunta("¿Quien es conocido como el kaiser en el mundo de la formula 1?", arrayOf("Schumacher", "Alonso", "Vettel", "Hamilton"), 0))
        nivel1.add(Pregunta("¿Quién ganó el Campeonato Mundial de Pilotos de la Fórmula 1 en 2020?", arrayOf("Max Verstappen", "Lewis Hamilton", "Valtteri Bottas", "Sebastian Vettel"), 1))
        nivel1.add(Pregunta("¿Cuál es el circuito más antiguo que sigue en uso en la Fórmula 1?", arrayOf("Circuit de Monaco", "Silverstone Circuit", "Circuit de Spa-Francorchamps", "Monza Circuit"), 1))
        nivel1.add(Pregunta("¿Qué piloto tiene el récord de la vuelta más rápida en la historia de la Fórmula 1?", arrayOf("Ayrton Senna", "Michael Schumacher", "Lewis Hamilton", "Juan Manuel Fangio"), 2))
        niveles.add(nivel1)

        val nivel2 = mutableListOf<Pregunta>()
        nivel2.add(Pregunta("¿Qué equipo de Fórmula 1 tiene su sede en Woking, Inglaterra?", arrayOf("Mercedes", "Red Bull Racing", "McLaren", "Ferrari"), 2))
        nivel2.add(Pregunta("¿Qué piloto tiene el récord de la mayor cantidad de victorias en Grandes Premios?", arrayOf("Michael Schumacher", "Lewis Hamilton", "Ayrton Senna", "Alain Prost"), 1))
        nivel2.add(Pregunta("¿Cuál es el equipo de Fórmula 1 más exitoso en términos de Campeonatos de Constructores ganados?", arrayOf("McLaren", "Mercedes", "Red Bull Racing", "Ferrari"), 3))
        nivel2.add(Pregunta("¿Quién fue el piloto más joven en ganar un Gran Premio de Fórmula 1?", arrayOf("Fernando Alonso", "Lewis Hamilton", "Max Verstappen", "Sebastian Vettel"), 2))
        niveles.add(nivel2)

        val nivel3 = mutableListOf<Pregunta>()
        nivel3.add(Pregunta("¿Quién fue el primer piloto en ganar un Campeonato Mundial de Conductores de Fórmula 1 con un motor turboalimentado?", arrayOf("Alain Prost", "Nelson Piquet", "Niki Lauda", "Keke Rosberg"), 3))
        nivel3.add(Pregunta("¿Qué piloto ganó el Campeonato Mundial de Conductores de Fórmula 1 en 1976 después de un grave accidente en el que sufrió quemaduras severas?", arrayOf("James Hunt", "Niki Lauda", "Jody Scheckter", "Mario Andretti"), 1))
        nivel3.add(Pregunta("¿Cuál es el único equipo en la historia de la Fórmula 1 que ha logrado un podio en su única carrera?", arrayOf("Brawn GP", "Hesketh Racing", "Pacific Grand Prix", "Porsche"), 0))
        nivel3.add(Pregunta("¿Qué piloto de Fórmula 1 tiene el récord de la mayor cantidad de carreras antes de su primera victoria?", arrayOf("Rubens Barrichello", "Jenson Button", "Nico Rosberg", "Mark Webber"), 0))
        niveles.add(nivel3)

    }

    fun getPregunta(nivel: Int, index: Int): Pregunta {
        return niveles[nivel][index]
    }

    fun getNumeroDePreguntas(nivel: Int): Int {
        return niveles[nivel].size
    }

    data class Pregunta(val pregunta: String, val opciones: Array<String>, val respuestaCorrecta: Int)
}


class QuizActivity : AppCompatActivity() {
    private lateinit var quiz: Quiz
    private lateinit var submit_button: Button
    private lateinit var level_text_view: TextView
    private lateinit var question_text_view: TextView
    private lateinit var option_1: RadioButton
    private lateinit var option_2: RadioButton
    private lateinit var option_3: RadioButton
    private lateinit var option_4: RadioButton
    private lateinit var score_text_view: TextView  // Agregamos un TextView para mostrar la puntuación
    private var nivelActual = 0
    private var preguntaActual = 0
    private var puntuacion = 0

    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Obtener el nivel seleccionado
        nivelActual = intent.getIntExtra("NIVEL", 0)

        sharedPreferences = getSharedPreferences("Puntuacion", Context.MODE_PRIVATE)
        puntuacion = sharedPreferences.getInt("puntuacion", 0)

        // Inicializar las vistas
        submit_button = findViewById(R.id.submit_button)
        level_text_view = findViewById(R.id.level_text_view)
        question_text_view = findViewById(R.id.question_text_view)
        option_1 = findViewById(R.id.option_1)
        option_2 = findViewById(R.id.option_2)
        option_3 = findViewById(R.id.option_3)
        option_4 = findViewById(R.id.option_4)
        score_text_view = findViewById(R.id.score_text_view)  // Inicializamos el TextView de la puntuación


        level_text_view.text = "Nivel: ${nivelActual + 1}"
        score_text_view.text = "Puntuación: $puntuacion"


        quiz = Quiz()

        mostrarPregunta()

        submit_button.setOnClickListener {
            if (respuestaCorrecta()) {
                // Asignamos puntos según el nivel de la pregunta
                puntuacion += nivelActual + 1
                // Mostramos la puntuación actualizada
                score_text_view.text = "Puntuación: $puntuacion"

                preguntaActual++
                if (preguntaActual < quiz.getNumeroDePreguntas(nivelActual)) {
                    mostrarPregunta()
                } else {
                    Toast.makeText(this, "¡Has terminado el nivel!", Toast.LENGTH_SHORT).show()
                    guardarPuntuacion()
                    finish() // Finaliza la actividad y vuelve a LevelSelectionActivity
                }
            } else {
                puntuacion -= nivelActual + 1
                Toast.makeText(this, "Lo siento, esa respuesta es incorrecta. Intenta de nuevo.", Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun mostrarPregunta() {
        val pregunta = quiz.getPregunta(nivelActual, preguntaActual)
        question_text_view.text = pregunta.pregunta
        option_1.text = pregunta.opciones[0]
        option_2.text = pregunta.opciones[1]
        option_3.text = pregunta.opciones[2]
        option_4.text = pregunta.opciones[3]
    }

    private fun respuestaCorrecta(): Boolean {
        val respuestaSeleccionada = when {
            option_1.isChecked -> 0
            option_2.isChecked -> 1
            option_3.isChecked -> 2
            option_4.isChecked -> 3
            else -> -1
        }
        return respuestaSeleccionada == quiz.getPregunta(nivelActual, preguntaActual).respuestaCorrecta
    }
    private fun guardarPuntuacion() {
        val editor = sharedPreferences.edit()
        editor.putInt("puntuacion", puntuacion)
        editor.apply()
    }
}
