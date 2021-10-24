package com.app.netrasehat.utils

import android.content.Context
import com.app.netrasehat.R
import com.app.netrasehat.model.*

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
                context.getString(R.string.porsi_ragam_makanan_1),
                "",
                "",
                R.drawable.img_makananpokok
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
                context.getString(R.string.masalah_ragam_makanan_2),
                R.drawable.img_laukpauk
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
                context.getString(R.string.masalah_ragam_makanan_3),
                R.drawable.img_sayuran
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
                context.getString(R.string.masalah_ragam_makanan_4),
                R.drawable.img_fruits
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
                context.getString(R.string.masalah_ragam_makanan_5),
                R.drawable.img_airputih
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
                context.getString(R.string.masalah_jenis_makananpokok_1),
                R.drawable.nasi
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
                context.getString(R.string.masalah_jenis_makananpokok_2),
                R.drawable.jagung
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
                context.getString(R.string.masalah_jenis_makananpokok_3),
                R.drawable.sagu
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
                context.getString(R.string.masalah_jenis_makananpokok_4),
                R.drawable.ubijalar
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
                context.getString(R.string.masalah_jenis_makananpokok_5),
                R.drawable.singkong
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
                context.getString(R.string.masalah_jenis_makananpokok_6),
                R.drawable.mie
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
                context.getString(R.string.masalah_jenis_makananpokok_7),
                R.drawable.roti
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
                context.getString(R.string.masalah_jenis_laukpauk_1),
                R.drawable.ayam
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
                context.getString(R.string.masalah_jenis_laukpauk_2),
                R.drawable.daging
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
                context.getString(R.string.masalah_jenis_laukpauk_3),
                R.drawable.ikan
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
                context.getString(R.string.masalah_jenis_laukpauk_4),
                R.drawable.telur
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
                context.getString(R.string.masalah_jenis_laukpauk_5),
                R.drawable.kepiting
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
                context.getString(R.string.masalah_jenis_laukpauk_6),
                R.drawable.udang
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
                context.getString(R.string.masalah_jenis_laukpauk_7),
                R.drawable.cumi_cumi
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
                context.getString(R.string.masalah_jenis_laukpauk_8),
                R.drawable.tempe
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
                context.getString(R.string.masalah_jenis_laukpauk_9),
                R.drawable.tahu
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
                context.getString(R.string.masalah_jenis_sayuran_1),
                R.drawable.kelor
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
                context.getString(R.string.masalah_jenis_sayuran_2),
                R.drawable.asem
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
                context.getString(R.string.masalah_jenis_sayuran_3),
                R.drawable.sop
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
                context.getString(R.string.masalah_jenis_sayuran_4),
                R.drawable.bayam_bening
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
                context.getString(R.string.masalah_jenis_sayuran_5),
                R.drawable.lodeh
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
                context.getString(R.string.masalah_jenis_sayuran_6),
                R.drawable.tumis_sawi
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
                context.getString(R.string.masalah_jenis_sayuran_7),
                R.drawable.tumis_kacangpanjang
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
                context.getString(R.string.masalah_jenis_sayuran_8),
                R.drawable.kangkung
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
                context.getString(R.string.masalah_jenis_sayuran_9),
                R.drawable.tauge
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
                context.getString(R.string.masalah_jenis_sayuran_10),
                R.drawable.wortel
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
                context.getString(R.string.masalah_jenis_sayuran_11),
                R.drawable.buncis
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
                context.getString(R.string.masalah_jenis_sayuran_12),
                R.drawable.labu_siam
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
                context.getString(R.string.masalah_jenis_sayuran_13),
                R.drawable.balado_terong
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
                context.getString(R.string.masalah_jenis_sayuran_14),
                R.drawable.terong_bulat_hijau
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
                context.getString(R.string.masalah_jenis_sayuran_15),
                R.drawable.terong_bulat_ungu
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
                context.getString(R.string.masalah_jenis_buah_1),
                R.drawable.apel
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
                context.getString(R.string.masalah_jenis_buah_2),
                R.drawable.pepaya
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
                context.getString(R.string.masalah_jenis_buah_3),
                R.drawable.pisang
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
                context.getString(R.string.masalah_jenis_buah_4),
                R.drawable.sirsak
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
                context.getString(R.string.masalah_jenis_buah_5),
                R.drawable.alpukat
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
                context.getString(R.string.masalah_jenis_buah_6),
                R.drawable.anggur
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
                context.getString(R.string.masalah_jenis_buah_7),
                R.drawable.belimbing
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
                context.getString(R.string.masalah_jenis_buah_8),
                R.drawable.durian
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
                context.getString(R.string.masalah_jenis_buah_9),
                R.drawable.jambu_air
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
                context.getString(R.string.masalah_jenis_buah_10),
                R.drawable.jambu_biji
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
                context.getString(R.string.masalah_jenis_buah_11),
                R.drawable.jeruk_bali
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
                context.getString(R.string.masalah_jenis_buah_12),
                R.drawable.jeruk_mandarin
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
                context.getString(R.string.masalah_jenis_buah_13),
                R.drawable.kedondong
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
                context.getString(R.string.masalah_jenis_buah_14),
                R.drawable.mangga
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
                context.getString(R.string.masalah_jenis_buah_15),
                R.drawable.buah_naga
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
                context.getString(R.string.masalah_jenis_buah_16),
                R.drawable.nenas
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
                context.getString(R.string.masalah_jenis_buah_17),
                R.drawable.nangka
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
                context.getString(R.string.masalah_jenis_buah_18),
                R.drawable.pear
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
                context.getString(R.string.masalah_jenis_buah_19),
                R.drawable.rambutan
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
                context.getString(R.string.masalah_jenis_buah_20),
                R.drawable.salak
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
                context.getString(R.string.masalah_jenis_buah_21),
                R.drawable.sawo
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
                context.getString(R.string.masalah_jenis_buah_22),
                R.drawable.semangka
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
                context.getString(R.string.masalah_jenis_buah_23),
                R.drawable.stroberi
            )
        )

        return data
    }

    fun generateJenisAktivitasFisik(context: Context): List<AktivitasFisik> {

        val jenisAktivitasFisik = ArrayList<AktivitasFisik>()

        jenisAktivitasFisik.add(
            AktivitasFisik(
                1,
                context.getString(R.string.title_af_ringan),
                context.getString(R.string.pengertian_af_ringan),
                context.getString(R.string.manfaat_af_ringan),
                context.getString(R.string.contoh_af_ringan)
            )
        )

        jenisAktivitasFisik.add(
            AktivitasFisik(
                2,
                context.getString(R.string.title_af_sedang),
                context.getString(R.string.pengertian_af_sedang),
                context.getString(R.string.manfaat_af_sedang),
                context.getString(R.string.contoh_af_sedang)
            )
        )

        jenisAktivitasFisik.add(
            AktivitasFisik(
                3,
                context.getString(R.string.title_af_berat),
                context.getString(R.string.pengertian_af_berat),
                context.getString(R.string.manfaat_af_berat),
                context.getString(R.string.contoh_af_berat)
            )
        )

        return jenisAktivitasFisik
    }
}