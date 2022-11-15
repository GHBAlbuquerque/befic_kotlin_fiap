package com.fiap.befic.service

import com.fiap.befic.data.dto.CreateChapterDto
import com.fiap.befic.data.entity.Capitulo
import com.fiap.befic.data.entity.CapituloId
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface CapituloBeficBackendService {

    @GET("capitulos")
    fun list(): Call<List<Capitulo>>

    @POST("capitulos/buscar")
    fun findById(@Body capituloId: CapituloId): Call<Capitulo>

    @POST("capitulos")
    fun save(@Body capituloDto: CreateChapterDto): Call<Capitulo>

    @DELETE("capitulos/deletar")
    fun delete(@Body capituloId: CapituloId): Call<Void>

}