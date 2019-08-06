package com.example.jewelleryclassification.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.jewelleryclassification.database.JWDatabase
import com.example.jewelleryclassification.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        val application = requireNotNull(this.activity).application
        val dataSource = JWDatabase.getInstance(application).jwDatabaseDao
        val viewModelFactory = OverviewViewModelFactory(dataSource, application)

        val overviewViewModel = ViewModelProviders.of(
            this, viewModelFactory).get(OverviewViewModel::class.java)

        binding.overviewViewModel = overviewViewModel

        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            overviewViewModel.displayProertyDetails(it)
        })

        return binding.root
    }
}