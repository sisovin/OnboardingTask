package co.peanech.onboardingtask.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.peanech.onboardingtask.R

data class TaskUiModel(
    val id: String,
    val title: String,
    val dueDate: String,
    val priority: String,
    val category: Int,
    val isCompleted: Boolean = false
)

@Composable
fun HomeScreen(
    onNavigateToTaskDetails: (String) -> Unit,
    onNavigateToNotifications: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val categories = listOf(
        R.string.category_all,
        R.string.category_work,
        R.string.category_personal,
        R.string.category_urgent
    )
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    // Dummy Data
    val recommendedTasks = listOf(
        TaskUiModel("1", "Prepare Presentation", "Today", "High", R.string.category_work),
        TaskUiModel("2", "Buy Groceries", "Tomorrow", "Medium", R.string.category_personal),
        TaskUiModel("3", "Call Mom", "Sunday", "Low", R.string.category_personal)
    )

    val nextTasks = listOf(
        TaskUiModel("4", "Review Code", "Today", "High", R.string.category_work)
    )

    val taskList = listOf(
        TaskUiModel("5", "Team Meeting", "2:00 PM", "High", R.string.category_work),
        TaskUiModel("6", "Gym", "6:00 PM", "Medium", R.string.category_personal),
        TaskUiModel("7", "Read Book", "9:00 PM", "Low", R.string.category_personal),
        TaskUiModel("8", "Pay Bills", "Tomorrow", "High", R.string.category_urgent)
    )

    val filteredRecommendedTasks = if (selectedCategory == R.string.category_all) {
        recommendedTasks
    } else {
        recommendedTasks.filter { it.category == selectedCategory }
    }

    val filteredNextTasks = if (selectedCategory == R.string.category_all) {
        nextTasks
    } else {
        nextTasks.filter { it.category == selectedCategory }
    }

    val filteredTaskList = if (selectedCategory == R.string.category_all) {
        taskList
    } else {
        taskList.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp), // Add bottom padding for FAB
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Top Bar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.home_greeting, "ស៊ីសូវិន"),
                    style = MaterialTheme.typography.headlineSmall
                )
                IconButton(onClick = onNavigateToNotifications) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = stringResource(R.string.content_description_notifications)
                    )
                }
            }
        }

        // Search Bar
        item {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                placeholder = { Text(stringResource(R.string.search_hint)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F7FA),
                    unfocusedContainerColor = Color(0xFFF5F7FA),
                    disabledContainerColor = Color(0xFFF5F7FA),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true
            )
        }

        // Category Rows
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { categoryRes ->
                    val isSelected = selectedCategory == categoryRes
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = categoryRes },
                        label = { Text(stringResource(categoryRes)) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF007AFF),
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFFF5F7FA),
                            labelColor = Color.Black
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = Color.Transparent,
                            selectedBorderColor = Color.Transparent
                        )
                    )
                }
            }
        }

        // Rec. Tasks
        item {
            Column {
                Text(
                    text = stringResource(R.string.section_recommendation),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredRecommendedTasks) { task ->
                        RecommendedTaskCard(task, onClick = { onNavigateToTaskDetails(task.id) })
                    }
                }
            }
        }

        // Next Tasks
        item {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFF007AFF)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.section_next),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                filteredNextTasks.forEach { task ->
                    TaskItem(task, onClick = { onNavigateToTaskDetails(task.id) })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // Task List
        item {
            Column {
                Text(
                    text = stringResource(R.string.section_task_list),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        items(filteredTaskList) { task ->
            TaskItem(task, onClick = { onNavigateToTaskDetails(task.id) })
        }
    }
}

@Composable
fun RecommendedTaskCard(task: TaskUiModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Surface(
                color = when(task.priority) {
                    "High" -> Color(0xFFFFEBEE) // Light Red
                    "Medium" -> Color(0xFFFFF3E0) // Light Orange
                    else -> Color(0xFFE8F5E9) // Light Green
                },
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = task.priority,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = when(task.priority) {
                        "High" -> Color(0xFFD32F2F)
                        "Medium" -> Color(0xFFEF6C00)
                        else -> Color(0xFF2E7D32)
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Due: ${task.dueDate}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun TaskItem(task: TaskUiModel, onClick: () -> Unit) {
    var checked by remember { mutableStateOf(task.isCompleted) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Flat style for list
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF007AFF),
                    uncheckedColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Due: ${task.dueDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}