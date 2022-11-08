package com.fiap.befic.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class Historia(

    @SerializedName("id")
    val id: Long,
    @SerializedName("autor")
    val autor: Usuario,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("dtPublicacao")
    val dtPublicacao: Date,
    @SerializedName("dtAtualizacao")
    val dtAtualizacao: Date,
    @SerializedName("sinopse")
    val sinopse: String
)
