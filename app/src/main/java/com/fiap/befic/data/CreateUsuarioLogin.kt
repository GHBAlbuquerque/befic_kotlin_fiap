package com.fiap.befic.data

import com.google.gson.annotations.SerializedName

data class CreateUsuarioLogin(

    @SerializedName("username")
    val username: String,
    @SerializedName("senha")
    val senha: String,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("ano")
    val ano: Int,
    @SerializedName("mes")
    val mes: Int,
    @SerializedName("dia")
    val dia: Int,
    @SerializedName("celular")
    val celular: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("genero")
    val genero: String
)
