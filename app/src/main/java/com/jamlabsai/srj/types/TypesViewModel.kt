package com.jamlabsai.srj.types

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jamlabsai.srj.database.JWDatabaseDao
import com.jamlabsai.srj.database.JWImage

class TypesViewModel (private val database: JWDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _navigateToSelectedType = MutableLiveData<String>()
    val navigateToSelectedType: LiveData<String>
        get() = _navigateToSelectedType

    val jwtypes = database.getAllTypes()

    fun displayTypeDetails(jwImage: JWImage) {
        _navigateToSelectedType.value = jwImage.type
    }

    fun displayTypeDetailsComplete() {
        _navigateToSelectedType.value = null
    }
}