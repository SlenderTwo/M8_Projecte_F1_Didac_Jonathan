package com.example.m8_projecte_f1_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import java.util.Timer
import java.util.TimerTask

class credits : AppCompatActivity() {

    var timer= Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)
        timer.scheduleAtFixedRate(TimeTask(),0L,3000L)
    }
    private inner class TimeTask : TimerTask() {
        private var currentFragmentIndex = 0

        override fun run() {
            currentFragmentIndex = (currentFragmentIndex + 1) % 5 // Se ajusta para manejar cuatro fragmentos

            runOnUiThread {
                when (currentFragmentIndex) {
                    0 -> {
                        supportFragmentManager.commit {
                            replace(R.id.container, BlankFragment())
                            setReorderingAllowed(true)
                        }
                    }
                    1 -> {
                        supportFragmentManager.commit {
                            replace(R.id.container, BlankFragment2())
                            setReorderingAllowed(true)
                        }
                    }
                    2 -> {
                        supportFragmentManager.commit {
                            replace(R.id.container, BlankFragment3())
                            setReorderingAllowed(true)
                        }
                    }
                    3 -> {
                        supportFragmentManager.commit {
                            replace(R.id.container, BlankFragment4())
                            setReorderingAllowed(true)
                        }
                    }
                    4 -> {
                        supportFragmentManager.commit {
                            replace(R.id.container, BlankFragment5())
                            setReorderingAllowed(true)
                        }
                    }
                }
            }
        }
    }

}