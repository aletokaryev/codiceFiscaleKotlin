package com.example.calcolocf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calcolocf.dataSets.comuniData
import com.example.calcolocf.dataSets.monthsData

class CodiceFiscaleViewModel: ViewModel() {
    private val _cognome = MutableLiveData<String>()
    val cognome: LiveData<String> = _cognome
    fun setCognome(surname: String){
        _cognome.value = surname
    }

    private val _nome = MutableLiveData<String>()
    val nome: LiveData<String> = _nome
    fun setNome(name: String){
        _nome.value = name
    }

    private val _giorno = MutableLiveData<String>()
    val giorno: LiveData<String> = _giorno
    fun setGiorno(day: String){
        _giorno.value = day
    }

    private val _mese = MutableLiveData<String>()
    val mese: LiveData<String> = _mese
    fun setMese(month: String){
        _mese.value = month
    }

    private val _anno = MutableLiveData<String>()
    val anno: LiveData<String> = _anno
    fun setAnno(year: String){
        _anno.value = year
    }

    private val _comune = MutableLiveData<String>()
    val comune: LiveData<String> = _comune
    fun setComune(country: String){
        _comune.value = country
    }

    private val _sesso = MutableLiveData<String>()
    val sesso: LiveData<String> = _sesso
    fun setSesso(gender: String){
        _sesso.value = gender
    }

    private val _codiceFiscale = MutableLiveData<String>()
    val codiceFiscale: LiveData<String>
        get() = _codiceFiscale

    init {
        _codiceFiscale.value = ""
    }

    fun calcoloCodiceFiscale(){

        val parteCognome = calcoloCognome(_cognome.value.toString())
        val parteNome = calcoloNome(_nome.value.toString())

        val codFiscaleIncompleto = (
                parteCognome + parteNome + _anno.value.toString().takeLast(2) + checkMese(_mese.value.toString()) +
                        checkGiorno(_giorno.value.toString(), _sesso.value.toString()) + checkComune(_comune.value.toString())).uppercase()


        val codFiscaleCompleto = if (codFiscaleIncompleto.length >= 15){
            val cin = calcolaCIN(codFiscaleIncompleto)
            codFiscaleIncompleto + cin
        } else {
            codFiscaleIncompleto
        }

        _codiceFiscale.value = codFiscaleCompleto
    }

    fun calcoloCognome(cognome: String): String {
        val consonants = getFilteredConsonants(cognome)
        return when {
            consonants.length >= 3 -> consonants.take(3)
            consonants.length == 2 -> consonants + "X"
            consonants.length == 1 -> consonants + "XX"
            else -> "XXX"
        }
    }

    private fun calcoloNome(nome: String): String {
        val consonants = getFilteredConsonants(nome)
        return when {
            consonants.length >= 4 -> "${consonants[0]}${consonants[2]}${consonants[3]}"
            consonants.length == 3 -> consonants.take(3)
            consonants.length == 2 -> consonants + "X"
            consonants.length == 1 -> consonants + "XX"
            else -> "XXX"
        }
    }

    private fun checkMese(mese: String): String {
        return monthsData[mese] ?: "X"
    }

    private fun checkComune(comune: String): String {
        return comuniData[comune] ?: "Z000"
    }

    private fun checkGiorno(giorno: String?, sesso: String): String {
        val giornoValue = giorno?.toIntOrNull() ?: 1
        val giornoAdjusted = if (sesso == "F") giornoValue + 40 else giornoValue
        return giornoAdjusted.toString().padStart(2, '0')
    }

    private fun getFilteredConsonants(s: String): String {
        val filteredConsonants = s.filter { it.isLetter() && it !in "AEIOUaeiou" }
        return filteredConsonants
    }



    fun reset(){
        _giorno.value = ""
        _codiceFiscale.value = ""
        _anno.value = ""
        _comune.value = ""
        _mese.value = ""
        _nome.value = ""
        _cognome.value = ""
        _sesso.value = ""
    }


    //Da modificare in un file esterno; calendar da aggiungere
    private fun calcolaCIN(codiceFiscale: String): Char {
        val caratteriDispari = hashMapOf(
            '0' to 1, '1' to 0, '2' to 5, '3' to 7, '4' to 9, '5' to 13,
            '6' to 15, '7' to 17, '8' to 19, '9' to 21, 'A' to 1, 'B' to 0,
            'C' to 5, 'D' to 7, 'E' to 9, 'F' to 13, 'G' to 15, 'H' to 17,
            'I' to 19, 'J' to 21, 'K' to 2, 'L' to 4, 'M' to 18, 'N' to 20,
            'O' to 11, 'P' to 3, 'Q' to 6, 'R' to 8, 'S' to 12, 'T' to 14,
            'U' to 16, 'V' to 10, 'W' to 22, 'X' to 25, 'Y' to 24, 'Z' to 23
        )
        val caratteriPari = hashMapOf(
            '0' to 0, '1' to 1, '2' to 2, '3' to 3, '4' to 4, '5' to 5,
            '6' to 6, '7' to 7, '8' to 8, '9' to 9, 'A' to 0, 'B' to 1,
            'C' to 2, 'D' to 3, 'E' to 4, 'F' to 5, 'G' to 6, 'H' to 7,
            'I' to 8, 'J' to 9, 'K' to 10, 'L' to 11, 'M' to 12, 'N' to 13,
            'O' to 14, 'P' to 15, 'Q' to 16, 'R' to 17, 'S' to 18, 'T' to 19,
            'U' to 20, 'V' to 21, 'W' to 22, 'X' to 23, 'Y' to 24, 'Z' to 25
        )
        var valDisp = 0
        var valPari = 0

        codiceFiscale.forEachIndexed { index, char ->
            if ((index + 1) % 2 == 0) {
                valPari += caratteriPari[char]!!
            } else {
                valDisp += caratteriDispari[char]!!
            }
        }

        return (64 + ((valDisp + valPari) % 26) + 1).toChar()
    }
}