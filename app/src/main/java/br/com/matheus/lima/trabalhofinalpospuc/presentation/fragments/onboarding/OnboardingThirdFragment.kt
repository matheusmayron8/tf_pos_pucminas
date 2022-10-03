package br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments.onboarding

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
import br.com.matheus.lima.trabalhofinalpospuc.databinding.FragmentOnboardingThirdBinding

class OnboardingThirdFragment : Fragment() {

    lateinit var binding: FragmentOnboardingThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding_third, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btBegin.setOnClickListener {
            markOnboardingAsViewed()
            findNavController().navigate(R.id.action_onboardingThirdFragment_to_loginFragment)
        }
    }

    fun markOnboardingAsViewed() {
        val preferences = requireActivity().getSharedPreferences(
            Constants.SHARED_PREFERENCES_FILE,
            Context.MODE_PRIVATE
        )
        preferences.edit().putBoolean(Constants.FIRST_ACCESS_PREF, true).apply()
    }
}
