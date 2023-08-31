package com.example.calcolocf

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.calcolocf.databinding.FragmentSurnameBinding

class SurnameFragment : Fragment() {
    private var _binding: FragmentSurnameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CodiceFiscaleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurnameBinding.inflate(inflater, container, false)

        binding.cognomeEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val cognome = s.toString()

                if (cognome.isBlank()) {
                    binding.cognomeEditText.error = "Il cognome non può essere vuoto"
                    return
                }

                if (cognome.any { it.isDigit() }) {
                    binding.cognomeEditText.error = "Il cognome non può contenere numeri"
                    return
                }

                viewModel.setCognome(cognome)
                viewModel.calcoloCodiceFiscale()
            }
        })
        

        binding.nextButton.setOnClickListener {
            val cognome = binding.cognomeEditText.text.toString()
            if (cognome.isBlank()) {
                binding.cognomeEditText.error = "Il cognome non può essere vuoto"
                return@setOnClickListener
            }

            if (cognome.any { it.isDigit() }) {
                binding.cognomeEditText.error = "Il cognome non può contenere numeri"
                return@setOnClickListener
            }

            Log.d("Surname-Viewmodel", viewModel.cognome.value.toString())
            findNavController().navigate(R.id.action_surnameFragment_to_nameFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}