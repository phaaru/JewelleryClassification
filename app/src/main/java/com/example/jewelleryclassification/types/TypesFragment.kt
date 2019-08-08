package com.example.jewelleryclassification.types

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.jewelleryclassification.database.JWDatabase
import com.example.jewelleryclassification.databinding.FragmentTypesBinding

class TypesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentTypesBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        val application = requireNotNull(this.activity).application
        val dataSource = JWDatabase.getInstance(application).jwDatabaseDao
        val viewModelFactory = TypesViewModelFactory(dataSource, application)

        val typesViewModel = ViewModelProviders.of(
            this, viewModelFactory).get(TypesViewModel::class.java)

        binding.typesViewModel = typesViewModel

        binding.typesGrid.adapter = TypesGridAdapter(TypesGridAdapter.OnClickListener{
            typesViewModel.displayTypeDetails(it)
        })

        typesViewModel.navigateToSelectedType.observe(this, Observer {
            if (null != it) {
                this.findNavController().navigate(TypesFragmentDirections.actionTypesFragmentToOverviewFragment(it))
                typesViewModel.displayTypeDetailsComplete()
            }
        })


        return binding.root
    }
}