package com.example.calcolocf

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.calcolocf.databinding.FragmentNameBinding
import com.example.calcolocf.databinding.FragmentSurnameBinding

class NameFragment : Fragment() {
    private var _binding: FragmentNameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CodiceFiscaleViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNameBinding.inflate(inflater, container, false)

        binding.nomeEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val nome = s.toString()

                if (nome.isBlank()) {
                    binding.nomeEditText.error = "Il nome non può essere vuoto"
                    return
                }

                if (nome.any { it.isDigit() }) {
                    binding.nomeEditText.error = "Il nome non può contenere numeri"
                    return
                }

                viewModel.setNome(nome)
                viewModel.calcoloCodiceFiscale()
            }
        })

        binding.nextButton.setOnClickListener {
           if (binding.nomeEditText.text.isBlank()) {
                binding.nomeEditText.error = "Il nome non può essere vuoto"
                return@setOnClickListener
            }

            if (binding.nomeEditText.text.any { it.isDigit() }) {
                binding.nomeEditText.error = "Il cognome non può contenere numeri"
                return@setOnClickListener
            }
            viewModel.setNome(binding.nomeEditText.text.toString())
            viewModel.calcoloCodiceFiscale()

            Log.d("Name-Viewmodel", viewModel.nome.value.toString())
            findNavController().navigate(R.id.action_nameFragment_to_dateFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}