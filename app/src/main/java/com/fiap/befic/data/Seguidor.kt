package com.fiap.befic.data

import com.google.gson.annotations.SerializedName

data class Seguidor(

    @SerializedName("id")
    val id: String,
    @SerializedName("userId")
    val userId: Long,
    @SerializedName("followerId")
    val followerId: Long,
    @SerializedName("followerUsername")
    val followerUsername: String

)
