package com.fiap.befic.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*

data class Historia(

    @SerializedName("id")
    val id: Long?,
    @SerializedName("autor")
    val autor: Usuario,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("dtPublicacao")
    val dtPublicacao: String?,
    @SerializedName("dtAtualizacao")
    val dtAtualizacao: String?,
    @SerializedName("sinopse")
    val sinopse: String
)
