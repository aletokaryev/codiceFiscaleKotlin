package com.example.calcolocf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CodiceFiscaleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val codFisLive: TextView = findViewById(R.id.codFisLive)

        viewModel = ViewModelProvider(this).get(CodiceFiscaleViewModel::class.java)

        viewModel.codiceFiscale.observe(this) { codiceFiscale ->
            codFisLive.text = codiceFiscale
        }
    }
}
