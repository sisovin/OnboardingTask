package co.peanech.onboardingtask.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.peanech.onboardingtask.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTaskScreen(
    onNavigateBack: () -> Unit,
    onTaskAdded: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val categories = listOf(
        R.string.category_work,
        R.string.category_personal,
        R.string.category_shopping
    )
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.add_task_title),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.description_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.label_task_title)) },
                placeholder = { Text(stringResource(R.string.hint_task_title)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.label_task_description)) },
                placeholder = { Text(stringResource(R.string.hint_task_description)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Text(
                text = stringResource(R.string.label_task_category),
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { categoryRes ->
                    FilterChip(
                        selected = selectedCategory == categoryRes,
                        onClick = { selectedCategory = categoryRes },
                        label = { Text(stringResource(categoryRes)) }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // TODO: Implement Add Task Logic
                    onTaskAdded()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.btn_add_task))
            }
        }
    }
}
