package com.fiap.befic.service

import com.fiap.befic.data.Capitulo
import retrofit2.Call
import retrofit2.http.*

interface CapituloBeficBackendService {

    @GET("capitulos")
    fun list(): Call<List<Capitulo>>

    @GET("capitulos/{id}")
    fun findById(@Path("id") id: Long): Call<Capitulo>

    @POST("capitulos")
    fun save(@Body capitulo: Capitulo): Call<Capitulo>

    @DELETE("capitulos/{id}")
    fun delete(@Path("id") id: Long): Call<Void>

}