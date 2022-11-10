package com.fiap.befic.data

import com.google.gson.annotations.SerializedName

data class LoginDto(

    @SerializedName("username")
    val username: String,
    @SerializedName("senha")
    val senha: String
)
