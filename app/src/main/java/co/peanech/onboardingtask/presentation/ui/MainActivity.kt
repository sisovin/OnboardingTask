package co.peanech.onboardingtask.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.peanech.onboardingtask.R
import co.peanech.onboardingtask.presentation.ui.screens.AddNewTaskScreen
import co.peanech.onboardingtask.presentation.ui.screens.HistoryScreen
import co.peanech.onboardingtask.presentation.ui.screens.HomeScreen
import co.peanech.onboardingtask.presentation.ui.screens.LoginScreen
import co.peanech.onboardingtask.presentation.ui.screens.NotificationsScreen
import co.peanech.onboardingtask.presentation.ui.screens.OnboardingScreen
import co.peanech.onboardingtask.presentation.ui.screens.ProfileScreen
import co.peanech.onboardingtask.presentation.ui.screens.RecommendedListScreen
import co.peanech.onboardingtask.presentation.ui.screens.SettingsScreen
import co.peanech.onboardingtask.presentation.ui.screens.SignupScreen
import co.peanech.onboardingtask.presentation.ui.screens.TaskDetailsScreen
import co.peanech.onboardingtask.presentation.ui.theme.OnboardingTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnboardingTaskTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val showBottomBar = currentRoute in listOf("home", "history", "profile", "settings")

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(
                                currentRoute = currentRoute,
                                onNavigate = { route ->
                                    navController.navigate(route) {
                                        popUpTo("home") {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    },
                    floatingActionButton = {
                        if (showBottomBar) {
                            FloatingActionButton(
                                onClick = { navController.navigate("add_task") },
                                containerColor = Color(0xFF007AFF),
                                contentColor = Color.White,
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(64.dp)
                                    .offset(y = 50.dp) // Sits (10/90) slightly higher than the bar
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = stringResource(R.string.add_task_title),
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "onboarding",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable("onboarding") {
                            OnboardingScreen(
                                onNavigateToSignup = { navController.navigate("signup") },
                                onNavigateToLogin = { navController.navigate("login") }
                            )
                        }
                        composable("login") {
                            LoginScreen(
                                onNavigateToHome = {
                                    navController.navigate("home") {
                                        popUpTo("onboarding") { inclusive = true }
                                    }
                                },
                                onNavigateToSignup = { navController.navigate("signup") }
                            )
                        }
                        composable("signup") {
                            SignupScreen(
                                onNavigateToHome = {
                                    navController.navigate("home") {
                                        popUpTo("onboarding") { inclusive = true }
                                    }
                                },
                                onNavigateToLogin = { navController.popBackStack() }
                            )
                        }
                        composable("home") {
                            HomeScreen(
                                onNavigateToTaskDetails = { taskId -> navController.navigate("task_details/$taskId") },
                                onNavigateToNotifications = { navController.navigate("notifications") },
                                onNavigateToRecommended = { navController.navigate("recommended") }
                            )
                        }
                        composable("notifications") {
                            NotificationsScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("profile") {
                            ProfileScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("history") {
                            HistoryScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("settings") {
                            SettingsScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("add_task") {
                            AddNewTaskScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onTaskAdded = { navController.popBackStack() }
                            )
                        }
                        composable("task_details/{taskId}") { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
                            TaskDetailsScreen(
                                taskId = taskId,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("recommended") {
                            RecommendedListScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onTaskClick = { taskId ->
                                    navController.navigate("task_details/$taskId")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    Surface(
        color = Color.Black,
        shadowElevation = 16.dp,
        modifier = Modifier.shadow(16.dp)
    ) {
        NavigationBar(
            containerColor = Color.Black,
            contentColor = Color.White,
            tonalElevation = 0.dp,
            modifier = Modifier.height(80.dp)
        ) {
            val items = listOf(
                BottomNavItem("home", R.string.nav_home, Icons.Default.Home),
                BottomNavItem("history", R.string.nav_history, Icons.Default.DateRange),
                BottomNavItem("placeholder", 0, Icons.Default.Add), // Placeholder for FAB
                BottomNavItem("profile", R.string.nav_profile, Icons.Default.Person),
                BottomNavItem("settings", R.string.nav_settings, Icons.Default.Settings)
            )

            items.forEachIndexed { index, item ->
                if (index == 2) {
                    // Spacer for FAB
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Box(modifier = Modifier.size(24.dp)) },
                        enabled = false,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                } else {
                    val isSelected = currentRoute == item.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { onNavigate(item.route) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(item.labelRes)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(item.labelRes),
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF007AFF),
                            selectedTextColor = Color(0xFF007AFF),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color.Transparent // Remove pill background
                        )
                    )
                }
            }
        }
    }
}

data class BottomNavItem(
    val route: String,
    val labelRes: Int,
    val icon: ImageVector
)
