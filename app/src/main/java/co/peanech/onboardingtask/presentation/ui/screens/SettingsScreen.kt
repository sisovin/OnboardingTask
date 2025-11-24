package co.peanech.onboardingtask.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.peanech.onboardingtask.R
import co.peanech.onboardingtask.presentation.ui.theme.OceanBlue
import co.peanech.onboardingtask.presentation.ui.theme.OffWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    var isDarkMode by remember { mutableStateOf(true) }
    var areNotificationsEnabled by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings_title),
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // General Section
            SettingsSection(title = "General") {
                SettingsItemClickable(
                    icon = Icons.Default.Info,
                    title = stringResource(R.string.settings_language),
                    subtitle = "English",
                    onClick = { /* TODO */ }
                )
                HorizontalDivider(color = Color.Gray, thickness = 0.5.dp)
                SettingsItemToggle(
                    icon = Icons.Default.Settings,
                    title = stringResource(R.string.settings_theme),
                    isChecked = isDarkMode,
                    onCheckedChange = { isDarkMode = it }
                )
            }

            // Notifications Section
            SettingsSection(title = stringResource(R.string.settings_notifications)) {
                SettingsItemToggle(
                    icon = Icons.Default.Notifications,
                    title = stringResource(R.string.settings_notifications),
                    isChecked = areNotificationsEnabled,
                    onCheckedChange = { areNotificationsEnabled = it }
                )
            }

            // Privacy & Security Section
            SettingsSection(title = "Privacy & Security") {
                SettingsItemClickable(
                    icon = Icons.Default.Lock,
                    title = "Change Password",
                    onClick = { /* TODO */ }
                )
            }

            // About Section
            SettingsSection(title = stringResource(R.string.settings_about)) {
                SettingsItemClickable(
                    icon = Icons.Default.Info,
                    title = stringResource(R.string.settings_about),
                    onClick = { /* TODO */ }
                )
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = OffWhite,
            modifier = Modifier.padding(start = 8.dp)
        )
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1C1C1E)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun SettingsItemClickable(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(text = title, color = OffWhite) },
        supportingContent = subtitle?.let { { Text(text = it, color = Color.Gray) } },
        leadingContent = {
            Icon(
                imageVector = icon,
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
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Composable
fun SettingsItemToggle(
    icon: ImageVector,
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = { Text(text = title, color = OffWhite) },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = OffWhite
            )
        },
        trailingContent = {
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = OceanBlue,
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.Transparent,
                    uncheckedBorderColor = Color.Gray
                )
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        )
    )
}
