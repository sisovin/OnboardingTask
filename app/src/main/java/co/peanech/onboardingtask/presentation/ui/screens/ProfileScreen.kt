package co.peanech.onboardingtask.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.peanech.onboardingtask.R
import co.peanech.onboardingtask.presentation.ui.theme.OceanBlue
import co.peanech.onboardingtask.presentation.ui.theme.OffWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Scaffold(
        containerColor = Color.Black,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Header (Identity)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray), // Placeholder background
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    tint = OffWhite
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sok Dara", // Dummy data
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = OffWhite
            )

            Text(
                text = "sok.dara@example.com", // Dummy data
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 2. StatsCard (Metrics)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1C1C1E) // Dark Gray for card
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatItem(
                        count = "12",
                        label = stringResource(R.string.profile_tasks_completed),
                        color = Color(0xFF4CAF50) // Green
                    )
                    
                    VerticalDivider(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp),
                        color = Color.Gray
                    )

                    StatItem(
                        count = "5",
                        label = stringResource(R.string.profile_tasks_pending),
                        color = Color(0xFFFF9800) // Orange
                    )

                    VerticalDivider(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp),
                        color = Color.Gray
                    )

                    StatItem(
                        count = "17",
                        label = stringResource(R.string.profile_total_tasks),
                        color = OceanBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3. ActionList (Navigation/Actions)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1C1C1E))
            ) {
                ListItem(
                    headlineContent = { Text(stringResource(R.string.nav_settings), color = OffWhite) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = OffWhite
                        )
                    },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.clickable(onClick = onNavigateToSettings)
                )

                HorizontalDivider(color = Color.Gray, thickness = 0.5.dp)

                ListItem(
                    headlineContent = { Text(stringResource(R.string.label_logout), color = Color(0xFFFF3B30)) }, // Red
                    leadingContent = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = null,
                            tint = Color(0xFFFF3B30)
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.clickable(onClick = onLogout)
                )
            }
        }
    }
}

@Composable
fun StatItem(
    count: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            modifier = Modifier.width(80.dp), // Limit width for wrapping if needed
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}
