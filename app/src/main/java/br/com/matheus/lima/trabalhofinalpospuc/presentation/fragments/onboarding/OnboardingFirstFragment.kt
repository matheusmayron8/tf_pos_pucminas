package br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.matheus.lima.trabalhofinalpospuc.R
import br.com.matheus.lima.trabalhofinalpospuc.databinding.FragmentOnboardingFirstBinding

class OnboardingFirstFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding_first, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btFirstNext.setOnClickListener {
            navigateToOnboardingSecond()
        }
    }

    private fun navigateToOnboardingSecond() {
        findNavController().navigate(R.id.action_onboardingFirstFragment_to_onboardingSecondFragment)
    }
}
