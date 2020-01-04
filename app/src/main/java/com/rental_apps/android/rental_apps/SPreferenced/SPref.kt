package com.rental_apps.android.rental_apps.SPreferenced

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
object SPref {
    fun getIdUser(): String? {
        return idUser
    }

    fun getUSERNAME(): String? {
        return uSERNAME

    }

    fun getNAME(): String? {
        return getNAME()
    }

    fun getEMAIL(): String? {
        return getEMAIL()
    }

    fun getNoTelp(): String? {
        return getNoTelp()
    }

    fun getJenisKelamin(): String? {
        return getJenisKelamin()
    }

    fun getPHOTO(): String? {
        return getPHOTO()
    }

    fun getLastUpdate(): String? {
        return getLastUpdate()
    }

    fun getALAMAT(): String? {
        return getALAMAT()
    }

    fun getGroupUser(): String? {
        return getGroupUser()
    }

    fun getPASSWORD(): String? {
        return getPASSWORD()
    }

    const val idUser = "ID_USER"
    const val uSERNAME = "USERNAME"
    const val nIK = "NIK"
    const val nAME = "NAME"
    const val eMAIL = "EMAIL"
    const val noTelp = "NO_TELP"
    const val jenisKelamin = "JENIS_KELAMIN"
    const val pHOTO = "PHOTO"
    const val lastUpdate = "LAST_UPDATE"
    const val aLAMAT = "ALAMAT"
    const val groupUser = "GROUP_USER"
    private var PASSWORD = "PASSWORD"
    var CARTS = "CARTS"

}