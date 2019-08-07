package com.example.jewelleryclassification.network

import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val BASE_URL = "http://10.0.2.2:5000/"

// The Gson Builder
private val gson = GsonBuilder()
    .setLenient()
    .create()

//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()


// Creating Retrofit object
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()

interface PredApiService {
    @Multipart
    @POST("predict")
    suspend fun getPredictionAndUpload(@Part file: MultipartBody.Part) : Call<Any>
}

object PredApi {
    val retrofitService : PredApiService by lazy {
        retrofit.create(PredApiService::class.java)
    }
}

