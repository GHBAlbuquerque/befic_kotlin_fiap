package com.fiap.befic.service

import com.fiap.befic.data.entity.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioBeficBackendService {

    @GET("usuarios")
    fun list(): Call<List<Usuario>>

    @GET("usuarios/{id}")
    fun findById(@Path("id") id: Long): Call<Usuario>

    @POST("usuarios")
    fun save(@Body usuario: Usuario): Call<Usuario>

//    //https://viacep.com.br/ws/SP/Sao%20Paulo/Avenida%20Lins%20de%20Vasconcelos/json/
//    @GET("{estado}/{cidade}/{endereco}/json/")
//    fun getRCE(
//        @Path("estado") estado: String,
//        @Path("cidade") cidade: String,
//        @Path("endereco") endereco: String
//    ): Call<List<CEP>>
}