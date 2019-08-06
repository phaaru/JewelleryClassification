package com.example.jewelleryclassification.imagepicker

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.jewelleryclassification.FilePickUtils
import com.example.jewelleryclassification.database.JWDatabaseDao
import com.example.jewelleryclassification.database.JWImage
import com.example.jewelleryclassification.network.PredApi
import kotlinx.coroutines.*
import java.io.File
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImagePickViewModel(private val database: JWDatabaseDao, application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private suspend fun insert(jwImage: JWImage) {
        withContext(Dispatchers.IO) {
            database.insert(jwImage)
        }
    }

    // Returns ImageUri from ImagePicker Intent
    fun returnDataFromPicker(resultCode: Int, data: Intent?) {
        coroutineScope.launch {
            if (resultCode == Activity.RESULT_OK) {
                Log.v("MyApp 1", resultCode.toString())
                val d = data?.clipData
                val d2 = data?.data
//              In case more than 1 image is selected in picker
                if (d != null) {
                    val count = d.itemCount //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (i in 1..count) {
                        val imageUri = d.getItemAt(i - 1).uri
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        val uri = FilePickUtils.getSmartFilePath(getApplication(), imageUri)
                        Log.v("MyApp 2", uri!!)
                        insert(JWImage(path = uri!!))
                        getJWPrediction(uri!!)
                    }
//              In case only 1 image is selected in picker
                } else if (d2 != null) {
                    val imagePath = d2
                    val uri = FilePickUtils.getSmartFilePath(getApplication(), imagePath)
                    Log.v("MyApp", uri!!)
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                    insert(JWImage(path = uri!!))
                    // The Image is POSTed to the server and a response is expected, it is parsed in this function itself, the time difference between the start of this function and its end is how much time it takes to get a prediction
                    getJWPrediction(uri!!)
                }
            }
        }
    }

    private fun getJWPrediction(filePath: String) {
        coroutineScope.launch {
            val file = File(filePath)
            Log.v("upload", "Filename $file")
            val mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val fileToUpload = MultipartBody.Part.createFormData("file", file.name, mFile)
            Log.v("mFile", mFile.toString())
            PredApi.retrofitService.getPredictionAndUpload(fileToUpload).enqueue(object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.d("YO", "Error " + t.message)
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Log.d("OK", "Response " + response.raw().message())
                    Log.d("YO", "Response " + response.body())
                }
            })
        }
    }
}