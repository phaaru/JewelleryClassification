package com.jamlabsai.srj.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.jamlabsai.srj.database.JWDatabase
import com.jamlabsai.srj.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        val application = requireNotNull(this.activity).application
        val dataSource = JWDatabase.getInstance(application).jwDatabaseDao

        val type = OverviewFragmentArgs.fromBundle(arguments!!).selectedType

        when (type) {
            "0" -> (activity as AppCompatActivity).supportActionBar?.title = "Bangles"
            "1" -> (activity as AppCompatActivity).supportActionBar?.title = "DKP"
            "2" -> (activity as AppCompatActivity).supportActionBar?.title = "Fox Kanthi"
            "3" -> (activity as AppCompatActivity).supportActionBar?.title = "Jhumki"
            "4" -> (activity as AppCompatActivity).supportActionBar?.title = "Ladies Ring"
            "5" -> (activity as AppCompatActivity).supportActionBar?.title = "Mangal Sutra"
            "6" -> (activity as AppCompatActivity).supportActionBar?.title = "Nath"
            "7" -> (activity as AppCompatActivity).supportActionBar?.title = "SKP"
            "8" -> (activity as AppCompatActivity).supportActionBar?.title = "Set"
            "9" -> (activity as AppCompatActivity).supportActionBar?.title = "Thrissur Kerela"
            "10" -> (activity as AppCompatActivity).supportActionBar?.title = "Tika"
            "11" -> (activity as AppCompatActivity).supportActionBar?.title = "Toda"
            else -> (activity as AppCompatActivity).supportActionBar?.title = "Not yet Classified"
        }


        val viewModelFactory = OverviewViewModelFactory(dataSource, application, type)

        val overviewViewModel = ViewModelProviders.of(
            this, viewModelFactory).get(OverviewViewModel::class.java)

        binding.overviewViewModel = overviewViewModel

        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            overviewViewModel.displayPropertyDetails(it)
        })

        overviewViewModel.navigateToSelectedProperty.observe(this, Observer {
            if (null != it) {
                this.findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it))
                overviewViewModel.displayPropertyDetailsComplete()
            }
        })

        return binding.root
    }
}