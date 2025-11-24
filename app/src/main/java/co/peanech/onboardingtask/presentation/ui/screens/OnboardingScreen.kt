package co.peanech.onboardingtask.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.peanech.onboardingtask.R

@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.onboarding_welcome), style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onNavigateToLogin, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = stringResource(R.string.onboarding_get_started))
        }
    }
}
