package com.marketiq.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.marketiq.app.ui.theme.*
import com.marketiq.app.viewmodel.*
import java.text.NumberFormat
import java.util.Locale

@Composable
fun WelcomeScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    onLaunchWorkspace: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = CorporateIndigo,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.size(64.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Filled.Analytics,
                    contentDescription = "MarketiQ Logo",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(34.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "MarketiQ", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Data-Driven Campaign & Lead Optimization",
            style = MaterialTheme.typography.bodyMedium,
            color = CorporateIndigo
        )

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SlateSurface),
            border = BorderStroke(1.dp, SlateOutline)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = "Marketer Sign-In", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = { Text("Work Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onLaunchWorkspace,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Launch Workspace", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(
    uiState: MarketiQUiState,
    onNavigateToCampaigns: () -> Unit,
    onNavigateToReports: () -> Unit
) {
    val currency = remember { NumberFormat.getCurrencyInstance(Locale.US).apply { maximumFractionDigits = 0 } }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(text = "Business Dashboard", style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "Signed in as ${uiState.marketerEmail}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                KpiCard("Total Ad Spend", currency.format(uiState.totalAdSpend), Modifier.weight(1f))
                KpiCard("Active Leads", uiState.activeLeads.toString(), Modifier.weight(1f))
                KpiCard("Avg. Conversion", "${uiState.avgConversionRate}%", Modifier.weight(1f))
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Active Channels", style = MaterialTheme.typography.titleMedium)
        }

        items(uiState.channelSummaries) { summary ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SlateSurface),
                border = BorderStroke(1.dp, SlateOutline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(summary.channel.displayName, style = MaterialTheme.typography.titleMedium)
                        Text(
                            "${summary.campaignCount} campaigns",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(currency.format(summary.totalBudget), fontWeight = FontWeight.SemiBold)
                        Text("${summary.totalLeads} leads", color = CorporateIndigo)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onNavigateToCampaigns,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Log Campaign")
                }
                OutlinedButton(
                    onClick = onNavigateToReports,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Recommendations")
                }
            }
        }
    }
}

@Composable
private fun KpiCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SlateSurface),
        border = BorderStroke(1.dp, SlateOutline)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = SlateTextSecondary)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignManagerScreen(
    uiState: MarketiQUiState,
    onAddCampaign: (String, MarketingChannel, Int, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedChannel by remember { mutableStateOf(MarketingChannel.LinkedIn) }
    var channelMenuExpanded by remember { mutableStateOf(false) }
    var budgetText by remember { mutableStateOf("") }
    var leadsText by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(text = "Campaign Manager", style = MaterialTheme.typography.headlineMedium)
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SlateSurface),
                border = BorderStroke(1.dp, SlateOutline)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Add New Campaign", style = MaterialTheme.typography.titleMedium)

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Campaign Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    ExposedDropdownMenuBox(
                        expanded = channelMenuExpanded,
                        onExpandedChange = { channelMenuExpanded = !channelMenuExpanded }
                    ) {
                        OutlinedTextField(
                            value = selectedChannel.displayName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Channel") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = channelMenuExpanded) },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = channelMenuExpanded,
                            onDismissRequest = { channelMenuExpanded = false }
                        ) {
                            MarketingChannel.entries.forEach { ch ->
                                DropdownMenuItem(
                                    text = { Text(ch.displayName) },
                                    onClick = {
                                        selectedChannel = ch
                                        channelMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = budgetText,
                            onValueChange = { budgetText = it },
                            label = { Text("Budget ($)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = leadsText,
                            onValueChange = { leadsText = it },
                            label = { Text("Leads Generated") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Button(
                        onClick = {
                            val budget = budgetText.toIntOrNull() ?: 0
                            val leads = leadsText.toIntOrNull() ?: 0
                            if (name.isNotBlank() && budget > 0 && leads >= 0) {
                                onAddCampaign(name, selectedChannel, budget, leads)
                                name = ""
                                budgetText = ""
                                leadsText = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add Campaign")
                    }
                }
            }
        }

        items(uiState.campaigns, key = { it.id }) { campaign ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SlateSurface),
                border = BorderStroke(1.dp, SlateOutline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(campaign.name, style = MaterialTheme.typography.titleMedium)
                        Text(
                            "${campaign.channel.displayName} · ${campaign.status.name}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("$${campaign.budget}", fontWeight = FontWeight.SemiBold)
                        Text("${campaign.leads} leads", color = CorporateIndigo)
                    }
                }
            }
        }
    }
}

@Composable
fun ReportsScreen(uiState: MarketiQUiState) {
    val recommendations = listOf(
        "Reallocate 15% budget from low-converting display ads to LinkedIn B2B.",
        "Scale Newsletter Drip sequences where Cost Per Lead remains lowest across channels.",
        "Refine Google Search Retargeting negative keywords to lower acquisition cost."
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(text = "Reports & Recommendations", style = MaterialTheme.typography.headlineMedium)
        }

        items(uiState.campaignReports, key = { it.campaign.id }) { report ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SlateSurface),
                border = BorderStroke(1.dp, SlateOutline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(report.campaign.name, style = MaterialTheme.typography.titleMedium)
                        Text(report.campaign.channel.displayName, style = MaterialTheme.typography.bodyMedium)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            String.format(Locale.US, "CPL: $%.2f", report.costPerLead),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "${report.roiMultiple}x · ${report.roiRating}",
                            color = if (report.roiRating == "High ROI") EmeraldPositive else CorporateIndigo
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
            Text("Actionable Budget Recommendations", style = MaterialTheme.typography.titleMedium)
        }

        items(recommendations.withIndex().toList()) { (idx, rec) ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SlateSurface),
                border = BorderStroke(1.dp, SlateOutline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "0${idx + 1}. Optimization Action",
                        style = MaterialTheme.typography.labelMedium,
                        color = CorporateIndigo
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(rec, style = MaterialTheme.typography.bodyMedium, color = SlateTextPrimary)
                }
            }
        }

        item {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SlateSurfaceVariant,
                border = BorderStroke(1.dp, SlateOutline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Filled.Info, contentDescription = null, tint = CorporateIndigo)
                    Text(
                        text = "Note: Displayed metrics and recommendations are generated from sample prototype data.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
