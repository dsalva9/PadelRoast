package com.example.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api.RoastResponseSchema
import com.example.ui.BanterViewModel
import com.example.ui.GenerationState
import com.example.ui.theme.*
import kotlinx.coroutines.launch
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BanterDeckScreen(
    viewModel: BanterViewModel,
    modifier: Modifier = Modifier
) {
    val genState by viewModel.generationState.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (val state = genState) {
            is GenerationState.Idle -> {
                InputFormView(viewModel)
            }
            is GenerationState.Loading -> {
                LoadingSportsView()
            }
            is GenerationState.Success -> {
                key(state.matchId) {
                    RoastCardSuccessView(
                        roast = state.roast,
                        isTemplate = state.isTemplate,
                        viewModel = viewModel,
                        onReset = { viewModel.clearState() }
                    )
                }
            }
            is GenerationState.Error -> {
                ErrorDisplayView(
                    message = state.message,
                    onRetry = { viewModel.clearState() }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InputFormView(viewModel: BanterViewModel) {
    val p1 by viewModel.player1.collectAsState()
    val p2 by viewModel.player2.collectAsState()
    val p3 by viewModel.player3.collectAsState()
    val p4 by viewModel.player4.collectAsState()

    val suggestionsP1 by viewModel.suggestionsP1.collectAsState()
    val suggestionsP2 by viewModel.suggestionsP2.collectAsState()
    val suggestionsP3 by viewModel.suggestionsP3.collectAsState()
    val suggestionsP4 by viewModel.suggestionsP4.collectAsState()

    val s1A by viewModel.set1A.collectAsState()
    val s1B by viewModel.set1B.collectAsState()
    val s2A by viewModel.set2A.collectAsState()
    val s2B by viewModel.set2B.collectAsState()
    val s3A by viewModel.set3A.collectAsState()
    val s3B by viewModel.set3B.collectAsState()

    val tone by viewModel.selectedTone.collectAsState()
    val tags by viewModel.selectedTags.collectAsState()

    val isProUser by viewModel.isProUser.collectAsState()
    val remainingAiCalls by viewModel.remainingAiCalls.collectAsState()
    var showSavageProDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App title header with gradient accent
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(PadelLime)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🔥", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(com.example.R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Black
                        ),
                        color = TextLight
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(com.example.R.string.app_description),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Same Lineup Quick button
        item {
            Button(
                onClick = { viewModel.prefillSameLineup() },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("same_lineup_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SlateSurfaceVariant.copy(alpha = 0.45f),
                    contentColor = TextLight
                ),
                border = BorderStroke(1.2.dp, SlateSurfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(com.example.R.string.same_lineup_btn),
                    modifier = Modifier.size(18.dp),
                    tint = TextLight
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(com.example.R.string.same_lineup_btn),
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                    color = TextLight
                )
            }
        }

        // Player inputs Team Alpha (With Left Accent Bar)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.5.dp, SlateSurfaceVariant, RoundedCornerShape(16.dp))
                    .background(SlateSurface)
            ) {
                // Left dynamic color accent bar
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(6.dp)
                        .background(PadelLime)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(com.example.R.string.team_a),
                        style = MaterialTheme.typography.titleSmall,
                        color = TextLight,
                        fontWeight = FontWeight.Black
                    )

                    // Player 1 AutoSuggest Field
                    InteractivePlayerField(
                        label = stringResource(com.example.R.string.player_label, 1),
                        value = p1,
                        onValueChange = { viewModel.player1.value = it },
                        suggestions = suggestionsP1,
                        onSuggestionSelect = { viewModel.player1.value = it },
                        testTag = "player_1_input"
                    )

                    // Player 2 AutoSuggest Field
                    InteractivePlayerField(
                        label = stringResource(com.example.R.string.player_label, 2),
                        value = p2,
                        onValueChange = { viewModel.player2.value = it },
                        suggestions = suggestionsP2,
                        onSuggestionSelect = { viewModel.player2.value = it },
                        testTag = "player_2_input"
                    )
                }
            }
        }

        // VS divider badge matching stadium layout
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                HorizontalDivider(color = SlateSurfaceVariant, thickness = 1.dp)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(HeroCardBg)
                        .border(1.dp, PadelLime.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "VS",
                        fontWeight = FontWeight.Black,
                        fontSize = 11.sp,
                        color = PadelLime,
                        letterSpacing = 2.sp
                    )
                }
            }
        }

        // Player inputs Team Beta (With Left Accent Bar)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.5.dp, SlateSurfaceVariant, RoundedCornerShape(16.dp))
                    .background(SlateSurface)
            ) {
                // Left dynamic color accent bar
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(6.dp)
                        .background(ElectricCyan)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(com.example.R.string.team_b),
                        style = MaterialTheme.typography.titleSmall,
                        color = TextLight,
                        fontWeight = FontWeight.Black
                    )

                    // Player 3 AutoSuggest Field
                    InteractivePlayerField(
                        label = stringResource(com.example.R.string.player_label, 3),
                        value = p3,
                        onValueChange = { viewModel.player3.value = it },
                        suggestions = suggestionsP3,
                        onSuggestionSelect = { viewModel.player3.value = it },
                        testTag = "player_3_input"
                    )

                    // Player 4 AutoSuggest Field
                    InteractivePlayerField(
                        label = stringResource(com.example.R.string.player_label, 4),
                        value = p4,
                        onValueChange = { viewModel.player4.value = it },
                        suggestions = suggestionsP4,
                        onSuggestionSelect = { viewModel.player4.value = it },
                        testTag = "player_4_input"
                    )
                }
            }
        }

        // Score selectors - Zero Typing Tap box
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.5.dp, SlateSurfaceVariant, RoundedCornerShape(16.dp))
                    .background(SlateSurface)
            ) {
                // Dual dynamic color blend accent bar (Lime matching Team A top, Cyan matching Team B bottom)
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(6.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(PadelLime, ElectricCyan)
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(com.example.R.string.header_score),
                        style = MaterialTheme.typography.titleSmall,
                        color = TextLight,
                        fontWeight = FontWeight.Bold
                    )

                // Set 1 Selector
                ScoreTrackSelector(
                    setName = stringResource(com.example.R.string.set_label_selector, 1),
                    valA = s1A,
                    valB = s1B,
                    onSelectA = { viewModel.set1A.value = it.toString() },
                    onSelectB = { viewModel.set1B.value = it.toString() }
                )

                HorizontalDivider(color = SlateSurfaceVariant, thickness = 1.dp)

                // Set 2 Selector
                ScoreTrackSelector(
                    setName = stringResource(com.example.R.string.set_label_selector, 2),
                    valA = s2A,
                    valB = s2B,
                    onSelectA = { viewModel.set2A.value = it.toString() },
                    onSelectB = { viewModel.set2B.value = it.toString() }
                )

                HorizontalDivider(color = SlateSurfaceVariant, thickness = 1.dp)

                // Set 3 Selector
                ScoreTrackSelector(
                    setName = stringResource(com.example.R.string.set_label_selector_optional),
                    valA = s3A,
                    valB = s3B,
                    onSelectA = { viewModel.set3A.value = it.toString() },
                    onSelectB = { viewModel.set3B.value = it.toString() }
                )
                }
            }
        }

        // Banter Tone toggle selector
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, SlateSurfaceVariant, RoundedCornerShape(16.dp))
                    .background(SlateSurface)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(com.example.R.string.tone_label),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextLight
                )

                if (showSavageProDialog) {
                    AlertDialog(
                        onDismissRequest = { showSavageProDialog = false },
                        title = {
                            Text(
                                text = "💀 " + stringResource(com.example.R.string.tone_savage_pro),
                                fontWeight = FontWeight.Black,
                                color = TextLight
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(com.example.R.string.tone_savage_pro_locked),
                                color = TextMuted,
                                fontSize = 14.sp
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showSavageProDialog = false
                                    viewModel.upgradeToPro()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ElectricCyan)
                            ) {
                                Text(stringResource(com.example.R.string.settings_upgrade_btn), fontWeight = FontWeight.Bold)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showSavageProDialog = false }) {
                                Text("Cancel", color = TextMuted, fontWeight = FontWeight.Bold)
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        containerColor = SlateSurface
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val tones = listOf("Casual", "Balanced", "Savage", "SAVAGE!!")
                    tones.forEach { toneName ->
                        val isSelected = tone == toneName
                        val isLocked = toneName == "SAVAGE!!" && !isProUser
                        
                        val bgColor = if (isSelected) {
                            if (toneName == "Savage") {
                                ActiveCoral
                            } else if (toneName == "SAVAGE!!") {
                                Color.Transparent
                            } else {
                                PadelLime
                            }
                        } else {
                            SlateSurfaceVariant
                        }
                        
                        val contentColor = if (isSelected) {
                            if (toneName == "Savage" || toneName == "SAVAGE!!") Color.White else Color.Black
                        } else {
                            TextMuted
                        }

                        val cellModifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .then(
                                if (isSelected && toneName == "SAVAGE!!") {
                                    Modifier.background(
                                        Brush.horizontalGradient(
                                            colors = listOf(ActiveCoral, Color.Red)
                                        )
                                    )
                                } else if (toneName == "SAVAGE!!") {
                                    Modifier.background(SlateSurfaceVariant)
                                        .border(1.dp, Brush.horizontalGradient(listOf(ActiveCoral, Color.Red)), RoundedCornerShape(10.dp))
                                } else {
                                    Modifier.background(bgColor)
                                }
                            )
                            .clickable {
                                if (isLocked) {
                                    showSavageProDialog = true
                                } else {
                                    viewModel.selectedTone.value = toneName
                                }
                            }
                            .testTag("tone_${toneName.lowercase().replace("!", "")}")

                        Box(
                            modifier = cellModifier,
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = when (toneName) {
                                        "Casual" -> "😊 " + stringResource(com.example.R.string.tone_casual)
                                        "Savage" -> "🔥 " + stringResource(com.example.R.string.tone_savage)
                                        "SAVAGE!!" -> "💀 " + stringResource(com.example.R.string.tone_savage_pro)
                                        else -> "⚔️ " + stringResource(com.example.R.string.tone_balanced)
                                    },
                                    fontWeight = FontWeight.Black,
                                    color = contentColor,
                                    fontSize = 10.sp,
                                    maxLines = 1
                                )
                                if (isLocked) {
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text("🔒", fontSize = 10.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Flavor chips
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, SlateSurfaceVariant, RoundedCornerShape(16.dp))
                    .background(SlateSurface)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(com.example.R.string.flavor_tags_title),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextLight
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val tagsList = listOf("Demolished", "Choked", "Lucky Win", "Comeback")
                    tagsList.forEach { tagValue ->
                        val isSelected = tags.contains(tagValue)
                        val chipBg = if (isSelected) PadelLime else SlateSurfaceVariant
                        val chipBorder = if (isSelected) PadelLime else Color.Transparent
                        val chipText = if (isSelected) Color.Black else TextMuted

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(chipBg)
                                .border(1.dp, chipBorder, RoundedCornerShape(20.dp))
                                .clickable { viewModel.toggleTag(tagValue) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                                .testTag("tag_chip_${tagValue.lowercase().replace(" ","_")}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = when (tagValue) {
                                    "Demolished" -> stringResource(com.example.R.string.tag_demolished)
                                    "Choked" -> stringResource(com.example.R.string.tag_choked)
                                    "Lucky Win" -> stringResource(com.example.R.string.tag_lucky)
                                    else -> stringResource(com.example.R.string.tag_comeback)
                                },
                                fontWeight = FontWeight.SemiBold,
                                color = chipText,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Generate button
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.generateRoast() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("generate_roast_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PadelLime,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(24.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Submit",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (remainingAiCalls > 0 || isProUser) {
                            stringResource(com.example.R.string.generate_btn)
                        } else {
                            stringResource(com.example.R.string.generate_template_btn)
                        },
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
                    )
                }

                Text(
                    text = if (isProUser) {
                        stringResource(com.example.R.string.ai_quota_pro)
                    } else if (remainingAiCalls > 0) {
                        stringResource(com.example.R.string.ai_quota_remaining, remainingAiCalls)
                    } else {
                        stringResource(com.example.R.string.ai_quota_exhausted)
                    },
                    fontSize = 11.sp,
                    color = if (remainingAiCalls > 0 || isProUser) TextMuted else ActiveCoral,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun InteractivePlayerField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    suggestions: List<String>,
    onSuggestionSelect: (String) -> Unit,
    testTag: String
) {
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(suggestions, value) {
        if (value.isBlank()) {
            expanded = false
        } else if (suggestions.isNotEmpty() && suggestions.contains(value).not()) {
            expanded = true
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            label = { Text(label, color = TextMuted) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextLight,
                unfocusedTextColor = TextLight,
                focusedBorderColor = PadelLime,
                unfocusedBorderColor = SlateSurfaceVariant,
                focusedContainerColor = SlateSurfaceVariant,
                unfocusedContainerColor = SlateSurfaceVariant
            ),
            singleLine = true,
            trailingIcon = {
                if (value.isNotBlank()) {
                    IconButton(onClick = { onValueChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextMuted)
                    }
                }
            }
        )

        if (expanded && suggestions.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SlateSurfaceVariant)
                    .border(1.dp, PadelLime.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    suggestions.forEach { suggestion ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSuggestionSelect(suggestion)
                                    expanded = false
                                }
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = ElectricCyan,
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = suggestion, color = TextLight, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreTrackSelector(
    setName: String,
    valA: String,
    valB: String,
    onSelectA: (Int) -> Unit,
    onSelectB: (Int) -> Unit
) {
    val currentA = valA.toIntOrNull() ?: 0
    val currentB = valB.toIntOrNull() ?: 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SlateSurfaceVariant.copy(alpha = 0.3f))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(setName, color = TextMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            // Show a mini current tally
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(HeroCardBg)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "$valA - $valB",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = PadelLime
                )
            }
        }

        // Team A selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(com.example.R.string.team_a_score),
                color = TextLight,
                modifier = Modifier.width(100.dp),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Decrement Button
            IconButton(
                onClick = { onSelectA(maxOf(0, currentA - 1)) },
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(50))
                    .background(SlateSurfaceVariant)
            ) {
                Text("-", fontWeight = FontWeight.Black, color = TextLight, fontSize = 15.sp)
            }

            val scrollStateA = rememberScrollState()
            Row(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(scrollStateA),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (score in 0..7) {
                    val isSelected = valA == score.toString()
                    val cellBg = if (isSelected) PadelLime else SlateSurfaceVariant
                    val cellTxt = if (isSelected) Color(0xFF0F172A) else TextMuted

                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(50))
                            .background(cellBg)
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) Color(0xFF0F172A) else Color.Transparent,
                                shape = RoundedCornerShape(50)
                            )
                            .clickable { onSelectA(score) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(score.toString(), color = cellTxt, fontWeight = FontWeight.Black, fontSize = 14.sp)
                    }
                }
            }

            // Increment Button
            IconButton(
                onClick = { onSelectA(minOf(7, currentA + 1)) },
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(50))
                    .background(SlateSurfaceVariant)
            ) {
                Text("+", fontWeight = FontWeight.Black, color = TextLight, fontSize = 15.sp)
            }
        }

        // Team B selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(com.example.R.string.team_b_score),
                color = TextLight,
                modifier = Modifier.width(100.dp),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Decrement Button
            IconButton(
                onClick = { onSelectB(maxOf(0, currentB - 1)) },
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(50))
                    .background(SlateSurfaceVariant)
            ) {
                Text("-", fontWeight = FontWeight.Black, color = TextLight, fontSize = 15.sp)
            }

            val scrollStateB = rememberScrollState()
            Row(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(scrollStateB),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (score in 0..7) {
                    val isSelected = valB == score.toString()
                    val cellBg = if (isSelected) ElectricCyan else SlateSurfaceVariant
                    val cellTxt = if (isSelected) Color(0xFF0F172A) else TextMuted

                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(50))
                            .background(cellBg)
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) Color(0xFF0F172A) else Color.Transparent,
                                shape = RoundedCornerShape(50)
                            )
                            .clickable { onSelectB(score) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(score.toString(), color = cellTxt, fontWeight = FontWeight.Black, fontSize = 14.sp)
                    }
                }
            }

            // Increment Button
            IconButton(
                onClick = { onSelectB(minOf(7, currentB + 1)) },
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(50))
                    .background(SlateSurfaceVariant)
            ) {
                Text("+", fontWeight = FontWeight.Black, color = TextLight, fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun LoadingSportsView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = ActiveCoral,
            strokeWidth = 6.dp,
            modifier = Modifier.size(72.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(com.example.R.string.generating_text),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = TextLight
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(com.example.R.string.generating_sub),
            style = MaterialTheme.typography.bodySmall,
            color = TextMuted,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoastCardSuccessView(
    roast: RoastResponseSchema,
    isTemplate: Boolean,
    viewModel: BanterViewModel,
    onReset: () -> Unit
) {
    val p1 by viewModel.player1.collectAsState()
    val p2 by viewModel.player2.collectAsState()
    val p3 by viewModel.player3.collectAsState()
    val p4 by viewModel.player4.collectAsState()

    val s1A by viewModel.set1A.collectAsState()
    val s1B by viewModel.set1B.collectAsState()
    val s2A by viewModel.set2A.collectAsState()
    val s2B by viewModel.set2B.collectAsState()
    val s3A by viewModel.set3A.collectAsState()
    val s3B by viewModel.set3B.collectAsState()

    val userTone by viewModel.selectedTone.collectAsState()

    val currentCardIndex by viewModel.currentRoastIndex.collectAsState()
    val isExpanded by viewModel.isDetailsExpanded.collectAsState()

    val currentCard = roast.roast_cards.getOrNull(currentCardIndex)
    val recapText = if (!currentCard?.match_recap.isNullOrBlank()) currentCard?.match_recap!! else roast.match_recap
    val mvpText = if (!currentCard?.mvp_line.isNullOrBlank()) currentCard?.mvp_line!! else roast.mvp_line
    val rematchText = if (!currentCard?.rematch_hype.isNullOrBlank()) currentCard?.rematch_hype!! else roast.rematch_hype

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { roast.roast_cards.size }
    )

    val lazyListState = rememberLazyListState()

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Observe pagerState and sync to viewmodel index
    LaunchedEffect(pagerState.currentPage) {
        viewModel.currentRoastIndex.value = pagerState.currentPage
    }

    // Outer scroll column
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Back/Reset header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onReset,
                    modifier = Modifier.background(PadelLime, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Log out",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(com.example.R.string.title_receipt),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextLight
                )
                Spacer(modifier = Modifier.weight(1f))

                // Brand Badge representing detected trigger
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            when (roast.narrative_trigger_detected) {
                                "Choke" -> ActiveCoral.copy(alpha = 0.25f)
                                "Dominant" -> PadelLime.copy(alpha = 0.25f)
                                "Upset" -> ElectricCyan.copy(alpha = 0.25f)
                                else -> SlateSurfaceVariant
                            }
                        )
                        .border(
                            width = 1.dp,
                            color = when (roast.narrative_trigger_detected) {
                                "Choke" -> ActiveCoral
                                "Dominant" -> PadelLime
                                "Upset" -> ElectricCyan
                                else -> TextMuted
                            },
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = roast.narrative_trigger_detected,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = when (roast.narrative_trigger_detected) {
                            "Choke" -> ActiveCoral
                            "Dominant" -> PadelLime
                            "Upset" -> ElectricCyan
                            else -> TextLight
                        }
                    )
                }
            }
        }

        if (isTemplate) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(ActiveCoral.copy(alpha = 0.15f))
                        .border(1.dp, ActiveCoral.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(com.example.R.string.template_banner),
                        color = ActiveCoral,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Pager Indicators / Alternative Selection row
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(com.example.R.string.swipe_instruction),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    repeat(roast.roast_cards.size) { idx ->
                        val isSelected = currentCardIndex == idx
                        Box(
                            modifier = Modifier
                                .size(width = if (isSelected) 24.dp else 8.dp, height = 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isSelected) PadelLime else SlateSurfaceVariant)
                                .clickable {
                                    scope.launch { pagerState.animateScrollToPage(idx) }
                                }
                        )
                    }
                }
            }
        }

        // Swipeable Cards (Horizontal Pager)
        item {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
            ) { page ->
                val currentCardObj = roast.roast_cards.getOrNull(page)
                if (currentCardObj != null) {
                    RoastPerformanceDisplayCard(
                        headline = currentCardObj.headline,
                        bodyText = currentCardObj.body_text,
                        p1 = p1, p2 = p2, p3 = p3, p4 = p4,
                        s1A = s1A, s1B = s1B, s2A = s2A, s2B = s2B, s3A = s3A, s3B = s3B,
                        assignedTitles = roast.player_titles_assigned,
                        tone = userTone,
                        onBodyClick = {
                            viewModel.isDetailsExpanded.value = true
                            scope.launch {
                                // Scroll to index 3 (Secondary Content Drawer Collapsible Panel)
                                lazyListState.animateScrollToItem(3)
                            }
                        }
                    )
                }
            }
        }

        // Secondary Content Drawer Collapsible Panel
        item(key = "tactical_diagnostics_item") {
            val activeCardObj = roast.roast_cards.getOrNull(currentCardIndex)
            val liveRecap = if (!activeCardObj?.match_recap.isNullOrBlank()) activeCardObj?.match_recap!! else roast.match_recap
            val liveMvp = if (!activeCardObj?.mvp_line.isNullOrBlank()) activeCardObj?.mvp_line!! else roast.mvp_line
            val liveRematch = if (!activeCardObj?.rematch_hype.isNullOrBlank()) activeCardObj?.rematch_hype!! else roast.rematch_hype

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0F172A)) // Dark sporty slate console card for high contrast
                    .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.isDetailsExpanded.value = !isExpanded },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Toggle Expand",
                        tint = PadelLime
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(com.example.R.string.tactical_match_diagnostics),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = if (isExpanded) stringResource(com.example.R.string.collapse_text) else stringResource(com.example.R.string.expand_text),
                        fontSize = 12.sp,
                        color = PadelLime,
                        fontWeight = FontWeight.Bold
                    )
                }

                AnimatedVisibility(
                    visible = isExpanded,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column(
                        modifier = Modifier.padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        HorizontalDivider(color = Color(0xFF1E293B), thickness = 1.dp)

                        // 3-Sentence Recaps
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Info, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(stringResource(com.example.R.string.comedic_recap), fontWeight = FontWeight.Bold, color = ElectricCyan, fontSize = 13.sp)
                            }
                            Text(
                                text = liveRecap,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFE2E8F0), // Premium readable off-white text inside dark background card
                                lineHeight = 20.sp
                            )
                        }

                        // Comedic MVP / Choke spotter
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = PadelLime, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(stringResource(com.example.R.string.clown_mvp), fontWeight = FontWeight.Bold, color = PadelLime, fontSize = 13.sp)
                            }
                            Text(
                                text = liveMvp,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFE2E8F0),
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }

                        // Rematch Trash-Talk response
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = ActiveCoral, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(stringResource(com.example.R.string.rematch_hype_title), fontWeight = FontWeight.Bold, color = ActiveCoral, fontSize = 13.sp)
                            }
                            Text(
                                text = liveRematch,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFE2E8F0)
                            )
                        }
                    }
                }
            }
        }

        // Native share action sheet
        item {
            Button(
                onClick = {
                    val activeCard = roast.roast_cards.getOrNull(currentCardIndex)
                    if (activeCard != null) {
                        triggerReceiptShare(
                            context = context,
                            p1 = p1, p2 = p2, p3 = p3, p4 = p4,
                            s1 = "$s1A-$s1B",
                            s2 = "$s2A-$s2B",
                            s3 = if (s3A != "0" || s3B != "0") "$s3A-$s3B" else null,
                            headline = activeCard.headline,
                            body = activeCard.body_text,
                            whatsappTxt = activeCard.pre_filled_whatsapp,
                            mvp = roast.mvp_line,
                            titles = roast.player_titles_assigned,
                            recap = if (!activeCard.match_recap.isNullOrBlank()) activeCard.match_recap!! else roast.match_recap,
                            rematch = if (!activeCard.rematch_hype.isNullOrBlank()) activeCard.rematch_hype!! else roast.rematch_hype
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("share_receipt_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ElectricCyan,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(com.example.R.string.share_btn_text),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
                )
            }
        }

        // Log another match redirect
        item {
            OutlinedButton(
                onClick = onReset,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextLight),
                border = BorderStroke(1.5.dp, TextLight)
            ) {
                Text(stringResource(com.example.R.string.log_another_btn), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RoastPerformanceDisplayCard(
    headline: String,
    bodyText: String,
    p1: String, p2: String, p3: String, p4: String,
    s1A: String, s1B: String,
    s2A: String, s2B: String,
    s3A: String, s3B: String,
    assignedTitles: com.example.api.PlayerTitles,
    tone: String,
    onBodyClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(32.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        HeroCardBg,
                        Color(0xFF020617) // Sleek rich dark athletic black
                    )
                )
            )
            .border(
                width = 4.dp,
                color = when (tone) {
                    "Savage" -> ActiveCoral
                    "Casual" -> ElectricCyan
                    else -> PadelLime
                },
                shape = RoundedCornerShape(32.dp)
            )
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Card Header: Scores
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(PadelLime)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "MATCH RESULT",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                    Text(
                        text = "#ROAST-RECEIPT",
                        style = MaterialTheme.typography.labelSmall,
                        color = HeroTextMuted
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Score Display Box - Sporty Dark Scoreboard Style
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF1E293B))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "$p1 & $p2",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = HeroTextLight,
                            maxLines = 1
                        )
                        Text(
                            text = "vs",
                            fontSize = 10.sp,
                            color = HeroTextMuted,
                            modifier = Modifier.padding(vertical = 1.dp)
                        )
                        Text(
                            text = "$p3 & $p4",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = HeroTextLight,
                            maxLines = 1
                        )
                    }

                    // Scoreboard labels representation
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ScoreBox(scoreA = s1A, scoreB = s1B)
                        ScoreBox(scoreA = s2A, scoreB = s2B)
                        if (s3A != "0" || s3B != "0") {
                            ScoreBox(scoreA = s3A, scoreB = s3B)
                        }
                    }
                }
            }

            // Headline matching the bold italic look
            Text(
                text = headline.uppercase(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Black,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    lineHeight = 28.sp
                ),
                color = HeroTextLight,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )

            // Body content with trailing ... hint
            val displayBody = if (bodyText.endsWith("...")) bodyText else "$bodyText..."
            Text(
                text = displayBody,
                style = MaterialTheme.typography.bodyMedium,
                color = HeroTextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onBodyClick() }
            )

            // Assigned Titles (Footer)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F172A))
                    .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "🏷️ ASSIGNED RACKET LABELS:",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    color = HeroTextMuted
                )

                Spacer(modifier = Modifier.height(2.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "• $p1: \"${assignedTitles.player_1}\"", fontSize = 11.sp, color = PadelLime, fontWeight = FontWeight.Bold)
                    Text(text = "• $p2: \"${assignedTitles.player_2}\"", fontSize = 11.sp, color = PadelLime, fontWeight = FontWeight.Bold)
                    Text(text = "• $p3: \"${assignedTitles.player_3}\"", fontSize = 11.sp, color = ElectricCyan, fontWeight = FontWeight.Bold)
                    Text(text = "• $p4: \"${assignedTitles.player_4}\"", fontSize = 11.sp, color = ElectricCyan, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ScoreBox(scoreA: String, scoreB: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF0F172A))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$scoreA - $scoreB",
            fontWeight = FontWeight.Black,
            fontSize = 12.sp,
            color = PadelLime
        )
    }
}

@Composable
fun ErrorDisplayView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Error",
            tint = ActiveCoral,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "AI ROAST TIMEOUT",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Black,
            color = ActiveCoral
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = TextLight,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = PadelLime, contentColor = Color.Black)
        ) {
            Text("Log Again", fontWeight = FontWeight.Bold)
        }
    }
}

/**
 * Simple text wrapping helper for canvas drawing.
 */
fun wrapText(text: String, maxWidth: Int, paint: Paint): List<String> {
    val words = text.split(" ")
    val lines = mutableListOf<String>()
    var currentLine = StringBuilder()
    for (word in words) {
        val testLine = if (currentLine.isEmpty()) word else "${currentLine} $word"
        val width = paint.measureText(testLine)
        if (width < maxWidth) {
            currentLine.append(if (currentLine.isEmpty()) "" else " ").append(word)
        } else {
            lines.add(currentLine.toString())
            currentLine = StringBuilder(word)
        }
    }
    if (currentLine.isNotEmpty()) {
        lines.add(currentLine.toString())
    }
    return lines
}

/**
 * Creates a high-fidelity visual PNG image of the Roast Receipt card directly on an Android Canvas and returns its Uri.
 */
fun createReceiptBitmap(
    context: Context,
    p1: String?, p2: String?, p3: String?, p4: String?,
    s1: String?, s2: String?, s3: String?,
    headline: String?,
    body: String?,
    mvp: String?,
    titles: com.example.api.PlayerTitles?
): Uri? {
    try {
        val width = 720
        val height = 1000
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val safeP1 = p1 ?: "Player 1"
        val safeP2 = p2 ?: "Player 2"
        val safeP3 = p3 ?: "Player 3"
        val safeP4 = p4 ?: "Player 4"
        val safeS1 = s1 ?: "0-0"
        val safeS2 = s2 ?: ""
        val safeHeadline = headline ?: "MATCH ROUNDUP"
        val safeBody = body ?: ""
        val safeMvp = mvp ?: ""

        // Draw deep space midnight background
        val bgPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#090D16")
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // Draw card border
        val borderPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#BEF264") // PadelLime
            style = Paint.Style.STROKE
            strokeWidth = 14f
        }
        canvas.drawRoundRect(14f, 14f, width - 14f, height - 14f, 32f, 32f, borderPaint)

        // Title Block with dual lightning bolts
        val titlePaint = Paint().apply {
            color = android.graphics.Color.parseColor("#BEF264") // PadelLime
            textSize = 42f
            isFakeBoldText = true
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("⚡ PADELROAST OFFICIAL ⚡", width / 2f, 90f, titlePaint)

        val receiptLabelPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#64748B")
            textSize = 20f
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("MATCH BANTER RECEIPT • #PADELROAST", width / 2f, 135f, receiptLabelPaint)

        // Separator line
        val linePaint = Paint().apply {
            color = android.graphics.Color.parseColor("#1E293B")
            strokeWidth = 4f
        }
        canvas.drawLine(50f, 165f, width - 50f, 165f, linePaint)

        // Duelists Section Title
        val sectionTitlePaint = Paint().apply {
            color = android.graphics.Color.parseColor("#94A3B8")
            textSize = 18f
            isFakeBoldText = true
        }
        canvas.drawText("⚔️ THE LINEUP:", 60f, 205f, sectionTitlePaint)

        // Team text - Team A (PadelLime) vs Team B (Cyan)
        val teamAPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#BEF264") // PadelLime
            textSize = 28f
            isFakeBoldText = true
        }
        val teamBPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#22D3EE") // ElectricCyan
            textSize = 28f
            isFakeBoldText = true
        }
        val vsPaint = Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 20f
            isFakeBoldText = true
        }

        canvas.drawText("$safeP1 & $safeP2", 60f, 250f, teamAPaint)
        canvas.drawText("    vs", 60f, 285f, vsPaint)
        canvas.drawText("$safeP3 & $safeP4", 60f, 325f, teamBPaint)

        // Scores Block
        val scoreLabelPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#94A3B8")
            textSize = 18f
            isFakeBoldText = true
        }
        canvas.drawText("📊 SCOREBOARD:", 60f, 380f, scoreLabelPaint)

        val scoreValuePaint = Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 30f
            isFakeBoldText = true
        }
        val safeS3 = s3 ?: ""
        val scoreStr = safeS1 + (if (safeS2.isNotEmpty() && safeS2 != "-") " | $safeS2" else "") + (if (safeS3.isNotEmpty() && safeS3 != "-") " | $safeS3" else "")
        canvas.drawText(scoreStr, 60f, 425f, scoreValuePaint)

        canvas.drawLine(50f, 460f, width - 50f, 460f, linePaint)

        // Headline (Large and Centered)
        val headlinePaint = Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 32f
            isFakeBoldText = true
            textAlign = Paint.Align.CENTER
        }
        
        val wrappedHeadline = wrapText(safeHeadline.uppercase(), width - 100, headlinePaint)
        var headlineY = 510f
        for (hLine in wrappedHeadline.take(2)) {
            canvas.drawText(hLine, width / 2f, headlineY, headlinePaint)
            headlineY += 38f
        }

        // Body Text
        val bodyPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#E2E8F0")
            textSize = 21f
            textAlign = Paint.Align.CENTER
        }
        val wrappedBody = wrapText(safeBody, width - 100, bodyPaint)
        var bodyY = headlineY + 20f
        for (bLine in wrappedBody.take(5)) {
            canvas.drawText(bLine, width / 2f, bodyY, bodyPaint)
            bodyY += 28f
        }

        canvas.drawLine(50f, 780f, width - 50f, 780f, linePaint)

        // Assigned Titles Section
        val titleSectionPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#F43F5E") // ActiveCoral style
            textSize = 18f
            isFakeBoldText = true
        }
        canvas.drawText("🏷️ ASSIGNED RACKET LABELS:", 60f, 815f, titleSectionPaint)

        val labelValueAPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#BEF264")
            textSize = 20f
            isFakeBoldText = true
        }
        val labelValueBPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#22D3EE")
            textSize = 20f
            isFakeBoldText = true
        }

        val title1 = titles?.player_1 ?: "Underdog"
        val title2 = titles?.player_2 ?: "Baseline Hugger"
        val title3 = titles?.player_3 ?: "Fence Smasher"
        val title4 = titles?.player_4 ?: "No-Show"

        canvas.drawText("• $safeP1: \"$title1\"", 70f, 855f, labelValueAPaint)
        canvas.drawText("• $safeP2: \"$title2\"", 70f, 885f, labelValueAPaint)
        canvas.drawText("• $safeP3: \"$title3\"", 70f, 915f, labelValueBPaint)
        canvas.drawText("• $safeP4: \"$title4\"", 70f, 945f, labelValueBPaint)

        // Write to Cache Dir
        val imagesDir = File(context.cacheDir, "shared_images")
        if (!imagesDir.exists()) {
            imagesDir.mkdirs()
        }
        val file = File(imagesDir, "roast_receipt.png")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.flush()
        stream.close()

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

/**
 * Triggers native Android share sheet with an image of the roast receipt card, and then text of the tactical match diagnostics followed by an Android App store download link.
 */
fun triggerReceiptShare(
    context: Context,
    p1: String?, p2: String?, p3: String?, p4: String?,
    s1: String?, s2: String?, s3: String?,
    headline: String?,
    body: String?,
    whatsappTxt: String?,
    mvp: String?,
    titles: com.example.api.PlayerTitles?,
    recap: String?,
    rematch: String?
) {
    val playStoreUrl = "https://play.google.com/store/apps/details?id=${context.packageName}"
    val safeWhatsapp = whatsappTxt ?: ""
    val safeHeadline = headline ?: "Banter Card"
    val safeBody = body ?: ""
    val safeRecap = recap ?: ""
    val safeMvp = mvp ?: ""
    val safeRematch = rematch ?: ""

    // Consolidate the tactical match diagnostics and app download link
    val diagnosticsText = buildString {
        append(safeWhatsapp)
        append("\n\n")
        append("🏆 PADELROAST TACTICAL MATCH DIAGNOSTICS 🏆\n\n")
        append("🔥 Roast Card: \"$safeHeadline\" - $safeBody\n\n")
        append("📊 Tactical Match Recap:\n")
        append("$safeRecap\n\n")
        append("🤡 Clown MVP of the Game:\n")
        append("$safeMvp\n\n")
        append("⚡ Rematch Trash-Talk & Hype:\n")
        append("$safeRematch\n\n")
        append("📱 Roast your partners instantly!\n")
        append("Get PadelRoast on Google Play Store:\n")
        append(playStoreUrl)
    }

    try {
        // Render the image card of the receipt
        val imageUri = createReceiptBitmap(
            context = context,
            p1 = p1, p2 = p2, p3 = p3, p4 = p4,
            s1 = s1, s2 = s2, s3 = s3,
            headline = headline,
            body = body,
            mvp = mvp,
            titles = titles
        )

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, diagnosticsText)
            if (imageUri != null) {
                putExtra(Intent.EXTRA_STREAM, imageUri)
                type = "image/png"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                type = "text/plain"
            }
        }

        val shareIntent = Intent.createChooser(sendIntent, "Drop the Receipt 🧾").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(shareIntent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
