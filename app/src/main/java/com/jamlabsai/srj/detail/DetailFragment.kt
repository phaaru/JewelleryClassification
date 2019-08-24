package com.jamlabsai.srj.detail

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jamlabsai.srj.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application


        activity!!.window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN

        (activity as AppCompatActivity).supportActionBar?.title = "Image"
        (activity as AppCompatActivity).supportActionBar?.hide()

        val imagePath = DetailFragmentArgs.fromBundle(arguments!!).selectedImagePath


        val viewModelFactory = DetailViewModelFactory(imagePath, application)


        val binding = FragmentDetailBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        binding.shareFab.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_STREAM, imagePath.toUri())
            intent.type = "image/*"
            startActivity(Intent.createChooser(intent, "Share Image"))
        }
        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(DetailViewModel::class.java)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity!!.window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_VISIBLE
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}