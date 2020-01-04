package com.rental_apps.android.rental_apps.helper

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */
object Hash {
    fun MD5(md5: String): String? {
        try {
            val md = MessageDigest.getInstance("MD5")
            val array = md.digest(md5.toByteArray())
            val sb = StringBuffer()
            for (i in array.indices) {
                sb.append(Integer.toHexString(array[i] and 0xFF or 0x100).substring(1, 3))
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
        }
        return null
    }
}