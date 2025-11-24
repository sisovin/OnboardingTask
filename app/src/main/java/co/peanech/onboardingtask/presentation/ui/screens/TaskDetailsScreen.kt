package co.peanech.onboardingtask.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.peanech.onboardingtask.R

@Composable
fun TaskDetailsScreen(
    taskId: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.task_details_title, taskId),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Description
        Text(text = stringResource(R.string.task_description), style = MaterialTheme.typography.titleMedium)
        Text(
            text = "This is a detailed description of the task. It includes all the necessary information to complete the task.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Date
        Text(text = stringResource(R.string.task_date), style = MaterialTheme.typography.titleMedium)
        Text(text = "2025-11-25", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Status
        Text(text = stringResource(R.string.task_status), style = MaterialTheme.typography.titleMedium)
        Text(text = stringResource(R.string.status_pending), style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.weight(1f))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { /* Handle Edit */ },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.btn_edit))
            }
            Button(
                onClick = { /* Handle Delete */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(text = stringResource(R.string.btn_delete))
            }
        }
    }
}
