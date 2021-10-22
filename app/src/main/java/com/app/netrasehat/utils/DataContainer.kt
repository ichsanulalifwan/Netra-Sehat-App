package com.app.netrasehat.utils

import android.content.Context
import com.app.netrasehat.R
import com.app.netrasehat.model.Pesan

object DataContainer {

    fun generatePesan(context: Context): List<Pesan> {

        val pesan = ArrayList<Pesan>()

        pesan.add(
            Pesan(
                1,
                context.getString(R.string.title_pesan_giziseimbang_1),
                context.getString(R.string.pesan_giziseimbang_1)
            )
        )

        pesan.add(
            Pesan(
                2,
                context.getString(R.string.title_pesan_giziseimbang_2),
                context.getString(R.string.pesan_giziseimbang_2)
            )
        )

        pesan.add(
            Pesan(
                3,
                context.getString(R.string.title_pesan_giziseimbang_3),
                context.getString(R.string.pesan_giziseimbang_3)
            )
        )

        pesan.add(
            Pesan(
                4,
                context.getString(R.string.title_pesan_giziseimbang_4),
                context.getString(R.string.pesan_giziseimbang_4)
            )
        )

        pesan.add(
            Pesan(
                5,
                context.getString(R.string.title_pesan_giziseimbang_5),
                context.getString(R.string.pesan_giziseimbang_5)
            )
        )

        pesan.add(
            Pesan(
                6,
                context.getString(R.string.title_pesan_giziseimbang_6),
                context.getString(R.string.pesan_giziseimbang_6)
            )
        )

        pesan.add(
            Pesan(
                7,
                context.getString(R.string.title_pesan_giziseimbang_7),
                context.getString(R.string.pesan_giziseimbang_7)
            )
        )

        pesan.add(
            Pesan(
                8,
                context.getString(R.string.title_pesan_giziseimbang_8),
                context.getString(R.string.pesan_giziseimbang_8)
            )
        )

        pesan.add(
            Pesan(
                9,
                context.getString(R.string.title_pesan_giziseimbang_9),
                context.getString(R.string.pesan_giziseimbang_9)
            )
        )

        pesan.add(
            Pesan(
                10,
                context.getString(R.string.title_pesan_giziseimbang_10),
                context.getString(R.string.pesan_giziseimbang_10)
            )
        )

        return pesan
    }
}