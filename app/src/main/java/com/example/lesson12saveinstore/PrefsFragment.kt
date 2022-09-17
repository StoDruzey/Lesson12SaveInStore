package com.example.lesson12saveinstore

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.lesson12saveinstore.databinding.FragmentPrefsBinding
import java.io.IOException
import java.util.*

class PrefsFragment() : Fragment() {
    private var _binding: FragmentPrefsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val uploadImageLauncher = registerForActivityResult( //create launcher for uploading photos from camera
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        checkNotNull(bitmap)
        savePhoto(bitmap)
        binding.imageView.setImageBitmap(bitmap) //got an image in bitmat format
        updateAdapter()
    }

    private val adapter = ImageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPrefsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            button.setOnClickListener {
                uploadImageLauncher.launch(null)
            }
        }
        updateAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun savePhoto(bitmap: Bitmap) {
        val fileName = UUID.randomUUID().toString()
        try {
            requireContext().openFileOutput("$fileName.jpg", Context.MODE_PRIVATE).use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap")
                }
            }
        } catch (e: IOException) {

        }
    }

    private fun updateAdapter() {
        val photos = loadPhotos()
        adapter.submitList(photos)
    }

    private fun loadPhotos(): List<Image> {
        return requireContext()
            .filesDir
            .listFiles()
            ?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }
            ?.map { file ->
                val bytes = file.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                Image(file.name, bitmap)
            }
            ?: emptyList()
    }
}