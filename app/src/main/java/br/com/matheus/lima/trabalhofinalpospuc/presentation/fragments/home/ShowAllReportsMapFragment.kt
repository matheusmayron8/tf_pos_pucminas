package br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.matheus.lima.trabalhofinalpospuc.R
import br.com.matheus.lima.trabalhofinalpospuc.constants.ReportStatusEnum
import br.com.matheus.lima.trabalhofinalpospuc.databinding.FragmentShowAllReportsMapBinding
import br.com.matheus.lima.trabalhofinalpospuc.presentation.viewmodels.ReportsViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowAllReportsMapFragment : Fragment() {

    val reportsViewModel: ReportsViewModel by viewModel()
    private lateinit var mMap: GoogleMap
    private var mMapIsReady = false
    private lateinit var binding: FragmentShowAllReportsMapBinding

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMapIsReady = true
        reportsViewModel.getAllReports()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_show_all_reports_map,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.btnMapAllBack.setOnClickListener {
            findNavController().popBackStack()
        }

        reportsViewModel.listOfReports.observe(viewLifecycleOwner) { list ->
            if (!mMapIsReady) return@observe

            list.forEach { report ->
                setMarker(report.latitude, report.longitude, report.status)
            }

            if (list.isNotEmpty()) {
                val firstLatLng = LatLng(list.first().latitude, list.first().longitude)
                mMap.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(
                            firstLatLng,
                            14f
                        )
                )
            }
        }
    }

    private fun setMarker(latitude: Double, longitude: Double, status: ReportStatusEnum) {
        val latLng = LatLng(latitude, longitude)

        if (!mMapIsReady) return

        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .icon(getMarkerIconByEnum(status))

        )
    }

    private fun getMarkerIconByEnum(status: ReportStatusEnum): BitmapDescriptor {
        val iconColor = when (status) {
            ReportStatusEnum.PENDING -> "#CD9250"
            ReportStatusEnum.IN_PROGRESS -> "#316DC0"
            ReportStatusEnum.FINISHED -> "#14AE67"
            ReportStatusEnum.DENIED -> "#DA2C2C"
        }
        val hsv = FloatArray(3)
        Color.colorToHSV(Color.parseColor(iconColor), hsv)
        return BitmapDescriptorFactory.defaultMarker(hsv[0])
    }
}
