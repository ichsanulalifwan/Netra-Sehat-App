package com.app.netrasehat.giziseimbang.pilar.anekaragammakanan

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.netrasehat.model.RagamMakanan
import com.app.netrasehat.utils.DataContainer
import kotlin.properties.Delegates

class DetailRagamMakananViewModel : ViewModel() {

    private var makananId by Delegates.notNull<Int>()

    fun setRagamMakanan(makananId: Int) {
        this.makananId = makananId
    }

    fun getDetailRagamMakanan(context: Context): RagamMakanan {
        lateinit var makananEntity: RagamMakanan
        val makananEntities = DataContainer.generateRagamMakanan(context)
        for (makanan in makananEntities) {
            if (makanan.id == makananId) {
                makananEntity = makanan
            }
        }
        return makananEntity
    }
}