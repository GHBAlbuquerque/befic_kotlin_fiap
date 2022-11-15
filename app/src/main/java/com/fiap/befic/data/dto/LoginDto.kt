package com.fiap.befic.data.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(

    @SerializedName("username")
    val username: String,
    @SerializedName("senha")
    val senha: String
)
