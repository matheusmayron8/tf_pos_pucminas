package br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments.home

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import br.com.matheus.lima.trabalhofinalpospuc.R
import br.com.matheus.lima.trabalhofinalpospuc.databinding.FragmentSelectLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class SelectLocationFragment : Fragment() {

    private var latLngSelected: LatLng? = null
    private var mLocationMarker: Marker? = null
    private lateinit var binding: FragmentSelectLocationBinding
    private lateinit var mMap: GoogleMap
    private var mIsMapReady = false
    private var mLocationManager: LocationManager? = null
    private val mLocationListener = LocationListener { location ->
        updateMapCameraToCurrentLocation(location)
    }
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        mMap.setOnMapClickListener { location ->
            latLngSelected = location
            mLocationMarker?.remove()
            mLocationMarker = mMap.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(location.latitude, location.longitude)
                    )
            )
            binding.btnSelectLatlng.visibility = View.VISIBLE
        }
        mIsMapReady = true

        setupLocationListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_location, container, false)
        binding.latlng = latLngSelected
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.btnSelectLatlng.setOnClickListener {
            if (latLngSelected == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.txt_needed_select_location),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                navigateBack()
            }
        }
    }

    private fun navigateBack() {
        setFragmentResult(
            NewReportsFragment.SELECTED_LATLNG,
            bundleOf(
                NewReportsFragment.LATITUDE to (latLngSelected?.latitude ?: 0.0),
                NewReportsFragment.LONGITUDE to (latLngSelected?.longitude ?: 0.0)
            )
        )
        findNavController().popBackStack()
    }

    @SuppressLint("MissingPermission")
    private fun setupLocationListener() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager
            mLocationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 60000, 10f, mLocationListener
            )
            mLocationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                updateMapCameraToCurrentLocation(it)
            }
        }
    }

    private fun updateMapCameraToCurrentLocation(location: Location) {
        if (!mIsMapReady) {
            return
        }
        val currentLatLng = LatLng(location.latitude, location.longitude)
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                currentLatLng,
                19f
            )
        )
    }
}
