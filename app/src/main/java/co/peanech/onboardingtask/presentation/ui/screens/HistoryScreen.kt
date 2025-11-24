package co.peanech.onboardingtask.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.peanech.onboardingtask.R
import co.peanech.onboardingtask.presentation.ui.theme.OffWhite

enum class HistoryStatus {
    COMPLETED,
    MISSED,
    DELETED
}

data class HistoryItemData(
    val id: String,
    val title: String,
    val category: String,
    val duration: String,
    val completionDate: String,
    val status: HistoryStatus
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateBack: () -> Unit
) {
    // Dummy Data
    val allHistoryItems = remember {
        listOf(
            HistoryItemData("1", "Project Alpha Review", "Work", "2h 30m", "24 Nov 2025", HistoryStatus.COMPLETED),
            HistoryItemData("2", "Gym Session", "Personal", "1h 00m", "23 Nov 2025", HistoryStatus.COMPLETED),
            HistoryItemData("3", "Submit Report", "Work", "0m", "22 Nov 2025", HistoryStatus.MISSED),
            HistoryItemData("4", "Buy Groceries", "Shopping", "45m", "21 Nov 2025", HistoryStatus.DELETED),
            HistoryItemData("5", "Team Meeting", "Work", "1h 15m", "20 Nov 2025", HistoryStatus.COMPLETED)
        )
    }

    var selectedFilter by remember { mutableStateOf<HistoryStatus?>(null) }

    val filteredItems = remember(selectedFilter, allHistoryItems) {
        if (selectedFilter == null) {
            allHistoryItems
        } else {
            allHistoryItems.filter { it.status == selectedFilter }
        }
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.history_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = OffWhite
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.description_back),
                            tint = OffWhite
                        )
                    }
                },
                actions = {
                    HistoryFilterButton(
                        selectedFilter = selectedFilter,
                        onFilterSelected = { selectedFilter = it }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        if (filteredItems.isEmpty()) {
            EmptyHistoryState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredItems) { item ->
                    HistoryCard(item = item)
                }
            }
        }
    }
}

@Composable
fun HistoryFilterButton(
    selectedFilter: HistoryStatus?,
    onFilterSelected: (HistoryStatus?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = "Filter",
                tint = if (selectedFilter != null) Color(0xFF007AFF) else OffWhite
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF1C1C1E))
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.filter_all), color = OffWhite) },
                onClick = {
                    onFilterSelected(null)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.filter_completed), color = OffWhite) },
                onClick = {
                    onFilterSelected(HistoryStatus.COMPLETED)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.filter_missed), color = OffWhite) },
                onClick = {
                    onFilterSelected(HistoryStatus.MISSED)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.filter_deleted), color = OffWhite) },
                onClick = {
                    onFilterSelected(HistoryStatus.DELETED)
                    expanded = false
                }
            )
        }
    }
}

@Composable
fun HistoryCard(item: HistoryItemData) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1C1C1E)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusIcon(status = item.status)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = OffWhite
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${item.category} • ${stringResource(R.string.label_duration)}: ${item.duration}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${stringResource(R.string.label_completion_date)}: ${item.completionDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun StatusIcon(status: HistoryStatus) {
    val icon = when (status) {
        HistoryStatus.COMPLETED -> Icons.Default.CheckCircle
        HistoryStatus.MISSED -> Icons.Default.Close // Red X
        HistoryStatus.DELETED -> Icons.Default.Delete
    }

    val color = when (status) {
        HistoryStatus.COMPLETED -> Color(0xFF4CAF50) // Green
        HistoryStatus.MISSED -> Color(0xFFF44336) // Red
        HistoryStatus.DELETED -> Color.Gray
    }

    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = color,
        modifier = Modifier.size(32.dp)
    )
}

@Composable
fun EmptyHistoryState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.List, // Or a dedicated empty icon
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.history_no_data),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }
    }
}
