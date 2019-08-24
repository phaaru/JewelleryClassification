package com.jamlabsai.srj.overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jamlabsai.srj.database.JWDatabaseDao

class OverviewViewModelFactory(
    private val datasource: JWDatabaseDao,
    private val application: Application,
    private val type: String) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel(datasource, application, type) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}