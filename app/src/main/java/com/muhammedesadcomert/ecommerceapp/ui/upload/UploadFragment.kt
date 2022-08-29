package com.muhammedesadcomert.ecommerceapp.ui.upload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.muhammedesadcomert.ecommerceapp.R
import com.muhammedesadcomert.ecommerceapp.databinding.FragmentUploadBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class UploadFragment : Fragment() {

    private lateinit var binding: FragmentUploadBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private var selectedPicture: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUploadBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        registerLauncher()

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.categories_array,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(R.layout.spinner_item)
            binding.spinner.adapter = it
        }

        binding.imageButton.setOnClickListener {
            selectImage(it)
        }

        binding.uploadButton.setOnClickListener {
            binding.loadingCircle.visibility = View.VISIBLE
            upload()
        }
    }

    private fun upload() {
        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"
        val reference = storage.reference
        val imageReference = reference.child("images/").child(imageName)

        if (selectedPicture != null) {
            imageReference.putFile(selectedPicture!!).addOnSuccessListener {

                val uploadPictureReference = storage.reference.child("images").child(imageName)
                uploadPictureReference.downloadUrl.addOnSuccessListener { Uri ->
                    val imageURL = Uri.toString()
                    val postMap = hashMapOf<String, Any>()
                    postMap.apply {

                        put("imageURL", imageURL)
                        put("userUID", auth.currentUser!!.uid)
                        put("name", binding.uploadProductName.text.toString())
                        put("price", binding.uploadProductPrice.text.toString())
                        put("category", binding.spinner.selectedItem.toString())
                        put("date", Timestamp.now())

                        firestore.collection("Products").add(postMap).addOnSuccessListener {
                            binding.loadingCircle.visibility = View.GONE
//                            finish()
                        }.addOnFailureListener {
                            binding.loadingCircle.visibility = View.GONE
                            Toast.makeText(
                                requireActivity(),
                                it.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }.addOnFailureListener {
                    binding.loadingCircle.visibility = View.GONE
                    Toast.makeText(requireActivity(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                binding.loadingCircle.visibility = View.GONE
                Toast.makeText(requireActivity(), it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun selectImage(view: View) {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Give Permission") {
                        // Request permission
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()
            } else {
                // Request permission
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }

//    private fun registerLauncher() {
//        activityResultLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                if (result.resultCode == RESULT_OK) {
//                    selectedPicture = result.data?.data
//                    selectedPicture.let {
//                        binding.imageButton.setImageURI(it)
//                        binding.selectAnImage.visibility = View.INVISIBLE
//                    }
//                }
//            }
//        permissionLauncher =
//            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
//                if (result) {
//                    // Permission granted
//                    val intentToGallery =
//                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                    activityResultLauncher.launch(intentToGallery)
//                } else {
//                    // Permission denied
//                    Toast.makeText(requireActivity(), "Permission needed!", Toast.LENGTH_LONG).show()
//                }
//            }
//    }
}