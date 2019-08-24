package com.jamlabsai.srj.imagepicker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jamlabsai.srj.database.JWDatabaseDao
import java.lang.IllegalArgumentException

class ImagePickViewModelFactory(
    private val datasource: JWDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImagePickViewModel::class.java)) {
            return ImagePickViewModel(datasource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
