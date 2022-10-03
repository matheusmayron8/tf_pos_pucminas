package br.com.matheus.lima.trabalhofinalpospuc.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.matheus.lima.trabalhofinalpospuc.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }
}
