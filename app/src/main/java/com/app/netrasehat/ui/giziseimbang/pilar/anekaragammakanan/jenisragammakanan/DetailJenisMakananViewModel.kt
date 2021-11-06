package com.app.netrasehat.ui.giziseimbang.pilar.anekaragammakanan.jenisragammakanan

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.netrasehat.model.JenisJenisMakanan
import com.app.netrasehat.utils.DataContainer
import kotlin.properties.Delegates

class DetailJenisMakananViewModel : ViewModel() {

    private var jenisId by Delegates.notNull<Int>()

    fun setJenisMakanan(jenisId: Int) {
        this.jenisId = jenisId
    }

    fun getDetailJenisMakananPokok(context: Context): JenisJenisMakanan {
        lateinit var jenisMakananEntity: JenisJenisMakanan
        val jenisMakananEntities = DataContainer.generateJenisMakananPokok(context)
        for (jenisMakanan in jenisMakananEntities) {
            if (jenisMakanan.id == jenisId) {
                jenisMakananEntity = jenisMakanan
            }
        }
        return jenisMakananEntity
    }

    fun getDetailJenisLaukPauk(context: Context): JenisJenisMakanan {
        lateinit var jenisMakananEntity: JenisJenisMakanan
        val jenisMakananEntities = DataContainer.generateJenisLaukPauk(context)
        for (jenisMakanan in jenisMakananEntities) {
            if (jenisMakanan.id == jenisId) {
                jenisMakananEntity = jenisMakanan
            }
        }
        return jenisMakananEntity
    }

    fun getDetailJenisSayuran(context: Context): JenisJenisMakanan {
        lateinit var jenisMakananEntity: JenisJenisMakanan
        val jenisMakananEntities = DataContainer.generateJenisSayuran(context)
        for (jenisMakanan in jenisMakananEntities) {
            if (jenisMakanan.id == jenisId) {
                jenisMakananEntity = jenisMakanan
            }
        }
        return jenisMakananEntity
    }

    fun getDetailJenisBuah(context: Context): JenisJenisMakanan {
        lateinit var jenisMakananEntity: JenisJenisMakanan
        val jenisMakananEntities = DataContainer.generateJenisBuah(context)
        for (jenisMakanan in jenisMakananEntities) {
            if (jenisMakanan.id == jenisId) {
                jenisMakananEntity = jenisMakanan
            }
        }
        return jenisMakananEntity
    }
}
