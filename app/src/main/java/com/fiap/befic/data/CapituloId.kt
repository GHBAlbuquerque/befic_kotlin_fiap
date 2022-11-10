package com.fiap.befic.data

import com.google.gson.annotations.SerializedName

data class CapituloId(
    @SerializedName("numero")
    val numero: Long,
    @SerializedName("historiaId")
    val historiaId: Long
)
