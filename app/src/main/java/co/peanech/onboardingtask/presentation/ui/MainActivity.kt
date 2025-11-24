package co.peanech.onboardingtask.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.peanech.onboardingtask.presentation.ui.screens.HomeScreen
import co.peanech.onboardingtask.presentation.ui.screens.OnboardingScreen
import co.peanech.onboardingtask.presentation.ui.screens.RecommendedListScreen
import co.peanech.onboardingtask.presentation.ui.screens.TaskDetailsScreen
import co.peanech.onboardingtask.presentation.ui.theme.OnboardingTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnboardingTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "onboarding") {
                        composable("onboarding") {
                            OnboardingScreen(
                                onNavigateToLogin = { navController.navigate("home") }
                            )
                        }
                        composable("home") {
                            HomeScreen(
                                onNavigateToTaskDetails = { taskId -> navController.navigate("task_details/$taskId") }
                            )
                        }
                        composable("task_details/{taskId}") { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
                            TaskDetailsScreen(taskId = taskId)
                        }
                        composable("recommended") {
                            RecommendedListScreen()
                        }
                    }
                }
            }
        }
    }
}
