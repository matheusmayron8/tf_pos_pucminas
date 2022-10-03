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
        return ComposeView(requireContext()).apply {
            setContent {
                OnboardingImage()
            }
        }
    }

    @Preview()
    @Composable
    fun OnboardingImage() {
        Row {
            Box {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.bg_onboarding),
                    contentDescription = ""
                )
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_onboarding_first),
                    contentDescription = ""
                )
            }
            Text(text = "Acompanhe tudo")
            Text(text = "Visualize de forma ráida e simples os problemas reportados na sua cidade")
            Button(onClick = { navigateToOnboardingSecond() }) {
                Text(text = "Avançar")
            }
        }
    }

    private fun navigateToOnboardingSecond() {
        findNavController().navigate(R.id.action_onboardingFirstFragment_to_onboardingSecondFragment)
    }
}
