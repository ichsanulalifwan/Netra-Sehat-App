package com.app.netrasehat.giziseimbang.pilar.phbs

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.netrasehat.model.Phbs
import com.app.netrasehat.utils.DataContainer

class PhbsViewModel : ViewModel() {

    fun getPhbsData(context: Context): List<Phbs> = DataContainer.generatePhbs(context)
}