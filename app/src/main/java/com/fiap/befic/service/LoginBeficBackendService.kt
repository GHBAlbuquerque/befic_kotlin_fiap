package com.fiap.befic.service

import com.fiap.befic.data.Login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginBeficBackendService {

    @GET("login")
    fun list(): Call<List<Login>>

    @GET("login/{userId}")
    fun findByUsuario(@Path("userId") userId: Long): Call<Login>

    @POST("login")
    fun save(@Body login: Login): Call<Login>

}