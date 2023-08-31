package com.example.calcolocf

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class ViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testSetCognome(){
        val viewModel = CodiceFiscaleViewModel()
        viewModel.cognome.observeForever {}
        viewModel.setCognome("Rossi")
        assertEquals("Rossi", viewModel.cognome.value.toString())
    }

    @Test
    fun testSetNome(){
        val viewModel = CodiceFiscaleViewModel()
        viewModel.nome.observeForever {}
        viewModel.setNome("Mario")
        assertEquals("Mario", viewModel.nome.value.toString())
    }

    @Test
    fun testSetDate(){
        val viewModel = CodiceFiscaleViewModel()
        viewModel.giorno.observeForever {}
        viewModel.mese.observeForever {}
        viewModel.anno.observeForever {}
        viewModel.setGiorno("15")
        viewModel.setMese("02")
        viewModel.setAnno("1990")
        assertEquals("15", viewModel.giorno.value.toString())
        assertEquals("02", viewModel.mese.value.toString())
        assertEquals("1990", viewModel.anno.value.toString())
    }

    @Test
    fun testSetSesso(){
        val viewModel = CodiceFiscaleViewModel()
        viewModel.sesso.observeForever {}
        viewModel.setSesso("M")
        assertEquals("M", viewModel.sesso.value.toString())
    }

    @Test
    fun testSetCountry(){
        val viewModel = CodiceFiscaleViewModel()
        viewModel.comune.observeForever {}
        viewModel.setComune("Napoli")
        assertEquals("Napoli", viewModel.comune.value.toString())
    }

    @Test
    fun testCalcoloCodiceFiscale() {
        val viewModel = CodiceFiscaleViewModel()

        viewModel.cognome.observeForever {}
        viewModel.nome.observeForever {}
        viewModel.anno.observeForever {}
        viewModel.mese.observeForever {}
        viewModel.giorno.observeForever {}
        viewModel.sesso.observeForever {}
        viewModel.comune.observeForever {}


        viewModel.setCognome("Cioffi")
        viewModel.setNome("Vincenzo")
        viewModel.setAnno("1994")
        viewModel.setMese("09")
        viewModel.setGiorno("12")
        viewModel.setSesso("M")
        viewModel.setComune("Napoli")

        viewModel.calcoloCodiceFiscale()

        assertEquals("CFFVCN94P12F839O", viewModel.codiceFiscale.value)
    }

    @Test
    fun testReset() {
        val viewModel = CodiceFiscaleViewModel()

        viewModel.cognome.observeForever {}
        viewModel.nome.observeForever {}
        viewModel.anno.observeForever {}
        viewModel.mese.observeForever {}
        viewModel.giorno.observeForever {}
        viewModel.sesso.observeForever {}
        viewModel.comune.observeForever {}
        viewModel.codiceFiscale.observeForever {}

        viewModel.setCognome("Cioffi")
        viewModel.setNome("Vincenzo")
        viewModel.setAnno("1994")
        viewModel.setMese("09")
        viewModel.setGiorno("12")
        viewModel.setSesso("M")
        viewModel.setComune("Napoli")
        viewModel.calcoloCodiceFiscale()

        viewModel.reset()

        assertEquals("", viewModel.cognome.value)
        assertEquals("",viewModel.nome.value)
        assertEquals("",viewModel.giorno.value)
        assertEquals("",viewModel.mese.value)
        assertEquals("",viewModel.anno.value)
        assertEquals("",viewModel.comune.value)
        assertEquals("",viewModel.sesso.value)
        assertEquals("",viewModel.codiceFiscale.value)
    }


}