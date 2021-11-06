package com.app.netrasehat.ui.giziseimbang.pesan

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.netrasehat.model.Pesan
import com.app.netrasehat.utils.DataContainer
import kotlin.properties.Delegates

class DetailPesanGiziViewModel : ViewModel() {

    private var pesanId by Delegates.notNull<Int>()

    fun setPesan(pesanId: Int) {
        this.pesanId = pesanId
    }

    fun getDetailPesan(context: Context): Pesan {
        lateinit var pesanEntity: Pesan
        val pesanEntities = DataContainer.generatePesan(context)
        for (pesan in pesanEntities) {
            if (pesan.id == pesanId) {
                pesanEntity = pesan
            }
        }
        return pesanEntity
    }
}