package co.peanech.onboardingtask.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import co.peanech.onboardingtask.presentation.ui.theme.OceanBlue
import co.peanech.onboardingtask.presentation.ui.theme.OffWhite

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
        R.string.category_urgent,
        R.string.category_shopping
    )
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val priorities = listOf("High", "Medium", "Low")
    var selectedPriority by remember { mutableStateOf("Medium") }

    // Dummy state for Date/Time (In real app, use DatePicker/TimePicker)
    var selectedDate by remember { mutableStateOf("Nov 24, 2025") }
    var selectedTime by remember { mutableStateOf("10:00 AM") }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.add_task_title),
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
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Transparent) // Ensure background doesn't block
            ) {
                Button(
                    onClick = { onTaskAdded() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OceanBlue
                    )
                ) {
                    Text(
                        text = stringResource(R.string.btn_add_task),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Title Section
            InputSection(title = stringResource(R.string.label_task_title)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text(stringResource(R.string.hint_task_title), color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                        focusedBorderColor = OceanBlue,
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = OffWhite,
                        unfocusedTextColor = OffWhite,
                        cursorColor = OceanBlue
                    ),
                    singleLine = true
                )
            }

            // Category Section
            InputSection(title = stringResource(R.string.label_task_category)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.forEach { categoryRes ->
                        val isSelected = selectedCategory == categoryRes
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = categoryRes },
                            label = { Text(stringResource(categoryRes)) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = OceanBlue,
                                selectedLabelColor = Color.White,
                                containerColor = Color.Black,
                                labelColor = OffWhite
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) Color.Transparent else Color.Gray,
                                selectedBorderColor = Color.Transparent
                            )
                        )
                    }
                }
            }

            // Date & Time Section
            InputSection(title = stringResource(R.string.label_task_date)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DateTimePickerBox(
                        text = selectedDate,
                        icon = Icons.Default.DateRange,
                        modifier = Modifier.weight(1f),
                        onClick = { /* Open Date Picker */ }
                    )
                    DateTimePickerBox(
                        text = selectedTime,
                        icon = Icons.Default.Notifications,
                        modifier = Modifier.weight(1f),
                        onClick = { /* Open Time Picker */ }
                    )
                }
            }

            // Priority Section
            InputSection(title = stringResource(R.string.label_task_priority)) {
                PrioritySelectionRow(
                    selectedPriority = selectedPriority,
                    onPrioritySelected = { selectedPriority = it }
                )
            }

            // Description Section
            InputSection(title = stringResource(R.string.label_task_description)) {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text(stringResource(R.string.hint_task_description), color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                        focusedBorderColor = OceanBlue,
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = OffWhite,
                        unfocusedTextColor = OffWhite,
                        cursorColor = OceanBlue
                    )
                )
            }
            
            // Spacer for bottom bar
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun InputSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = OffWhite
        )
        content()
    }
}

@Composable
fun DateTimePickerBox(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black)
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = OffWhite,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = OffWhite
        )
    }
}

@Composable
fun PrioritySelectionRow(
    selectedPriority: String,
    onPrioritySelected: (String) -> Unit
) {
    val priorities = listOf(
        Triple("High", Color(0xFFFFEBEE), Color(0xFFD32F2F)),
        Triple("Medium", Color(0xFFFFF3E0), Color(0xFFEF6C00)),
        Triple("Low", Color(0xFFE8F5E9), Color(0xFF2E7D32))
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        priorities.forEach { (priority, bgColor, textColor) ->
            val isSelected = selectedPriority == priority
            val labelRes = when(priority) {
                "High" -> R.string.priority_high
                "Medium" -> R.string.priority_medium
                else -> R.string.priority_low
            }
            
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) bgColor else Color.Black)
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) textColor else Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onPrioritySelected(priority) }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(labelRes),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) textColor else OffWhite
                )
            }
        }
    }
}

