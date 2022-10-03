package br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.matheus.lima.trabalhofinalpospuc.R
import br.com.matheus.lima.trabalhofinalpospuc.databinding.FragmentOnboardingSecondBinding

class OnboardingSecondFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding_second, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNext.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingSecondFragment_to_onboardingThirdFragment)
        }
    }
}
