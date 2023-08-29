package com.example.calcolocf

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.calcolocf.databinding.FragmentLastBinding
import com.example.calcolocf.databinding.FragmentSurnameBinding

class LastFragment : Fragment() {
    private var _binding: FragmentLastBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CodiceFiscaleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLastBinding.inflate(inflater, container, false)


        binding.reset.setOnClickListener {
            viewModel.reset()
            findNavController().navigate(R.id.action_lastFragment_to_surnameFragment)
        }

        return binding.root


    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.codFinaleTextView.text = viewModel.codiceFiscale.value.toString()
        binding.nomeTextView.text = viewModel.nome.value.toString()
        binding.cognomeTextView.text = viewModel.cognome.value.toString()
        binding.comuneTextView.text = viewModel.comune.value.toString()
        binding.sessoTextView.text = viewModel.sesso.value.toString()
        binding.dataTextView.text = "${viewModel.giorno.value.toString()} ${viewModel.mese.value.toString()} ${viewModel.anno.value.toString()}"
    }
}