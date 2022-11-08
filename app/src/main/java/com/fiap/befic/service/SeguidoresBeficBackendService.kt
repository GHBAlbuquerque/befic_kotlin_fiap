package com.fiap.befic.service

import com.fiap.befic.data.Seguidor
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SeguidoresBeficBackendService {

    @GET("seguidores/{userId}")
    fun listByUser(@Path("userId") userId: Long): Call<List<Seguidor>>

}