package com.app.netrasehat.contactsahabatnetra

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.netrasehat.databinding.FragmentContactSahabatNetraBinding
import com.app.netrasehat.databinding.FragmentCovid19Binding

class ContactSahabatNetraFragment : Fragment() {

    private var _binding: FragmentContactSahabatNetraBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactSahabatNetraBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private const val TAG = "ContactSahabatNetra"
    }
}