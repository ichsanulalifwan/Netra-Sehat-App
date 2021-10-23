package com.app.netrasehat.utils

import android.content.Context
import com.app.netrasehat.R
import com.app.netrasehat.model.Pesan
import com.app.netrasehat.model.Phbs
import com.app.netrasehat.model.RagamMakanan

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

    fun generatePhbs(context: Context): List<Phbs> {

        val phbs = ArrayList<Phbs>()

        phbs.add(
            Phbs(
                1,
                context.getString(R.string.title_phbs_1),
                context.getString(R.string.pengertian_phbs_1),
                context.getString(R.string.fungsi_phbs_1),
                context.getString(R.string.caramerawat_phbs_1),
                11
            )
        )

        phbs.add(
            Phbs(
                2,
                context.getString(R.string.title_phbs_2),
                context.getString(R.string.pengertian_phbs_2),
                context.getString(R.string.fungsi_phbs_2),
                context.getString(R.string.caramerawat_phbs_2),
                11
            )
        )

        phbs.add(
            Phbs(
                3,
                context.getString(R.string.title_phbs_3),
                context.getString(R.string.pengertian_phbs_3),
                context.getString(R.string.fungsi_phbs_3),
                context.getString(R.string.caramerawat_phbs_3),
                11
            )
        )

        phbs.add(
            Phbs(
                4,
                context.getString(R.string.title_phbs_4),
                context.getString(R.string.pengertian_phbs_4),
                context.getString(R.string.fungsi_phbs_4),
                context.getString(R.string.caramerawat_phbs_4),
                11
            )
        )

        phbs.add(
            Phbs(
                5,
                context.getString(R.string.title_phbs_5),
                context.getString(R.string.pengertian_phbs_5),
                context.getString(R.string.fungsi_phbs_5),
                context.getString(R.string.caramerawat_phbs_5),
                11
            )
        )

        phbs.add(
            Phbs(
                6,
                context.getString(R.string.title_phbs_6),
                context.getString(R.string.pengertian_phbs_6),
                context.getString(R.string.fungsi_phbs_6),
                context.getString(R.string.caramerawat_phbs_6),
                11
            )
        )

        phbs.add(
            Phbs(
                7,
                context.getString(R.string.title_phbs_7),
                context.getString(R.string.pengertian_phbs_7),
                context.getString(R.string.fungsi_phbs_7),
                context.getString(R.string.caramerawat_phbs_7),
                11
            )
        )

        phbs.add(
            Phbs(
                8,
                context.getString(R.string.title_phbs_8),
                context.getString(R.string.pengertian_phbs_8),
                context.getString(R.string.fungsi_phbs_8),
                context.getString(R.string.caramerawat_phbs_8),
                11
            )
        )

        return phbs
    }

    fun generateRagamMakanan(context: Context): List<RagamMakanan> {

        val data = ArrayList<RagamMakanan>()

        data.add(
            RagamMakanan(
                1,
                context.getString(R.string.title_ragam_makanan_1),
                context.getString(R.string.pengertian_ragam_makanan_1),
                context.getString(R.string.manfaat_ragam_makanan_1),
                context.getString(R.string.porsi_ragam_makanan_1)
            )
        )

        data.add(
            RagamMakanan(
                2,
                context.getString(R.string.title_ragam_makanan_2),
                context.getString(R.string.pengertian_ragam_makanan_2),
                context.getString(R.string.manfaat_ragam_makanan_2),
                context.getString(R.string.porsi_ragam_makanan_2),
                context.getString(R.string.kandungan_ragam_makanan_2),
                context.getString(R.string.masalah_ragam_makanan_2)
            )
        )

        data.add(
            RagamMakanan(
                3,
                context.getString(R.string.title_ragam_makanan_3),
                context.getString(R.string.pengertian_ragam_makanan_3),
                context.getString(R.string.manfaat_ragam_makanan_3),
                context.getString(R.string.porsi_ragam_makanan_3),
                context.getString(R.string.kandungan_ragam_makanan_3),
                context.getString(R.string.masalah_ragam_makanan_3)
            )
        )

        data.add(
            RagamMakanan(
                4,
                context.getString(R.string.title_ragam_makanan_4),
                context.getString(R.string.pengertian_ragam_makanan_4),
                context.getString(R.string.manfaat_ragam_makanan_4),
                context.getString(R.string.porsi_ragam_makanan_4),
                context.getString(R.string.kandungan_ragam_makanan_4),
                context.getString(R.string.masalah_ragam_makanan_4)
            )
        )

        data.add(
            RagamMakanan(
                5,
                context.getString(R.string.title_ragam_makanan_5),
                context.getString(R.string.pengertian_ragam_makanan_5),
                context.getString(R.string.manfaat_ragam_makanan_5),
                context.getString(R.string.porsi_ragam_makanan_5),
                context.getString(R.string.kandungan_ragam_makanan_5),
                context.getString(R.string.masalah_ragam_makanan_5)
            )
        )

        return data
    }
}