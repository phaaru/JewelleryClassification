package com.jamlabsai.srj.network

import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val BASE_URL = "http://predict.jamlabsai.com/"

// The Gson Builder
private val gson = GsonBuilder()
    .setLenient()
    .create()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


interface PredApiService {
    @Multipart
    @POST("predict")
    suspend fun getPredictionAndUpload(@Part file: MultipartBody.Part) : Response<IndexResponse>
}

object PredApi {
    fun makePredApiService(): PredApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build().create(PredApiService::class.java)
    }
}

