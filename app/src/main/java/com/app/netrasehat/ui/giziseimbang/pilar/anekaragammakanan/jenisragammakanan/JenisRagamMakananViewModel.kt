package com.app.netrasehat.ui.giziseimbang.pilar.anekaragammakanan.jenisragammakanan

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.netrasehat.model.JenisJenisMakanan
import com.app.netrasehat.utils.DataContainer

class JenisRagamMakananViewModel : ViewModel() {

    fun getJenisMakananPokok(context: Context): List<JenisJenisMakanan> =
        DataContainer.generateJenisMakananPokok(context)

    fun getJenisLaukPauk(context: Context): List<JenisJenisMakanan> =
        DataContainer.generateJenisLaukPauk(context)

    fun getJenisSayuran(context: Context): List<JenisJenisMakanan> =
        DataContainer.generateJenisSayuran(context)

    fun getJenisBuah(context: Context): List<JenisJenisMakanan> =
        DataContainer.generateJenisBuah(context)
}