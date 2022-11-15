package com.fiap.befic.data.dto

import com.google.gson.annotations.SerializedName

data class CreateStoryDto(

    @SerializedName("autor")
    val autor: Long,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("sinopse")
    val sinopse: String,
)