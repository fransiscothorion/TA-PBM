package com.rental_apps.android.rental_apps.model.model_user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataUser {
    fun getId_user(): Int {
        return getId_user()

    }

    public final fun getUsername(): String? {
        return getUsername()
    }

    fun getName(): String? {
        return getName()
    }

    fun getEmail(): String? {
        return getEmail()
    }

    fun getNo_telp(): String? {
        return getNo_telp()
    }

    fun getJenis_kelamin(): String {
        return getJenis_kelamin()
    }

    fun getPhoto(): String? {
        return getPhoto()
    }

    fun getLast_update(): String {
        return getLast_update()
    }

    fun getAlamat(): String? {
        return getAlamat()
    }

    fun getGroup_user(): Int {
        return getGroup_user()
    }

    fun getPassword(): String {
        return getPassword()
    }

    @SerializedName("ID_USER")
    @Expose
    var id_user: Int? = null
    @SerializedName("NIK")
    @Expose
    var nik: String? = null
    @SerializedName("USERNAME")
    @Expose
    var username: String? = null
    @SerializedName("NAME")
    @Expose
    var name: String? = null
    @SerializedName("EMAIL")
    @Expose
    var email: String? = null
    @SerializedName("NO_TELP")
    @Expose
    var no_telp: String? = null
    @SerializedName("JENIS_KELAMIN")
    @Expose
    var jenis_kelamin: Char? = null
    @SerializedName("ALAMAT")
    @Expose
    var alamat: String? = null
    @SerializedName("PASSWORD")
    @Expose
    var password: String? = null
    @SerializedName("PHOTO")
    @Expose
    var photo: String? = null
    @SerializedName("ACTIVATED")
    @Expose
    var activated: Int? = null
    @SerializedName("CREATED")
    @Expose
    var created: String? = null
    @SerializedName("GROUP_USER")
    @Expose
    var group_user: Int? = null
    @SerializedName("LAST_LOGIN")
    @Expose
    var last_logn: String? = null
    @SerializedName("LAST_UPDATE")
    @Expose
    var last_update: String? = null

}