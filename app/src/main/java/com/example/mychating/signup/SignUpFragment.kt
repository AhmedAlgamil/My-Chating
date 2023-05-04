package com.example.mychating.signup

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.mychating.R
import com.example.mychating.base.BaseFragment
import com.example.mychating.databinding.FragmentSignupBinding
import com.example.mychating.databinding.FragmentSignupBinding.inflate
import com.squareup.picasso.Picasso


class SignUpFragment : BaseFragment() {

    private lateinit var binding: FragmentSignupBinding
    private var REQUEST_STORAGE: Int = 1
    private var REQUEST_CAMERA: Int = 2
    private var PICK_IMAGE: Int = 3
    private var TAKE_PHOTO: Int = 3
    private var imageUri: Uri? = null

    private val signUpViewModel: SignUpViewModel by lazy {
        ViewModelProvider(this).get(SignUpViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        //make sure to clear the view model after destroy, as it's a single view model.
//        signUpViewModel.onClear()
    }

    override val _viewModel: SignUpViewModel by lazy {
        ViewModelProvider(this).get(SignUpViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkRequestStorage() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), REQUEST_STORAGE
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun chaeckRequestCamera() {
        requestPermissions(
            arrayOf(
                Manifest.permission.CAMERA
            ), REQUEST_CAMERA
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showDialog() {
        val chooseDialog = AlertDialog.Builder(context)
        with(chooseDialog)
        {
            setTitle(R.string.image_profile)
            setMessage("you can select image from camera or from gallery")
            setPositiveButton(R.string.image) { dialog, which ->
                checkRequestStorage()
            }
            setNegativeButton(R.string.take_photo) { dialog, which ->
                chaeckRequestCamera()
            }
//            setNeutralButton("Maybe", neutralButtonClick)
            show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_STORAGE -> {
                if (requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, PICK_IMAGE)
                }
            }
            REQUEST_CAMERA -> {
                if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED
                ) {
//                    activity?.startActivityForResult(gallery, TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

            imageUri = data?.data
//            binding.imageProfile.setImageURI(imageUri)
            Picasso.get().load(imageUri).into(binding.imageProfile)
//            val imageFile : File(data?.data)
            val imagePath = activity?.applicationContext?.let { getPath(it,imageUri) }
            Log.e("SignUpFragment67","url ${imageUri}")
        }
    }

    override fun onStart() {
        super.onStart()
    }

    fun getPath(context: Context, uri: Uri?): String? {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = context.getContentResolver().query(uri!!, proj, null, null, null)!!
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index: Int = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = inflate(inflater, container, false)
        binding.btnSignUp.setOnClickListener {

        }
        binding.fabSelectImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                showDialog()
            }
        }
        return binding.root
    }

}