package com.app.netrasehat.giziseimbang.pilar.aktivitasfisik

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.netrasehat.model.AktivitasFisik
import com.app.netrasehat.utils.DataContainer
import kotlin.properties.Delegates

class JenisAktivitasFisikViewModel : ViewModel() {

    private var aktivitasId by Delegates.notNull<Int>()

    fun setJenisAktivitas(aktivitasId: Int) {
        this.aktivitasId = aktivitasId
    }

    fun getDetailAktivitas(context: Context): AktivitasFisik {
        lateinit var aktivitasFisikEntity: AktivitasFisik
        val aktivitasFisikEntities = DataContainer.generateJenisAktivitasFisik(context)
        for (aktivitasFisik in aktivitasFisikEntities) {
            if (aktivitasFisik.id == aktivitasId) {
                aktivitasFisikEntity = aktivitasFisik
            }
        }
        return aktivitasFisikEntity
    }
}