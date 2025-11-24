package co.peanech.onboardingtask.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.peanech.onboardingtask.R

data class Task(
    val id: String,
    val name: String,
    val category: Int,
    val isRecommended: Boolean
)

@Composable
fun RecommendedListScreen() {
    val recommendedItems = listOf(
        Task("1", "Item A", R.string.category_work, isRecommended = true),
        Task("2", "Item B", R.string.category_personal, isRecommended = true),
        Task("3", "Item C", R.string.category_work, isRecommended = true),
        Task("4", "Item D", R.string.category_urgent, isRecommended = true)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.section_recommendation), // Updated string resource
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(recommendedItems) { item ->
                TaskItem(
                    task = TaskUiModel(
                        id = item.id,
                        title = item.name,
                        dueDate = "Today", // Dummy date
                        priority = "Medium", // Dummy priority
                        category = item.category
                    ),
                    onClick = { /* Handle Click */ }
                )
            }
        }
    }
}
