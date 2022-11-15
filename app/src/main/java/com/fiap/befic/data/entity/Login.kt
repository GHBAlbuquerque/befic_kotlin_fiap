package com.fiap.befic.data.entity

import com.google.gson.annotations.SerializedName

data class Login(

    @SerializedName("id")
    val id: Long?,
    @SerializedName("usuario")
    val usuario: Usuario,
    @SerializedName("username")
    val username: String,
    @SerializedName("senha")
    val senha: String,
    @SerializedName("dtCadastro")
    val String: String?
)
