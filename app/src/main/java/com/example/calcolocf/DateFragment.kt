package com.example.calcolocf

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.calcolocf.dataSets.comuniData
import com.example.calcolocf.dataSets.monthsData
import com.example.calcolocf.databinding.FragmentDateBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateFragment : Fragment() {
    private var _binding: FragmentDateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CodiceFiscaleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDateBinding.inflate(inflater, container, false)

        val listGeneri = arrayOf("M", "F", "Altro")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listGeneri)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sessoSpinner.adapter = adapter

        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val today = Calendar.getInstance()

        binding.datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)) { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, monthOfYear)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }

            val formattedDate = dateFormatter.format(selectedDate.time)
            val splitDate = formattedDate.split("/")
            val day = splitDate[0]
            val month = splitDate[1]
            val year = splitDate[2]

            viewModel.setGiorno(day)
            viewModel.setMese(month)
            viewModel.setAnno(year)
            Log.d("Giorno-Viewmodel", viewModel.giorno.value.toString())
            Log.d("Mese-Viewmodel", viewModel.mese.value.toString())
            Log.d("Anno-Viewmodel", viewModel.anno.value.toString())
            viewModel.calcoloCodiceFiscale()
        }

        binding.sessoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val sesso = parent?.getItemAtPosition(position).toString()

                if (sesso.isBlank()) {
                    binding.sessoSpinner.rootView.showSnackbar("Scegli un sesso valido")
                    return
                }

                viewModel.setSesso(sesso)
                viewModel.calcoloCodiceFiscale()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        binding.nextButton.setOnClickListener {
            val sessoSelezionato = binding.sessoSpinner.selectedItem.toString()
            if (sessoSelezionato.isBlank()) {
                binding.sessoSpinner.rootView.showSnackbar("Scegli un sesso valido")
                return@setOnClickListener
            }
            viewModel.setSesso(sessoSelezionato)
            viewModel.calcoloCodiceFiscale()

            findNavController().navigate(R.id.action_dateFragment_to_countryFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun View.showSnackbar(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
    }
}