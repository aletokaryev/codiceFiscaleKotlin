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
import com.example.calcolocf.dataSets.monthsData
import com.example.calcolocf.databinding.FragmentDateBinding
import com.google.android.material.snackbar.Snackbar

class DateFragment : Fragment() {
    private var _binding: FragmentDateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CodiceFiscaleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDateBinding.inflate(inflater, container, false)

        val monthsList = monthsData.keys.toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, monthsList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.meseSpinner.adapter = adapter

        binding.giornoEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val giorno = s.toString()

                if (giorno.isBlank()) {
                    binding.giornoEditText.error = "Il giorno non può essere vuoto"
                    return
                }

                viewModel.setGiorno(giorno)
                viewModel.calcoloCodiceFiscale()
            }
        })

        binding.meseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val mese = parent?.getItemAtPosition(position).toString()

                if (mese.isBlank()) {
                    binding.meseSpinner.rootView.showSnackbar("Il mese non può essere vuoto")
                    return
                }

                viewModel.setMese(mese)
                viewModel.calcoloCodiceFiscale()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.sessoEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val sesso = s.toString()

                if (sesso.isBlank()) {
                    binding.sessoEditText.error = "Inserisci sesso"
                    return
                }

                viewModel.setGiorno(sesso)
                Log.d("Sesso-Viewmodel", viewModel.sesso.value.toString())
                viewModel.calcoloCodiceFiscale()
            }
        })



        binding.annoEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val anno = s.toString()

                if (anno.isBlank()) {
                    binding.annoEditText.error = "L'anno non può essere vuoto"
                    return
                }

                viewModel.setAnno(anno)
                viewModel.calcoloCodiceFiscale()
            }
        })

        binding.nextButton.setOnClickListener {

            if (binding.giornoEditText.text.isBlank()) {
                binding.giornoEditText.error = "Il giorno non può essere vuoto"
                return@setOnClickListener
            }
            viewModel.setGiorno(binding.giornoEditText.text.toString())
            Log.d("Giorno-Viewmodel", viewModel.giorno.value.toString())

            val selectedMonth = binding.meseSpinner.selectedItem.toString()
            if (selectedMonth.isBlank()) {
                binding.meseSpinner.rootView.showSnackbar("Il mese non può essere vuoto")
                return@setOnClickListener
            }
            viewModel.setMese(selectedMonth)
            Log.d("Mese-Viewmodel", viewModel.mese.value.toString())


            if (binding.annoEditText.text.isBlank()) {
                binding.annoEditText.error = "L'anno non può essere vuoto"
                return@setOnClickListener
            }
            viewModel.setAnno(binding.annoEditText.text.toString())
            Log.d("Anno-Viewmodel", viewModel.anno.value.toString())


            if (binding.sessoEditText.text.isBlank()) {
                binding.sessoEditText.error = "Inserisci sesso"
                return@setOnClickListener
            }

            viewModel.setSesso(binding.sessoEditText.text.toString())
            viewModel.calcoloCodiceFiscale()
            Log.d("Sesso-Viewmodel", viewModel.sesso.value.toString())
            findNavController().navigate(R.id.action_dateFragment_to_countryFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun View.showSnackbar(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
    }

}