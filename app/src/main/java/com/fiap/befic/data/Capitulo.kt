package com.fiap.befic.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class Capitulo(

    @SerializedName("numero")
    val numero: Long,
    @SerializedName("historia")
    val historia: Historia,
    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("dtPublicacao")
    val dtPublicacao: LocalDate,
    @SerializedName("dtAtualizacao")
    val dtAtualizacao: LocalDate,
    @SerializedName("conteudo")
    val conteudo: String,
    @SerializedName("notasIniciais")
    val notasIniciais: String,
    @SerializedName("notasFinais")
    val notasFinais: String
)
