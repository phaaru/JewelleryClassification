package com.example.jewelleryclassification.detail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.View.*
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.jewelleryclassification.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application


        activity!!.window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN

        activity!!.actionBar?.hide()


        val imagePath = DetailFragmentArgs.fromBundle(arguments!!).selectedImagePath


        val viewModelFactory = DetailViewModelFactory(imagePath, application)


        val binding = FragmentDetailBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        binding.shareFab.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_STREAM, imagePath)
            intent.type = "image/jpeg"
            startActivity(Intent.createChooser(intent, "Share Image"))
        }
        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(DetailViewModel::class.java)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity!!.window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_VISIBLE
    }
}