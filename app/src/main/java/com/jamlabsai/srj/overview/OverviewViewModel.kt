package com.jamlabsai.srj.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jamlabsai.srj.database.JWDatabaseDao
import com.jamlabsai.srj.database.JWImage

class OverviewViewModel (private val database: JWDatabaseDao, application: Application, type: String) : AndroidViewModel(application) {

    // viewModelJob allows us to cancel all coroutines started by this ViewModel.
//    private var viewModelJob = Job()
//    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToSelectedProperty = MutableLiveData<String>()
    val navigateToSelectedProperty: LiveData<String>
        get() = _navigateToSelectedProperty

    val jwimages = database.getAllImagesOfType(type)

    fun displayPropertyDetails(jwImage: JWImage) {
        _navigateToSelectedProperty.value = jwImage.path
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

}