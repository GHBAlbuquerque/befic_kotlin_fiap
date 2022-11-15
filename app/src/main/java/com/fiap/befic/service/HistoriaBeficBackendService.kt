package com.fiap.befic.service

import com.fiap.befic.data.dto.CreateStoryDto
import com.fiap.befic.data.entity.Capitulo
import com.fiap.befic.data.entity.Historia
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HistoriaBeficBackendService {

    @GET("historias")
    fun list(): Call<List<Historia>>

    @GET("historias/{id}")
    fun findById(@Path("id") id: Long): Call<Historia>

    @GET("historias/autor/{userId}")
    fun findByAutor(@Path("userId") userId: Long): Call<List<Historia>>

    @POST("historias")
    fun save(@Body historia: CreateStoryDto): Call<Historia>

    @GET("historias/{id}/capitulos")
    fun getCapitulos(@Path("id") id: Long): Call<List<Capitulo>>

}