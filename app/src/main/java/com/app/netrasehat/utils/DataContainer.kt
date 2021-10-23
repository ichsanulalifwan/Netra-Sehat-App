package com.app.netrasehat.utils

import android.content.Context
import com.app.netrasehat.R
import com.app.netrasehat.model.JenisJenisMakanan
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
                context.getString(R.string.jenis_ragam_makanan_1),
                context.getString(R.string.porsi_ragam_makanan_1)
            )
        )

        data.add(
            RagamMakanan(
                2,
                context.getString(R.string.title_ragam_makanan_2),
                context.getString(R.string.pengertian_ragam_makanan_2),
                context.getString(R.string.manfaat_ragam_makanan_2),
                context.getString(R.string.jenis_ragam_makanan_2),
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
                context.getString(R.string.jenis_ragam_makanan_3),
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
                context.getString(R.string.jenis_ragam_makanan_4),
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
                context.getString(R.string.jenis_ragam_makanan_5),
                context.getString(R.string.porsi_ragam_makanan_5),
                context.getString(R.string.kandungan_ragam_makanan_5),
                context.getString(R.string.masalah_ragam_makanan_5)
            )
        )

        return data
    }

    fun generateJenisMakananPokok(context: Context): List<JenisJenisMakanan> {

        val data = ArrayList<JenisJenisMakanan>()

        data.add(
            JenisJenisMakanan(
                1,
                context.getString(R.string.title_jenis_makananpokok_1),
                context.getString(R.string.pengertian_jenis_makananpokok_1),
                context.getString(R.string.manfaat_jenis_makananpokok_1),
                context.getString(R.string.jenis_jenis_makananpokok_1),
                context.getString(R.string.porsi_jenis_makananpokok_1),
                context.getString(R.string.kandungan_jenis_makananpokok_1),
                context.getString(R.string.masalah_jenis_makananpokok_1)
            )
        )

        data.add(
            JenisJenisMakanan(
                2,
                context.getString(R.string.title_jenis_makananpokok_2),
                context.getString(R.string.pengertian_jenis_makananpokok_2),
                context.getString(R.string.manfaat_jenis_makananpokok_2),
                context.getString(R.string.jenis_jenis_makananpokok_2),
                context.getString(R.string.porsi_jenis_makananpokok_2),
                context.getString(R.string.kandungan_jenis_makananpokok_2),
                context.getString(R.string.masalah_jenis_makananpokok_2)
            )
        )

        data.add(
            JenisJenisMakanan(
                3,
                context.getString(R.string.title_jenis_makananpokok_3),
                context.getString(R.string.pengertian_jenis_makananpokok_3),
                context.getString(R.string.manfaat_jenis_makananpokok_3),
                context.getString(R.string.jenis_jenis_makananpokok_3),
                context.getString(R.string.porsi_jenis_makananpokok_3),
                context.getString(R.string.kandungan_jenis_makananpokok_3),
                context.getString(R.string.masalah_jenis_makananpokok_3)
            )
        )

        data.add(
            JenisJenisMakanan(
                4,
                context.getString(R.string.title_jenis_makananpokok_4),
                context.getString(R.string.pengertian_jenis_makananpokok_4),
                context.getString(R.string.manfaat_jenis_makananpokok_4),
                context.getString(R.string.jenis_jenis_makananpokok_4),
                context.getString(R.string.porsi_jenis_makananpokok_4),
                context.getString(R.string.kandungan_jenis_makananpokok_4),
                context.getString(R.string.masalah_jenis_makananpokok_4)
            )
        )

        data.add(
            JenisJenisMakanan(
                5,
                context.getString(R.string.title_jenis_makananpokok_5),
                context.getString(R.string.pengertian_jenis_makananpokok_5),
                context.getString(R.string.manfaat_jenis_makananpokok_5),
                context.getString(R.string.jenis_jenis_makananpokok_5),
                context.getString(R.string.porsi_jenis_makananpokok_5),
                context.getString(R.string.kandungan_jenis_makananpokok_5),
                context.getString(R.string.masalah_jenis_makananpokok_5)
            )
        )

        data.add(
            JenisJenisMakanan(
                6,
                context.getString(R.string.title_jenis_makananpokok_6),
                context.getString(R.string.pengertian_jenis_makananpokok_6),
                context.getString(R.string.manfaat_jenis_makananpokok_6),
                context.getString(R.string.jenis_jenis_makananpokok_6),
                context.getString(R.string.porsi_jenis_makananpokok_6),
                context.getString(R.string.kandungan_jenis_makananpokok_6),
                context.getString(R.string.masalah_jenis_makananpokok_6)
            )
        )

        data.add(
            JenisJenisMakanan(
                7,
                context.getString(R.string.title_jenis_makananpokok_7),
                context.getString(R.string.pengertian_jenis_makananpokok_7),
                context.getString(R.string.manfaat_jenis_makananpokok_7),
                context.getString(R.string.jenis_jenis_makananpokok_7),
                context.getString(R.string.porsi_jenis_makananpokok_7),
                context.getString(R.string.kandungan_jenis_makananpokok_7),
                context.getString(R.string.masalah_jenis_makananpokok_7)
            )
        )

        return data
    }

    fun generateJenisLaukPauk(context: Context): List<JenisJenisMakanan> {

        val data = ArrayList<JenisJenisMakanan>()

        data.add(
            JenisJenisMakanan(
                1,
                context.getString(R.string.title_jenis_laukpauk_1),
                context.getString(R.string.pengertian_jenis_laukpauk_1),
                context.getString(R.string.manfaat_jenis_laukpauk_1),
                context.getString(R.string.jenis_jenis_laukpauk_1),
                context.getString(R.string.porsi_jenis_laukpauk_1),
                context.getString(R.string.kandungan_jenis_laukpauk_1),
                context.getString(R.string.masalah_jenis_laukpauk_1)
            )
        )

        data.add(
            JenisJenisMakanan(
                2,
                context.getString(R.string.title_jenis_laukpauk_2),
                context.getString(R.string.pengertian_jenis_laukpauk_2),
                context.getString(R.string.manfaat_jenis_laukpauk_2),
                context.getString(R.string.jenis_jenis_laukpauk_2),
                context.getString(R.string.porsi_jenis_laukpauk_2),
                context.getString(R.string.kandungan_jenis_laukpauk_2),
                context.getString(R.string.masalah_jenis_laukpauk_2)
            )
        )

        data.add(
            JenisJenisMakanan(
                3,
                context.getString(R.string.title_jenis_laukpauk_3),
                context.getString(R.string.pengertian_jenis_laukpauk_3),
                context.getString(R.string.manfaat_jenis_laukpauk_3),
                context.getString(R.string.jenis_jenis_laukpauk_3),
                context.getString(R.string.porsi_jenis_laukpauk_3),
                context.getString(R.string.kandungan_jenis_laukpauk_3),
                context.getString(R.string.masalah_jenis_laukpauk_3)
            )
        )

        data.add(
            JenisJenisMakanan(
                4,
                context.getString(R.string.title_jenis_laukpauk_4),
                context.getString(R.string.pengertian_jenis_laukpauk_4),
                context.getString(R.string.manfaat_jenis_laukpauk_4),
                context.getString(R.string.jenis_jenis_laukpauk_4),
                context.getString(R.string.porsi_jenis_laukpauk_4),
                context.getString(R.string.kandungan_jenis_laukpauk_4),
                context.getString(R.string.masalah_jenis_laukpauk_4)
            )
        )

        data.add(
            JenisJenisMakanan(
                5,
                context.getString(R.string.title_jenis_laukpauk_5),
                context.getString(R.string.pengertian_jenis_laukpauk_5),
                context.getString(R.string.manfaat_jenis_laukpauk_5),
                context.getString(R.string.jenis_jenis_laukpauk_5),
                context.getString(R.string.porsi_jenis_laukpauk_5),
                context.getString(R.string.kandungan_jenis_laukpauk_5),
                context.getString(R.string.masalah_jenis_laukpauk_5)
            )
        )

        data.add(
            JenisJenisMakanan(
                6,
                context.getString(R.string.title_jenis_laukpauk_6),
                context.getString(R.string.pengertian_jenis_laukpauk_6),
                context.getString(R.string.manfaat_jenis_laukpauk_6),
                context.getString(R.string.jenis_jenis_laukpauk_6),
                context.getString(R.string.porsi_jenis_laukpauk_6),
                context.getString(R.string.kandungan_jenis_laukpauk_6),
                context.getString(R.string.masalah_jenis_laukpauk_6)
            )
        )

        data.add(
            JenisJenisMakanan(
                7,
                context.getString(R.string.title_jenis_laukpauk_7),
                context.getString(R.string.pengertian_jenis_laukpauk_7),
                context.getString(R.string.manfaat_jenis_laukpauk_7),
                context.getString(R.string.jenis_jenis_laukpauk_7),
                context.getString(R.string.porsi_jenis_laukpauk_7),
                context.getString(R.string.kandungan_jenis_laukpauk_7),
                context.getString(R.string.masalah_jenis_laukpauk_7)
            )
        )

        data.add(
            JenisJenisMakanan(
                8,
                context.getString(R.string.title_jenis_laukpauk_8),
                context.getString(R.string.pengertian_jenis_laukpauk_8),
                context.getString(R.string.manfaat_jenis_laukpauk_8),
                context.getString(R.string.jenis_jenis_laukpauk_8),
                context.getString(R.string.porsi_jenis_laukpauk_8),
                context.getString(R.string.kandungan_jenis_laukpauk_8),
                context.getString(R.string.masalah_jenis_laukpauk_8)
            )
        )

        data.add(
            JenisJenisMakanan(
                9,
                context.getString(R.string.title_jenis_laukpauk_9),
                context.getString(R.string.pengertian_jenis_laukpauk_9),
                context.getString(R.string.manfaat_jenis_laukpauk_9),
                context.getString(R.string.jenis_jenis_laukpauk_9),
                context.getString(R.string.porsi_jenis_laukpauk_9),
                context.getString(R.string.kandungan_jenis_laukpauk_9),
                context.getString(R.string.masalah_jenis_laukpauk_9)
            )
        )

        return data
    }

    fun generateJenisSayuran(context: Context): List<JenisJenisMakanan> {

        val data = ArrayList<JenisJenisMakanan>()

        data.add(
            JenisJenisMakanan(
                1,
                context.getString(R.string.title_jenis_sayuran_1),
                context.getString(R.string.pengertian_jenis_sayuran_1),
                context.getString(R.string.manfaat_jenis_sayuran_1),
                context.getString(R.string.jenis_jenis_sayuran_1),
                context.getString(R.string.porsi_jenis_sayuran_1),
                context.getString(R.string.kandungan_jenis_sayuran_1),
                context.getString(R.string.masalah_jenis_sayuran_1)
            )
        )

        data.add(
            JenisJenisMakanan(
                2,
                context.getString(R.string.title_jenis_sayuran_2),
                context.getString(R.string.pengertian_jenis_sayuran_2),
                context.getString(R.string.manfaat_jenis_sayuran_2),
                context.getString(R.string.jenis_jenis_sayuran_2),
                context.getString(R.string.porsi_jenis_sayuran_2),
                context.getString(R.string.kandungan_jenis_sayuran_2),
                context.getString(R.string.masalah_jenis_sayuran_2)
            )
        )

        data.add(
            JenisJenisMakanan(
                3,
                context.getString(R.string.title_jenis_sayuran_3),
                context.getString(R.string.pengertian_jenis_sayuran_3),
                context.getString(R.string.manfaat_jenis_sayuran_3),
                context.getString(R.string.jenis_jenis_sayuran_3),
                context.getString(R.string.porsi_jenis_sayuran_3),
                context.getString(R.string.kandungan_jenis_sayuran_3),
                context.getString(R.string.masalah_jenis_sayuran_3)
            )
        )

        data.add(
            JenisJenisMakanan(
                4,
                context.getString(R.string.title_jenis_sayuran_4),
                context.getString(R.string.pengertian_jenis_sayuran_4),
                context.getString(R.string.manfaat_jenis_sayuran_4),
                context.getString(R.string.jenis_jenis_sayuran_4),
                context.getString(R.string.porsi_jenis_sayuran_4),
                context.getString(R.string.kandungan_jenis_sayuran_4),
                context.getString(R.string.masalah_jenis_sayuran_4)
            )
        )

        data.add(
            JenisJenisMakanan(
                5,
                context.getString(R.string.title_jenis_sayuran_5),
                context.getString(R.string.pengertian_jenis_sayuran_5),
                context.getString(R.string.manfaat_jenis_sayuran_5),
                context.getString(R.string.jenis_jenis_sayuran_5),
                context.getString(R.string.porsi_jenis_sayuran_5),
                context.getString(R.string.kandungan_jenis_sayuran_5),
                context.getString(R.string.masalah_jenis_sayuran_5)
            )
        )

        data.add(
            JenisJenisMakanan(
                6,
                context.getString(R.string.title_jenis_sayuran_6),
                context.getString(R.string.pengertian_jenis_sayuran_6),
                context.getString(R.string.manfaat_jenis_sayuran_6),
                context.getString(R.string.jenis_jenis_sayuran_6),
                context.getString(R.string.porsi_jenis_sayuran_6),
                context.getString(R.string.kandungan_jenis_sayuran_6),
                context.getString(R.string.masalah_jenis_sayuran_6)
            )
        )

        data.add(
            JenisJenisMakanan(
                7,
                context.getString(R.string.title_jenis_sayuran_7),
                context.getString(R.string.pengertian_jenis_sayuran_7),
                context.getString(R.string.manfaat_jenis_sayuran_7),
                context.getString(R.string.jenis_jenis_sayuran_7),
                context.getString(R.string.porsi_jenis_sayuran_7),
                context.getString(R.string.kandungan_jenis_sayuran_7),
                context.getString(R.string.masalah_jenis_sayuran_7)
            )
        )

        data.add(
            JenisJenisMakanan(
                8,
                context.getString(R.string.title_jenis_sayuran_8),
                context.getString(R.string.pengertian_jenis_sayuran_8),
                context.getString(R.string.manfaat_jenis_sayuran_8),
                context.getString(R.string.jenis_jenis_sayuran_8),
                context.getString(R.string.porsi_jenis_sayuran_8),
                context.getString(R.string.kandungan_jenis_sayuran_8),
                context.getString(R.string.masalah_jenis_sayuran_8)
            )
        )

        data.add(
            JenisJenisMakanan(
                9,
                context.getString(R.string.title_jenis_sayuran_9),
                context.getString(R.string.pengertian_jenis_sayuran_9),
                context.getString(R.string.manfaat_jenis_sayuran_9),
                context.getString(R.string.jenis_jenis_sayuran_9),
                context.getString(R.string.porsi_jenis_sayuran_9),
                context.getString(R.string.kandungan_jenis_sayuran_9),
                context.getString(R.string.masalah_jenis_sayuran_9)
            )
        )

        data.add(
            JenisJenisMakanan(
                10,
                context.getString(R.string.title_jenis_sayuran_10),
                context.getString(R.string.pengertian_jenis_sayuran_10),
                context.getString(R.string.manfaat_jenis_sayuran_10),
                context.getString(R.string.jenis_jenis_sayuran_10),
                context.getString(R.string.porsi_jenis_sayuran_10),
                context.getString(R.string.kandungan_jenis_sayuran_10),
                context.getString(R.string.masalah_jenis_sayuran_10)
            )
        )

        data.add(
            JenisJenisMakanan(
                11,
                context.getString(R.string.title_jenis_sayuran_11),
                context.getString(R.string.pengertian_jenis_sayuran_11),
                context.getString(R.string.manfaat_jenis_sayuran_11),
                context.getString(R.string.jenis_jenis_sayuran_11),
                context.getString(R.string.porsi_jenis_sayuran_11),
                context.getString(R.string.kandungan_jenis_sayuran_11),
                context.getString(R.string.masalah_jenis_sayuran_11)
            )
        )

        data.add(
            JenisJenisMakanan(
                12,
                context.getString(R.string.title_jenis_sayuran_12),
                context.getString(R.string.pengertian_jenis_sayuran_12),
                context.getString(R.string.manfaat_jenis_sayuran_12),
                context.getString(R.string.jenis_jenis_sayuran_12),
                context.getString(R.string.porsi_jenis_sayuran_12),
                context.getString(R.string.kandungan_jenis_sayuran_12),
                context.getString(R.string.masalah_jenis_sayuran_12)
            )
        )

        data.add(
            JenisJenisMakanan(
                13,
                context.getString(R.string.title_jenis_sayuran_13),
                context.getString(R.string.pengertian_jenis_sayuran_13),
                context.getString(R.string.manfaat_jenis_sayuran_13),
                context.getString(R.string.jenis_jenis_sayuran_13),
                context.getString(R.string.porsi_jenis_sayuran_13),
                context.getString(R.string.kandungan_jenis_sayuran_13),
                context.getString(R.string.masalah_jenis_sayuran_13)
            )
        )

        data.add(
            JenisJenisMakanan(
                14,
                context.getString(R.string.title_jenis_sayuran_14),
                context.getString(R.string.pengertian_jenis_sayuran_14),
                context.getString(R.string.manfaat_jenis_sayuran_14),
                context.getString(R.string.jenis_jenis_sayuran_14),
                context.getString(R.string.porsi_jenis_sayuran_14),
                context.getString(R.string.kandungan_jenis_sayuran_14),
                context.getString(R.string.masalah_jenis_sayuran_14)
            )
        )

        data.add(
            JenisJenisMakanan(
                15,
                context.getString(R.string.title_jenis_sayuran_15),
                context.getString(R.string.pengertian_jenis_sayuran_15),
                context.getString(R.string.manfaat_jenis_sayuran_15),
                context.getString(R.string.jenis_jenis_sayuran_15),
                context.getString(R.string.porsi_jenis_sayuran_15),
                context.getString(R.string.kandungan_jenis_sayuran_15),
                context.getString(R.string.masalah_jenis_sayuran_15)
            )
        )

        return data
    }

    fun generateJenisBuah(context: Context): List<JenisJenisMakanan> {

        val data = ArrayList<JenisJenisMakanan>()

        data.add(
            JenisJenisMakanan(
                1,
                context.getString(R.string.title_jenis_buah_1),
                context.getString(R.string.pengertian_jenis_buah_1),
                context.getString(R.string.manfaat_jenis_buah_1),
                context.getString(R.string.jenis_jenis_buah_1),
                context.getString(R.string.porsi_jenis_buah_1),
                context.getString(R.string.kandungan_jenis_buah_1),
                context.getString(R.string.masalah_jenis_buah_1)
            )
        )

        data.add(
            JenisJenisMakanan(
                2,
                context.getString(R.string.title_jenis_buah_2),
                context.getString(R.string.pengertian_jenis_buah_2),
                context.getString(R.string.manfaat_jenis_buah_2),
                context.getString(R.string.jenis_jenis_buah_2),
                context.getString(R.string.porsi_jenis_buah_2),
                context.getString(R.string.kandungan_jenis_buah_2),
                context.getString(R.string.masalah_jenis_buah_2)
            )
        )

        data.add(
            JenisJenisMakanan(
                3,
                context.getString(R.string.title_jenis_buah_3),
                context.getString(R.string.pengertian_jenis_buah_3),
                context.getString(R.string.manfaat_jenis_buah_3),
                context.getString(R.string.jenis_jenis_buah_3),
                context.getString(R.string.porsi_jenis_buah_3),
                context.getString(R.string.kandungan_jenis_buah_3),
                context.getString(R.string.masalah_jenis_buah_3)
            )
        )

        data.add(
            JenisJenisMakanan(
                4,
                context.getString(R.string.title_jenis_buah_4),
                context.getString(R.string.pengertian_jenis_buah_4),
                context.getString(R.string.manfaat_jenis_buah_4),
                context.getString(R.string.jenis_jenis_buah_4),
                context.getString(R.string.porsi_jenis_buah_4),
                context.getString(R.string.kandungan_jenis_buah_4),
                context.getString(R.string.masalah_jenis_buah_4)
            )
        )

        data.add(
            JenisJenisMakanan(
                5,
                context.getString(R.string.title_jenis_buah_5),
                context.getString(R.string.pengertian_jenis_buah_5),
                context.getString(R.string.manfaat_jenis_buah_5),
                context.getString(R.string.jenis_jenis_buah_5),
                context.getString(R.string.porsi_jenis_buah_5),
                context.getString(R.string.kandungan_jenis_buah_5),
                context.getString(R.string.masalah_jenis_buah_5)
            )
        )

        data.add(
            JenisJenisMakanan(
                6,
                context.getString(R.string.title_jenis_buah_6),
                context.getString(R.string.pengertian_jenis_buah_6),
                context.getString(R.string.manfaat_jenis_buah_6),
                context.getString(R.string.jenis_jenis_buah_6),
                context.getString(R.string.porsi_jenis_buah_6),
                context.getString(R.string.kandungan_jenis_buah_6),
                context.getString(R.string.masalah_jenis_buah_6)
            )
        )

        data.add(
            JenisJenisMakanan(
                7,
                context.getString(R.string.title_jenis_buah_7),
                context.getString(R.string.pengertian_jenis_buah_7),
                context.getString(R.string.manfaat_jenis_buah_7),
                context.getString(R.string.jenis_jenis_buah_7),
                context.getString(R.string.porsi_jenis_buah_7),
                context.getString(R.string.kandungan_jenis_buah_7),
                context.getString(R.string.masalah_jenis_buah_7)
            )
        )

        data.add(
            JenisJenisMakanan(
                8,
                context.getString(R.string.title_jenis_buah_8),
                context.getString(R.string.pengertian_jenis_buah_8),
                context.getString(R.string.manfaat_jenis_buah_8),
                context.getString(R.string.jenis_jenis_buah_8),
                context.getString(R.string.porsi_jenis_buah_8),
                context.getString(R.string.kandungan_jenis_buah_8),
                context.getString(R.string.masalah_jenis_buah_8)
            )
        )

        data.add(
            JenisJenisMakanan(
                9,
                context.getString(R.string.title_jenis_buah_9),
                context.getString(R.string.pengertian_jenis_buah_9),
                context.getString(R.string.manfaat_jenis_buah_9),
                context.getString(R.string.jenis_jenis_buah_9),
                context.getString(R.string.porsi_jenis_buah_9),
                context.getString(R.string.kandungan_jenis_buah_9),
                context.getString(R.string.masalah_jenis_buah_9)
            )
        )

        data.add(
            JenisJenisMakanan(
                10,
                context.getString(R.string.title_jenis_buah_10),
                context.getString(R.string.pengertian_jenis_buah_10),
                context.getString(R.string.manfaat_jenis_buah_10),
                context.getString(R.string.jenis_jenis_buah_10),
                context.getString(R.string.porsi_jenis_buah_10),
                context.getString(R.string.kandungan_jenis_buah_10),
                context.getString(R.string.masalah_jenis_buah_10)
            )
        )

        data.add(
            JenisJenisMakanan(
                11,
                context.getString(R.string.title_jenis_buah_11),
                context.getString(R.string.pengertian_jenis_buah_11),
                context.getString(R.string.manfaat_jenis_buah_11),
                context.getString(R.string.jenis_jenis_buah_11),
                context.getString(R.string.porsi_jenis_buah_11),
                context.getString(R.string.kandungan_jenis_buah_11),
                context.getString(R.string.masalah_jenis_buah_11)
            )
        )

        data.add(
            JenisJenisMakanan(
                12,
                context.getString(R.string.title_jenis_buah_12),
                context.getString(R.string.pengertian_jenis_buah_12),
                context.getString(R.string.manfaat_jenis_buah_12),
                context.getString(R.string.jenis_jenis_buah_12),
                context.getString(R.string.porsi_jenis_buah_12),
                context.getString(R.string.kandungan_jenis_buah_12),
                context.getString(R.string.masalah_jenis_buah_12)
            )
        )

        data.add(
            JenisJenisMakanan(
                13,
                context.getString(R.string.title_jenis_buah_13),
                context.getString(R.string.pengertian_jenis_buah_13),
                context.getString(R.string.manfaat_jenis_buah_13),
                context.getString(R.string.jenis_jenis_buah_13),
                context.getString(R.string.porsi_jenis_buah_13),
                context.getString(R.string.kandungan_jenis_buah_13),
                context.getString(R.string.masalah_jenis_buah_13)
            )
        )

        data.add(
            JenisJenisMakanan(
                14,
                context.getString(R.string.title_jenis_buah_14),
                context.getString(R.string.pengertian_jenis_buah_14),
                context.getString(R.string.manfaat_jenis_buah_14),
                context.getString(R.string.jenis_jenis_buah_14),
                context.getString(R.string.porsi_jenis_buah_14),
                context.getString(R.string.kandungan_jenis_buah_14),
                context.getString(R.string.masalah_jenis_buah_14)
            )
        )

        data.add(
            JenisJenisMakanan(
                15,
                context.getString(R.string.title_jenis_buah_15),
                context.getString(R.string.pengertian_jenis_buah_15),
                context.getString(R.string.manfaat_jenis_buah_15),
                context.getString(R.string.jenis_jenis_buah_15),
                context.getString(R.string.porsi_jenis_buah_15),
                context.getString(R.string.kandungan_jenis_buah_15),
                context.getString(R.string.masalah_jenis_buah_15)
            )
        )

        data.add(
            JenisJenisMakanan(
                16,
                context.getString(R.string.title_jenis_buah_16),
                context.getString(R.string.pengertian_jenis_buah_16),
                context.getString(R.string.manfaat_jenis_buah_16),
                context.getString(R.string.jenis_jenis_buah_16),
                context.getString(R.string.porsi_jenis_buah_16),
                context.getString(R.string.kandungan_jenis_buah_16),
                context.getString(R.string.masalah_jenis_buah_16)
            )
        )

        data.add(
            JenisJenisMakanan(
                17,
                context.getString(R.string.title_jenis_buah_17),
                context.getString(R.string.pengertian_jenis_buah_17),
                context.getString(R.string.manfaat_jenis_buah_17),
                context.getString(R.string.jenis_jenis_buah_17),
                context.getString(R.string.porsi_jenis_buah_17),
                context.getString(R.string.kandungan_jenis_buah_17),
                context.getString(R.string.masalah_jenis_buah_17)
            )
        )

        data.add(
            JenisJenisMakanan(
                18,
                context.getString(R.string.title_jenis_buah_18),
                context.getString(R.string.pengertian_jenis_buah_18),
                context.getString(R.string.manfaat_jenis_buah_18),
                context.getString(R.string.jenis_jenis_buah_18),
                context.getString(R.string.porsi_jenis_buah_18),
                context.getString(R.string.kandungan_jenis_buah_18),
                context.getString(R.string.masalah_jenis_buah_18)
            )
        )

        data.add(
            JenisJenisMakanan(
                19,
                context.getString(R.string.title_jenis_buah_19),
                context.getString(R.string.pengertian_jenis_buah_19),
                context.getString(R.string.manfaat_jenis_buah_19),
                context.getString(R.string.jenis_jenis_buah_19),
                context.getString(R.string.porsi_jenis_buah_19),
                context.getString(R.string.kandungan_jenis_buah_19),
                context.getString(R.string.masalah_jenis_buah_19)
            )
        )

        data.add(
            JenisJenisMakanan(
                20,
                context.getString(R.string.title_jenis_buah_20),
                context.getString(R.string.pengertian_jenis_buah_20),
                context.getString(R.string.manfaat_jenis_buah_20),
                context.getString(R.string.jenis_jenis_buah_20),
                context.getString(R.string.porsi_jenis_buah_20),
                context.getString(R.string.kandungan_jenis_buah_20),
                context.getString(R.string.masalah_jenis_buah_20)
            )
        )

        data.add(
            JenisJenisMakanan(
                21,
                context.getString(R.string.title_jenis_buah_21),
                context.getString(R.string.pengertian_jenis_buah_21),
                context.getString(R.string.manfaat_jenis_buah_21),
                context.getString(R.string.jenis_jenis_buah_21),
                context.getString(R.string.porsi_jenis_buah_21),
                context.getString(R.string.kandungan_jenis_buah_21),
                context.getString(R.string.masalah_jenis_buah_21)
            )
        )

        data.add(
            JenisJenisMakanan(
                22,
                context.getString(R.string.title_jenis_buah_22),
                context.getString(R.string.pengertian_jenis_buah_22),
                context.getString(R.string.manfaat_jenis_buah_22),
                context.getString(R.string.jenis_jenis_buah_22),
                context.getString(R.string.porsi_jenis_buah_22),
                context.getString(R.string.kandungan_jenis_buah_22),
                context.getString(R.string.masalah_jenis_buah_22)
            )
        )

        data.add(
            JenisJenisMakanan(
                23,
                context.getString(R.string.title_jenis_buah_23),
                context.getString(R.string.pengertian_jenis_buah_23),
                context.getString(R.string.manfaat_jenis_buah_23),
                context.getString(R.string.jenis_jenis_buah_23),
                context.getString(R.string.porsi_jenis_buah_23),
                context.getString(R.string.kandungan_jenis_buah_23),
                context.getString(R.string.masalah_jenis_buah_23)
            )
        )

        return data
    }
}