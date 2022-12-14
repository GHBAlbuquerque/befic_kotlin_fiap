package com.fiap.befic.data.entity

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*

data class Capitulo(

    @SerializedName("numero")
    val numero: Long?,
    @SerializedName("historiaId")
    val historiaId: Long,
    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("dtPublicacao")
    val dtPublicacao: String?,
    @SerializedName("dtAtualizacao")
    val dtAtualizacao: String?,
    @SerializedName("conteudo")
    val conteudo: String,
    @SerializedName("notasIniciais")
    val notasIniciais: String,
    @SerializedName("notasFinais")
    val notasFinais: String
)
