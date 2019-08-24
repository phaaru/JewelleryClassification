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

private lateinit var barType : String

class OverviewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        val application = requireNotNull(this.activity).application
        val dataSource = JWDatabase.getInstance(application).jwDatabaseDao

        val type = OverviewFragmentArgs.fromBundle(arguments!!).selectedType

        when (type) {
            "0" -> barType = "Bangles"
            "1" -> barType = "DKP"
            "2" -> barType = "Fox Kanthi"
            "3" -> barType = "Jhumki"
            "4" -> barType = "Ladies Ring"
            "5" -> barType = "Mangal Sutra"
            "6" -> barType = "Nath"
            "7" -> barType = "SKP"
            "8" -> barType = "Set"
            "9" -> barType = "Thrissur Kerela"
            "10" -> barType = "Tika"
            "11" -> barType = "Toda"
            else -> barType= "Not yet Classified"
        }
        (activity as AppCompatActivity).supportActionBar?.title = barType

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

    override fun onResume() {
        (activity as AppCompatActivity).supportActionBar?.title = barType
        super.onResume()
    }
}