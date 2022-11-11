package com.fiap.befic.service

import com.fiap.befic.data.CreateUsuarioLogin
import com.fiap.befic.data.Login
import com.fiap.befic.data.LoginDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginBeficBackendService {

    @POST("login/entrar")
    fun entrar(@Body loginDto: LoginDto): Call<Login>

    @GET("login")
    fun list(): Call<List<Login>>

    @GET("login/{userId}")
    fun findByUsuario(@Path("userId") userId: Long): Call<Login>

    @POST("login/criar")
    fun save(@Body createUsuarioLogin: CreateUsuarioLogin): Call<Login>

}