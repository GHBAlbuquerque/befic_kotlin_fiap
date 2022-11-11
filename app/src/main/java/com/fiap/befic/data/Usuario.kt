package com.fiap.befic.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*

data class Usuario(

    @SerializedName("id")
    val id: Long?,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("dtNasc")
    val dtNasc: String?,
    @SerializedName("celular")
    val celular: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("genero")
    val genero: String,
    @SerializedName("perfil")
    val perfil: String?,
    @SerializedName("dtCadastro")
    val dtCadastro: String?
)