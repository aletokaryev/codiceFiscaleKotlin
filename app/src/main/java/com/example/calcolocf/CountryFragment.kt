package com.example.calcolocf

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.calcolocf.dataSets.comuniData
import com.example.calcolocf.databinding.FragmentCountryBinding
import com.google.android.material.snackbar.Snackbar

class CountryFragment : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CodiceFiscaleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)

        val listaComuni = comuniData.keys.toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listaComuni)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.comuniSpinner.adapter = adapter

        binding.nextButton.setOnClickListener {
            val selectedCoutry = binding.comuniSpinner.selectedItem.toString()
            if (selectedCoutry.isBlank()) {
                binding.comuniSpinner.rootView.showSnackbar("Il mese non può essere vuoto")
                return@setOnClickListener
            }
            viewModel.setComune(selectedCoutry)
            viewModel.calcoloCodiceFiscale()
            Log.d("Comune-Viewmodel", viewModel.comune.value.toString())
            findNavController().navigate(R.id.action_countryFragment_to_lastFragment)
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