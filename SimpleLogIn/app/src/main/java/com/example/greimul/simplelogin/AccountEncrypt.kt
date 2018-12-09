package com.example.greimul.simplelogin

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AccountEncrypt() {
    fun encrypt(data:String):String{
        val seed:String = "ForAccountEncryt"
        val key = SecretKeySpec(seed.toByteArray(),"AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val iv = ByteArray(16)
        val tmpchar = seed.toCharArray()
        for(i in 0..(tmpchar.size-1)){
            iv[i] = tmpchar[i].toByte()
        }
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE,key,ivSpec)
        val finalencrypt = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(finalencrypt,Base64.NO_WRAP)
    }
}