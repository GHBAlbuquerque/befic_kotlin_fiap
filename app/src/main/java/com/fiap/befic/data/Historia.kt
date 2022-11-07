package com.fiap.befic.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class Historia(

    @SerializedName("id")
    val id: Long,
    @SerializedName("autor")
    val autor: Usuario,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("dtPublicacao")
    val dtPublicacao: LocalDate,
    @SerializedName("dtAtualizacao")
    val dtAtualizacao: LocalDate,
    @SerializedName("sinopse")
    val sinopse: String
)
