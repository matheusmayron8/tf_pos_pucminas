package br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments.login

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.matheus.lima.trabalhofinalpospuc.R
import br.com.matheus.lima.trabalhofinalpospuc.databinding.FragmentSplashBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Check if user logged
        if (FirebaseAuth.getInstance().currentUser == null) {
            launchSignInScreen()
        } else {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    private fun launchSignInScreen() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.ic_login)
            .setTheme(R.style.Theme_LoginScreen)
            .setTosAndPrivacyPolicyUrls(
                getString(R.string.url_google_tems_of_service),
                getString(R.string.url_google_privacy_policy)
            )
            .setIsSmartLockEnabled(false)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        } else {
            // Sign in failed.
            Toast.makeText(requireContext(), getString(R.string.msg_error_login), Toast.LENGTH_LONG)
                .show()
        }
    }
}
