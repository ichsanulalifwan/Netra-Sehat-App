package com.app.tunanetradaily.giziseimbang.pilar.aktivitasfisik

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.tunanetradaily.R

class AktivitasFisikFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aktivitas_fisik, container, false)
    }

    companion object {
    }
}