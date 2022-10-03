package br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.matheus.lima.trabalhofinalpospuc.R
import br.com.matheus.lima.trabalhofinalpospuc.databinding.FragmentViewMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

class ViewMapsFragment : Fragment() {

    private val navArgs: ViewMapsFragmentArgs by navArgs()
    private lateinit var binding: FragmentViewMapsBinding

    private val callback = OnMapReadyCallback { googleMap ->
        val latLng = navArgs.latLng
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        googleMap.addMarker(MarkerOptions().position(latLng))
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                14f
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_maps, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.btnViewMapBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
