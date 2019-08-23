package com.example.jewelleryclassification.imagepicker

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.jewelleryclassification.CompressImage
import com.example.jewelleryclassification.FilePickUtils
import com.example.jewelleryclassification.database.JWDatabaseDao
import com.example.jewelleryclassification.database.JWImage
import com.example.jewelleryclassification.network.PredApi
import kotlinx.coroutines.*
import java.io.File
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.MultipartBody
import kotlinx.serialization.*
import kotlinx.serialization.json.JSON
import retrofit2.HttpException


@Serializable
data class SimpleResponse(val index: String)

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
                        val image = JWImage(path = uri)
                        insert(image)
                    }
//              In case only 1 image is selected in picker
                } else if (d2 != null) {
                    val imagePath = d2
                    val uri = FilePickUtils.getSmartFilePath(getApplication(), imagePath)

                    Log.v("MyApp", uri!!)
                    val image =JWImage(path = uri!!)
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                    insert(image)
                    // The Image is POSTed to the server and a response is expected, it is parsed in this function itself, the time difference between the start of this function and its end is how much time it takes to get a prediction
                }
            }
        }
    }

    fun startPredictions(context: Context){
        GlobalScope.launch {
            val images = database.getListOfType("unclassified")
//            update(getJWPrediction(images[2]))
            if (images.isNotEmpty()){
                for (image in images) {
                    image.path = CompressImage.compressImage(image.path, context)
                    image.type = getJWPrediction(image)
//                    database.insert(image)
                    delay(2000)
                }
            }
        }
    }

    private fun getJWPrediction(image: JWImage) : String {
        coroutineScope.launch {
            val file = File(image.path)
            Log.v("upload", "Filename $file")
            val mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val fileToUpload = MultipartBody.Part.createFormData("file", file.name, mFile)
            Log.v("mFile", mFile.toString())
            val service = PredApi.makePredApiService()
            CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = service.getPredictionAndUpload(fileToUpload)
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.
                            val obj = response.body()
                            if (response.body() != null) {
                                image.type = obj!!.index
                                image.type = response.body()!!.index
                                insert(image)
                            }
                            Log.d("OK", "Response " + response.raw().message())
                            Log.d("YO", "Response " + response.body()?.index + " " + image.imageId + " " + image.type)

                        } else {
                            Log.d("ERROR","Error: ${response.code()}")
                        }
                    } catch (e: HttpException) {
                        Log.d("ERROR","Exception ${e.message}")
                    } catch (e: Throwable) {
                        Log.d("ERROR", "${e.message} Ooops: Something else went wrong")
                    } finally {
                        this.cancel()
                    }
            }
        }
        return image.type
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}