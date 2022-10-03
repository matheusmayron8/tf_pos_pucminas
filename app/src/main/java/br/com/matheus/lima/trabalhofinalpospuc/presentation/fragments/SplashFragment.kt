package br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.matheus.lima.trabalhofinalpospuc.R
import br.com.matheus.lima.trabalhofinalpospuc.constants.Constants
import br.com.matheus.lima.trabalhofinalpospuc.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = requireActivity().getSharedPreferences(
            Constants.SHARED_PREFERENCES_FILE,
            Context.MODE_PRIVATE
        )

        if (preferences.contains(Constants.FIRST_ACCESS_PREF)) {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_onboardingFirstFragment)
        }
    }
}
