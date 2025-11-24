package co.peanech.onboardingtask.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.peanech.onboardingtask.R
import co.peanech.onboardingtask.presentation.ui.theme.OceanBlue
import co.peanech.onboardingtask.presentation.ui.theme.OffWhite

// Dummy data model for Recommended Task
data class RecommendedTask(
    val id: String,
    val title: String,
    val reason: String, // e.g., "Due soon"
    val urgencyColor: Color,
    val priority: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedListScreen(
    onNavigateBack: () -> Unit,
    onTaskClick: (String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        R.string.filter_by_priority,
        R.string.filter_by_due_date,
        R.string.filter_ai_picks
    )

    // Dummy Data
    val tasks = remember {
        mutableStateListOf(
            RecommendedTask("1", "Submit Project Report", "Due soon", Color(0xFFFF5252), "High"),
            RecommendedTask("2", "Client Meeting Preparation", "High Priority", Color(0xFFFFAB40), "High"),
            RecommendedTask("3", "Review Design Assets", "AI Pick", Color(0xFF448AFF), "Medium"),
            RecommendedTask("4", "Update Documentation", "Due soon", Color(0xFFFF5252), "High")
        )
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.recommended_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = OffWhite
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.description_back),
                            tint = OffWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Page Title
            Text(
                text = stringResource(R.string.recommended_title),
                style = MaterialTheme.typography.headlineMedium,
                color = OffWhite,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Filter Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Black,
                contentColor = OceanBlue,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = OceanBlue
                    )
                }
            ) {
                tabs.forEachIndexed { index, titleRes ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = stringResource(titleRes),
                                color = if (selectedTab == index) OceanBlue else Color.Gray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Task Cards List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    RecommendedTaskCard(
                        task = task,
                        onClick = { onTaskClick(task.id) },
                        onDismiss = { tasks.remove(task) }
                    )
                }
            }
        }
    }
}

@Composable
fun RecommendedTaskCard(
    task: RecommendedTask,
    onClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E) // Dark card background
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Urgency Indicator & Title
                Row(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(task.urgencyColor, shape = RoundedCornerShape(4.dp))
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = OffWhite,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Dismiss Action
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.dismiss_not_interested),
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Reason
            Text(
                text = task.reason,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Dismiss Text Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.dismiss_not_interested),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.clickable { onDismiss() }
                )
            }
        }
    }
}
