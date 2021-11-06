package com.app.netrasehat.ui.giziseimbang.pesan

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.netrasehat.model.Pesan
import com.app.netrasehat.utils.DataContainer

class PesanGiziSeimbangViewModel : ViewModel() {

    fun getPesan(context: Context): List<Pesan> = DataContainer.generatePesan(context)
}