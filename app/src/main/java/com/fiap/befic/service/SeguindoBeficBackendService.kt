package com.fiap.befic.service

import com.fiap.befic.data.Seguindo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SeguindoBeficBackendService {

    @GET("seguindo/{userId}")
    fun listByUser(@Path("userId") userId: Long): Call<List<Seguindo>>

}