package com.app.netrasehat.giziseimbang.pilar.phbs

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.netrasehat.model.Phbs
import com.app.netrasehat.utils.DataContainer
import kotlin.properties.Delegates

class DetailPhbsViewModel : ViewModel() {

    private var phbsId by Delegates.notNull<Int>()

    fun setPhbs(phbsId: Int) {
        this.phbsId = phbsId
    }

    fun getDetailPhbs(context: Context): Phbs {
        lateinit var phbsEntity: Phbs
        val phbsEntities = DataContainer.generatePhbs(context)
        for (phbs in phbsEntities) {
            if (phbs.id == phbsId) {
                phbsEntity = phbs
            }
        }
        return phbsEntity
    }
}