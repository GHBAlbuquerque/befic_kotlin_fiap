package com.fiap.befic.data.entity

import com.google.gson.annotations.SerializedName

data class Seguindo(

    @SerializedName("id")
    val id: String,
    @SerializedName("userId")
    val userId: Long,
    @SerializedName("followingId")
    val followingId: Long,
    @SerializedName("followingUsername")
    val followingUsername: String

)
