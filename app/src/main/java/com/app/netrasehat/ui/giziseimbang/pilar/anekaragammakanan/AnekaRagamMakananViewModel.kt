package com.app.netrasehat.ui.giziseimbang.pilar.anekaragammakanan

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.netrasehat.model.RagamMakanan
import com.app.netrasehat.utils.DataContainer

class AnekaRagamMakananViewModel : ViewModel() {

    fun getData(context: Context): List<RagamMakanan> = DataContainer.generateRagamMakanan(context)
}