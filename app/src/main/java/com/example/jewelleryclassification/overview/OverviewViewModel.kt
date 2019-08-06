package com.example.jewelleryclassification.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jewelleryclassification.database.JWDatabaseDao
import com.example.jewelleryclassification.database.JWImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class OverviewViewModel (private val database: JWDatabaseDao, application: Application) : AndroidViewModel(application) {

    // viewModelJob allows us to cancel all coroutines started by this ViewModel.
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToSelectedProperty = MutableLiveData<JWImage>()
    val navigateToSelectedProperty: LiveData<JWImage>
        get() = _navigateToSelectedProperty

    val jwimages = database.getAllImages()

    fun displayProertyDetails(jwImage: JWImage) {
        _navigateToSelectedProperty.value = jwImage
    }

}