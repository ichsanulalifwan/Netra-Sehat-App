package com.app.netrasehat.model

class JenisJenisMakanan(
    val id: Int,
    val judul: String,
    val pengertian: String,
    val manfaat: String,
    val jenis: String,
    val porsi: String,
    val kandungan: String? = "",
    val masalah: String? = "",
    val img: Int? = 0
)