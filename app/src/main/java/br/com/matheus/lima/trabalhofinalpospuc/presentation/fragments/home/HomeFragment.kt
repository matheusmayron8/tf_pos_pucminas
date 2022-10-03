package br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments.home

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.matheus.lima.trabalhofinalpospuc.R
import br.com.matheus.lima.trabalhofinalpospuc.databinding.FragmentHomeBinding
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.User
import br.com.matheus.lima.trabalhofinalpospuc.presentation.adapters.MyReportsListAdapter
import br.com.matheus.lima.trabalhofinalpospuc.presentation.viewmodels.ReportsViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAdapter: MyReportsListAdapter

    private val mReportsViewModel: ReportsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureLayout()
        configureListeners()
        configureObservers()
    }

    override fun onResume() {
        super.onResume()
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            mReportsViewModel.getReportsByUserId(it)
        }
    }

    private fun configureLayout() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            binding.user = User(
                user.displayName ?: "-",
                user.email ?: "-"
            )
            user.photoUrl?.let { photoUri ->
                Glide.with(this)
                    .load(photoUri)
                    .circleCrop()
                    .placeholder(R.drawable.ic_account_placeholder)
                    .into(binding.profileImage)
            }
        }

        mAdapter = MyReportsListAdapter() { report ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToViewReportsDetailFragment(
                    report
                )
            )
        }

        binding.iclHomeMyReports.rvMyReports.apply {
            adapter = mAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }
    }

    private fun configureListeners() {
        binding.btnSignout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        binding.iclHomeMyReports.tvViewAll.setOnClickListener {
            navigateToShowAllReportsMapFragment()
        }

        binding.iclHomeMyReports.fabNewReport.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewReportsFragment())
        }

        binding.iclHomeNoContent.btnViewAllCity.setOnClickListener {
            navigateToShowAllReportsMapFragment()
        }

        binding.iclHomeNoContent.btnReportProblem.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewReportsFragment())
        }
    }

    private fun configureObservers() {
        mReportsViewModel.listOfReports.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.iclHomeMyReports.root.visibility = View.GONE
                binding.iclHomeNoContent.root.visibility = View.VISIBLE
            } else {
                binding.iclHomeMyReports.root.visibility = View.VISIBLE
                binding.iclHomeNoContent.root.visibility = View.GONE
                mAdapter.submitList(it)
            }
        }

        mReportsViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.iclHomeMyReports.root.visibility = View.GONE
                binding.iclHomeNoContent.root.visibility = View.GONE
                binding.shimmerViewContainer.visibility = View.VISIBLE
                binding.shimmerViewContainer.startShimmer()
            } else {
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.visibility = View.GONE
            }
        }
    }

    private fun navigateToShowAllReportsMapFragment() {
        Dexter.withContext(requireContext())
            .withPermission(ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToShowAllReportsMapFragment())
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    alertPermissionDenied()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).check()
    }

    private fun alertPermissionDenied() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.title_required_permissions))
            .setMessage(getString(R.string.msg_required_permissions))
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.txt_fix)
            ) { _, _ ->
                navigateToShowAllReportsMapFragment()
            }
            .setNegativeButton(
                getString(R.string.txt_cancel)
            ) { _, _ ->
                Toast.makeText(
                    requireContext(),
                    getString(R.string.txt_features_disabled),
                    Toast.LENGTH_LONG
                ).show()
            }
            .create()
            .show()
    }
}
