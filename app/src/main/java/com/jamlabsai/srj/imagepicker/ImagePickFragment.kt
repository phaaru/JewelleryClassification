package com.jamlabsai.srj.imagepicker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jamlabsai.srj.database.JWDatabase
import com.jamlabsai.srj.databinding.FragmentImagePickBinding

class ImagePickFragment : Fragment()  {

    private val SELECT_PICTURES = 1
    private lateinit var imagePickViewModel : ImagePickViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentImagePickBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        val dataSource = JWDatabase.getInstance(application).jwDatabaseDao
        val viewModelFactory = ImagePickViewModelFactory(dataSource, application)

        (activity as AppCompatActivity).supportActionBar?.title = "Pick & Classify"

        imagePickViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImagePickViewModel::class.java)

        binding.imagePickViewModel = imagePickViewModel

        binding.setLifecycleOwner(this)

        binding.greatButton.setOnClickListener {
//            Can use either of the intents, the second one provides flexibility in choosing ImagePicker
//            val intent = Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*" //allows any image file type. Change * to specific extension to limit it
            //**These following line is the important one!
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        }

        binding.predButton.setOnClickListener{
            imagePickViewModel.startPredictions(context!!)
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Pick & Classify"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SELECT_PICTURES) {
            imagePickViewModel.returnDataFromPicker(resultCode, data)
        }
    }
}