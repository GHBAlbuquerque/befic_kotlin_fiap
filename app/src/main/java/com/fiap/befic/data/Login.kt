package com.fiap.befic.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class Login(

    @SerializedName("id")
    val id: Long,
    @SerializedName("usuario")
    val usuario: Usuario,
    @SerializedName("username")
    val username: String,
    @SerializedName("senha")
    val senha: String,
    @SerializedName("dtCadastro")
    val dtCadastro: Date
)
