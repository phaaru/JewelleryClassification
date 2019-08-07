package com.example.jewelleryclassification.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jewelleryclassification.database.JWImage

class DetailViewModelFactory(
    private val imagePath: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(imagePath, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}