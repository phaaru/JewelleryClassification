package com.example.jewelleryclassification.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jewelleryclassification.database.JWImage

class DetailViewModel(imagePath: String, app: Application) : AndroidViewModel(app) {
    private val _selectedImagePath = MutableLiveData<String>()
    val selectedImagePath : LiveData<String>
        get() = _selectedImagePath
    init {
        _selectedImagePath.value = imagePath
    }
}