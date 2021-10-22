package com.app.netrasehat.giziseimbang.pilar.anekaragammakanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.netrasehat.R

class JenisRagamMakananFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jenis_ragam_makanan, container, false)
    }

    companion object {
    }
}