package br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.matheus.lima.trabalhofinalpospuc.R
import br.com.matheus.lima.trabalhofinalpospuc.databinding.FragmentViewReportsDetailBinding
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.MyReport
import br.com.matheus.lima.trabalhofinalpospuc.utils.MaskEditUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.lang.Exception

class ViewReportsDetailFragment : Fragment() {

    private lateinit var binding: FragmentViewReportsDetailBinding
    private val args: ViewReportsDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_view_reports_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureLayout()
        configureListeners()
    }

    private fun configureLayout() {
        val report = args.report
        binding.report = report
        binding.tilDetailReportLocation.editText?.setText("${report.latitude},\n${report.longitude}")
        loadImages(report)

        binding.tilDetailReportCpf.editText?.apply {
            addTextChangedListener(MaskEditUtil.mask(this, MaskEditUtil.FORMAT_CPF))
        }
    }

    private fun configureListeners() {
        binding.btnBackToHome.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnViewReportLocation.setOnClickListener {
            val latLng = LatLng(args.report.latitude, args.report.longitude)
            findNavController().navigate(
                ViewReportsDetailFragmentDirections.actionViewReportsDetailFragmentToViewMapsFragment(
                    latLng
                )
            )
        }
    }

    private fun getImageView(index: Int): ImageView? {
        return when (index) {
            0 -> {
                binding.ivDetailFirstImage.visibility = View.VISIBLE
                binding.ivDetailFirstImage
            }
            1 -> {
                binding.ivDetailSecondImage.visibility = View.VISIBLE
                binding.ivDetailSecondImage
            }
            2 -> {
                binding.ivDetailThirdImage.visibility = View.VISIBLE
                binding.ivDetailThirdImage
            }
            else -> null
        }
    }

    private fun hideImage(index: Int) {
        when (index) {
            0 -> {
                binding.ivDetailFirstImage.visibility = View.GONE
            }
            1 -> {
                binding.ivDetailSecondImage.visibility = View.GONE
            }
            2 -> {
                binding.ivDetailThirdImage.visibility = View.GONE
            }
        }
    }

    private fun loadImages(report: MyReport) {
        startPhotoShimmerLoading()
        report.photos.forEachIndexed { index, url ->
            try {
                val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(url)
                val localFile = File.createTempFile("images", "jpg")
                storageRef.getFile(localFile).addOnSuccessListener {
                    val imageView = getImageView(index)
                    imageView?.let {
                        Glide.with(this)
                            .load(localFile)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    if (index == report.photos.size - 1) {
                                        stopPhotoShimmerLoading()
                                    }
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    if (index == report.photos.size - 1) {
                                        stopPhotoShimmerLoading()
                                    }
                                    return false
                                }
                            })
                            .centerCrop()
                            .into(it)
                    }
                }.addOnFailureListener {
                    hideImage(index)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_to_loading_images),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_to_loading_images),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun startPhotoShimmerLoading() {
        binding.hsvPhotos.visibility = View.GONE
        binding.shimmerDetailViewContainer.visibility = View.VISIBLE
        binding.shimmerDetailViewContainer.startShimmer()
    }

    private fun stopPhotoShimmerLoading() {
        binding.hsvPhotos.visibility = View.VISIBLE
        binding.shimmerDetailViewContainer.visibility = View.GONE
        binding.shimmerDetailViewContainer.stopShimmer()
    }
}
