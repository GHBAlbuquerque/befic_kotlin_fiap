package com.fiap.befic.service

import com.fiap.befic.data.Capitulo
import com.fiap.befic.data.CapituloId
import retrofit2.Call
import retrofit2.http.*

interface CapituloBeficBackendService {

    @GET("capitulos")
    fun list(): Call<List<Capitulo>>

    @POST("capitulos/buscar")
    fun findById(@Body capituloId: CapituloId): Call<Capitulo>

    @POST("capitulos")
    fun save(@Body capitulo: Capitulo): Call<Capitulo>

    @DELETE("capitulos/deletar")
    fun delete(@Body capituloId: CapituloId): Call<Void>

}