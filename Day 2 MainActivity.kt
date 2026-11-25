package com.marketiq.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marketiq.app.ui.screens.*
import com.marketiq.app.ui.theme.MarketiQTheme
import com.marketiq.app.viewmodel.MarketiQScreen
import com.marketiq.app.viewmodel.MarketiQViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarketiQTheme {
                MarketiQApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketiQApp(viewModel: MarketiQViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MarketiQ") }
            )
        },
        bottomBar = {
            NavigationBar {
                MarketiQScreen.entries.forEach { screen ->
                    val icon = when (screen) {
                        MarketiQScreen.Welcome -> Icons.Filled.Home
                        MarketiQScreen.Dashboard -> Icons.Filled.Dashboard
                        MarketiQScreen.Campaigns -> Icons.Filled.Campaign
                        MarketiQScreen.Reports -> Icons.Filled.Assessment
                    }
                    NavigationBarItem(
                        selected = uiState.currentScreen == screen,
                        onClick = { viewModel.navigateTo(screen) },
                        icon = { Icon(icon, contentDescription = screen.label) },
                        label = { Text(screen.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (uiState.currentScreen) {
                MarketiQScreen.Welcome -> WelcomeScreen(
                    email = uiState.marketerEmail,
                    onEmailChange = viewModel::updateEmail,
                    onLaunchWorkspace = viewModel::launchWorkspace
                )
                MarketiQScreen.Dashboard -> DashboardScreen(
                    uiState = uiState,
                    onNavigateToCampaigns = { viewModel.navigateTo(MarketiQScreen.Campaigns) },
                    onNavigateToReports = { viewModel.navigateTo(MarketiQScreen.Reports) }
                )
                MarketiQScreen.Campaigns -> CampaignManagerScreen(
                    uiState = uiState,
                    onAddCampaign = viewModel::addCampaign
                )
                MarketiQScreen.Reports -> ReportsScreen(uiState = uiState)
            }
        }
    }
}
