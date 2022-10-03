package br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments.onboarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.matheus.lima.trabalhofinalpospuc.presentation.fragments.onboarding.ui.theme.TrabalhoFinalPosPucTheme

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrabalhoFinalPosPucTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = "Hello $name!")
    }
}

@Preview()
@Composable
fun DefaultPreview() {
    TrabalhoFinalPosPucTheme {
        Greeting("Android")
    }
}
