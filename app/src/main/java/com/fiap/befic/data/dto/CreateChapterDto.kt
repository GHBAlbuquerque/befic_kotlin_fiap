package com.fiap.befic.data.dto

import com.google.gson.annotations.SerializedName

data class CreateChapterDto(
    @SerializedName("historiaId")
    val historiaId: Long,
    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("conteudo")
    val conteudo: String,
    @SerializedName("notasIniciais")
    val notasIniciais: String?,
    @SerializedName("notasFinais")
    val notasFinais: String?,
)
