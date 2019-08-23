package com.example.jewelleryclassification.network

import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

//private const val BASE_URL = "http://ec2-3-14-11-91.us-east-2.compute.amazonaws.com:80/"


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
    private const val BASE_URL = "http://10.0.2.2:5000/"
    // Creating Retrofit object
    fun makePredApiService(): PredApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build().create(PredApiService::class.java)
    }
}

