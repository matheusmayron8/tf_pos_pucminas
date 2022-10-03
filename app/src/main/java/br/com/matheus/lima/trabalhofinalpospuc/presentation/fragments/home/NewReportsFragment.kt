package br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import br.com.matheus.lima.trabalhofinalpospuc.R
import br.com.matheus.lima.trabalhofinalpospuc.databinding.FragmentNewReportsBinding
import br.com.matheus.lima.trabalhofinalpospuc.presentation.viewmodels.ReportsViewModel
import br.com.matheus.lima.trabalhofinalpospuc.utils.MaskEditUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class NewReportsFragment : Fragment() {

    companion object {
        const val SELECTED_LATLNG = "selected_latlng"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }

    private lateinit var binding: FragmentNewReportsBinding
    private val mReportsViewModel: ReportsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_reports, container, false)
        binding.viewModel = mReportsViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureLayout()
        configureListeners()
        configureObservers()
    }

    private fun configureLayout() {
        binding.tilReportCpf.editText?.apply {
            addTextChangedListener(
                MaskEditUtil.mask(this, MaskEditUtil.FORMAT_CPF)
            )
        }
    }

    private fun configureListeners() {

        setFragmentResultListener(
            SELECTED_LATLNG
        ) { _, result ->
            mReportsViewModel.reportForm.latitude.value = result.getDouble(LATITUDE)
            mReportsViewModel.reportForm.longitude.value = result.getDouble(LONGITUDE)
            mReportsViewModel.reportForm.location.value =
                "${mReportsViewModel.reportForm.latitude.value},\n${mReportsViewModel.reportForm.longitude.value}"
        }

        binding.btnSelectReportLocation.setOnClickListener {
            findNavController().navigate(NewReportsFragmentDirections.actionNewReportsFragmentToSelectLocationFragment())
        }

        binding.ivFirstImage.setOnClickListener {
            dispatchTakePictureFullIntent(REQUEST_TAKE_FIRST_PHOTO)
        }

        binding.ivSecondImage.setOnClickListener {
            dispatchTakePictureFullIntent(REQUEST_TAKE_SECOND_PHOTO)
        }

        binding.ivThirdImage.setOnClickListener {
            dispatchTakePictureFullIntent(REQUEST_TAKE_THIRD_PHOTO)
        }

        binding.btnSendReport.setOnClickListener {
            if (mReportsViewModel.reportForm.validateForm()) {
                mReportsViewModel.postNewReport()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.txt_please_enter_data),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.tilReportLocation.setOnClickListener {
            findNavController().navigate(NewReportsFragmentDirections.actionNewReportsFragmentToSelectLocationFragment())
        }
    }

    private fun configureObservers() {
        mReportsViewModel.successSentReport.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireActivity(),
                getString(R.string.txt_solicitation_sent),
                Toast.LENGTH_LONG
            )
                .show()
            findNavController().popBackStack()
        }

        mReportsViewModel.genericError.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }

        mReportsViewModel.reportForm.photos.observe(viewLifecycleOwner) {

            it.forEachIndexed { index, s ->
                val imageBitmap = BitmapFactory.decodeFile(s)
                when (index) {
                    0 -> {
                        binding.ivFirstImage.setImageBitmap(imageBitmap)
                        binding.ivSecondImage.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.ivSecondImage.setImageBitmap(imageBitmap)
                        binding.ivThirdImage.visibility = View.VISIBLE
                    }
                    2 -> {
                        binding.ivThirdImage.setImageBitmap(imageBitmap)
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            mReportsViewModel.addPhotoOnQueue(currentPhotoPath)
        }
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        activity?.let {
            val storageDir: File? = it.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            ).apply {
                // Save a file: path for use with ACTION_VIEW intents
                currentPhotoPath = absolutePath
            }
        } ?: return null
    }

    private val REQUEST_TAKE_FIRST_PHOTO = 1
    private val REQUEST_TAKE_SECOND_PHOTO = 2
    private val REQUEST_TAKE_THIRD_PHOTO = 3

    private fun dispatchTakePictureFullIntent(action: Int) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            activity?.let {
                takePictureIntent.resolveActivity(it.packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            "com.example.android.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, action)
                    }
                }
            }
        }
    }
}
