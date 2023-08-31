package com.example.calcolocf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.calcolocf.databinding.FragmentCodFiscaleLiveBinding
import com.example.calcolocf.databinding.FragmentLastBinding

class CodFiscaleLive : Fragment() {

    private var _binding: FragmentCodFiscaleLiveBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CodiceFiscaleViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCodFiscaleLiveBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

//        viewModel.codiceFiscale.observe(viewLifecycleOwner, Observer { nuovoCodiceFiscale ->
//            binding.codFiscaleLive.text = nuovoCodiceFiscale
//        })

        return binding.root


    }

}