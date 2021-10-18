package com.app.tunanetradaily.giziseimbang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.tunanetradaily.databinding.FragmentPesanGiziSeimbangBinding

class PesanGiziSeimbangFragment : Fragment() {

    private var _binding: FragmentPesanGiziSeimbangBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPesanGiziSeimbangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        textToSpeechEngine?.shutdown()
//        speechRecognizer.destroy()
        _binding = null
    }

    companion object {
        private const val TAG = "PesanGiziSeimbangFragment"
    }
}