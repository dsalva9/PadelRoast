package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun SettingsScreen(
    currentLocale: String,          // "system", "en", "es", etc.
    onLocaleChanged: (String) -> Unit,
    isProUser: Boolean,
    remainingAiCalls: Int,
    onUpgradeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Header
            Text(
                text = stringResource(com.example.R.string.settings_title),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                color = TextLight
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Configure app parameters & upgrade your bantering power.",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )
        }

        item {
            // Language Selection Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SlateSurfaceVariant, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SlateSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(com.example.R.string.settings_language_title),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                        color = TextLight
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    val locales = listOf(
                        "system" to stringResource(com.example.R.string.settings_language_system),
                        "en" to "English",
                        "es" to "Español",
                        "fr" to "Français",
                        "it" to "Italiano",
                        "nl" to "Nederlands",
                        "sv" to "Svenska"
                    )

                    locales.forEach { (code, name) ->
                        val isSelected = currentLocale == code
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onLocaleChanged(code) }
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = name,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) TextLight else TextMuted
                            )
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = ElectricCyan,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            // Premium Pro Upgrade Card (Hero/Dark Theme)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(HeroCardBg)
                    .border(
                        border = BorderStroke(
                            2.dp,
                            Brush.horizontalGradient(
                                listOf(PadelLime, ElectricCyan)
                            )
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(com.example.R.string.settings_pro_title),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                            color = HeroTextLight
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isProUser) PadelLime else ActiveCoral)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (isProUser) {
                                    stringResource(com.example.R.string.settings_plan_pro)
                                } else {
                                    stringResource(com.example.R.string.settings_plan_free)
                                },
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )
                        }
                    }

                    // Quota Badge
                    Text(
                        text = if (isProUser) {
                            stringResource(com.example.R.string.settings_ai_unlimited)
                        } else {
                            stringResource(
                                com.example.R.string.settings_ai_remaining,
                                remainingAiCalls,
                                3
                            )
                        },
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = HeroTextMuted
                    )

                    // Benefits List
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        BenefitRow(text = stringResource(com.example.R.string.settings_pro_benefit_unlimited))
                        BenefitRow(text = stringResource(com.example.R.string.settings_pro_benefit_savage))
                        BenefitRow(text = stringResource(com.example.R.string.settings_pro_benefit_model))
                    }

                    if (!isProUser) {
                        Button(
                            onClick = onUpgradeClicked,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricCyan,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = stringResource(com.example.R.string.settings_upgrade_btn),
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF1E293B))
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "⚡ PRO UNLOCKED ⚡",
                                color = PadelLime,
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        item {
            // About info
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SlateSurfaceVariant, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SlateSurface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(com.example.R.string.settings_about),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                        color = TextLight,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(com.example.R.string.settings_version, "1.0"),
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(com.example.R.string.settings_made_with),
                        fontSize = 13.sp,
                        color = TextMuted,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp)) // Padding for bottom bar
        }
    }
}

@Composable
fun BenefitRow(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "•",
            color = PadelLime,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            color = HeroTextLight,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
