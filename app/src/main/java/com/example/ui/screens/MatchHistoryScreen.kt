package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MatchEntity
import com.example.data.PlayerEntity
import com.example.ui.BanterViewModel
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MatchHistoryScreen(
    viewModel: BanterViewModel,
    onNavigateToDeck: () -> Unit,
    modifier: Modifier = Modifier
) {
    val matches by viewModel.matchHistory.collectAsState()
    val leaderboard by viewModel.leaderboard.collectAsState()
    var selectedTab by remember { mutableStateOf(0) } // 0 = Match Logs, 1 = Leaderboard Standings

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Log title
        Text(
            text = androidx.compose.ui.res.stringResource(com.example.R.string.header_archives),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
            color = TextLight,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Sub heading
        Text(
            text = androidx.compose.ui.res.stringResource(com.example.R.string.archives_subtitle),
            style = MaterialTheme.typography.bodySmall,
            color = TextMuted,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Tab Row selectors
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = SlateSurface,
            contentColor = TextLight,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = PadelLime
                )
            },
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, SlateSurfaceVariant, RoundedCornerShape(10.dp))
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text(androidx.compose.ui.res.stringResource(com.example.R.string.history_tab), fontWeight = FontWeight.Bold, fontSize = 14.sp) },
                modifier = Modifier.testTag("match_history_tab")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text(androidx.compose.ui.res.stringResource(com.example.R.string.rankings_tab), fontWeight = FontWeight.Bold, fontSize = 14.sp) },
                modifier = Modifier.testTag("leaderboard_tab")
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            if (selectedTab == 0) {
                if (matches.isEmpty()) {
                    HistoryEmptyState()
                } else {
                    MatchHistoryList(
                        matches = matches,
                        onReShare = { match ->
                            viewModel.selectMatchToView(match, onNavigateToDeck)
                        }
                    )
                }
            } else {
                if (leaderboard.isEmpty()) {
                    LeaderboardEmptyState()
                } else {
                    LeaderboardView(leaderboard = leaderboard)
                }
            }
        }
    }
}

@Composable
fun MatchHistoryList(
    matches: List<MatchEntity>,
    onReShare: (MatchEntity) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        items(matches) { match ->
            MatchHistoryCard(match = match, onReShare = onReShare)
        }
    }
}

@Composable
fun MatchHistoryCard(
    match: MatchEntity,
    onReShare: (MatchEntity) -> Unit
) {
    val dateString = remember(match.date) {
        val sdf = SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault())
        sdf.format(Date(match.date))
    }

    // Determine the set scores
    val s1A = match.set1_teamA
    val s1B = match.set1_teamB
    val s2A = match.set2_teamA
    val s2B = match.set2_teamB
    val s3A = match.set3_teamA
    val s3B = match.set3_teamB

    val hasSet3 = s3A > 0 || s3B > 0

    // Simple rule to identify which team won based on set scores
    val teamASets = (if (s1A > s1B) 1 else 0) + (if (s2A > s2B) 1 else 0) + (if (hasSet3 && s3A > s3B) 1 else 0)
    val teamBSets = (if (s1B > s1A) 1 else 0) + (if (s2B > s2A) 1 else 0) + (if (hasSet3 && s3B > s3A) 1 else 0)
    val teamAWon = teamASets > teamBSets

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SlateSurface)
            .border(
                border = BorderStroke(
                    1.5.dp, 
                    if (teamAWon) PadelLime.copy(alpha = 0.6f) else ElectricCyan.copy(alpha = 0.6f)
                ), 
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Header Row: Date & Tone
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(if (teamAWon) PadelLime else ElectricCyan, RoundedCornerShape(50))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = dateString,
                        fontSize = 11.sp,
                        color = TextMuted,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SlateSurfaceVariant)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = androidx.compose.ui.res.stringResource(com.example.R.string.tone_prefix, match.tone),
                        fontSize = 10.sp,
                        color = TextLight,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            // Competitor Details matched with a Professional Tournament Grid Scoreboard
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left Part: The Competitors
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Team Alpha
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(RoundedCornerShape(50))
                                .background(PadelLime)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${match.player1} & ${match.player2}",
                            fontSize = 14.sp,
                            fontWeight = if (teamAWon) FontWeight.ExtraBold else FontWeight.Medium,
                            color = if (teamAWon) TextLight else TextMuted,
                            maxLines = 1
                        )
                        if (teamAWon) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("👑", fontSize = 12.sp)
                        }
                    }

                    // Separation Divider line between team rows
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(1.dp)
                            .background(SlateSurfaceVariant)
                    )

                    // Team Beta
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(RoundedCornerShape(50))
                                .background(ElectricCyan)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${match.player3} & ${match.player4}",
                            fontSize = 14.sp,
                            fontWeight = if (!teamAWon) FontWeight.ExtraBold else FontWeight.Medium,
                            color = if (!teamAWon) TextLight else TextMuted,
                            maxLines = 1
                        )
                        if (!teamAWon) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("👑", fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Right Part: Sporty Digital Scoreboard console
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(HeroCardBg)
                        .border(1.dp, SlateSurfaceVariant.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                        .padding(6.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Set 1 Block
                        ScoreSetBlock(scoreA = s1A, scoreB = s1B, activeWinnerA = s1A > s1B, activeWinnerB = s1B > s1A)
                        
                        // Set 2 Block
                        ScoreSetBlock(scoreA = s2A, scoreB = s2B, activeWinnerA = s2A > s2B, activeWinnerB = s2B > s2A)

                        // Set 3 Block
                        if (hasSet3) {
                            ScoreSetBlock(scoreA = s3A, scoreB = s3B, activeWinnerA = s3A > s3B, activeWinnerB = s3B > s3A)
                        }
                    }
                }
            }

            HorizontalDivider(color = SlateSurfaceVariant, thickness = 1.dp)

            // Bottom trigger detector and reshare
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(ActiveCoral, RoundedCornerShape(50))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = androidx.compose.ui.res.stringResource(com.example.R.string.trigger_label, match.trigger_detected),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ActiveCoral
                    )
                }

                Button(
                    onClick = { onReShare(match) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HeroCardBg,
                        contentColor = HeroTextLight
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                    modifier = Modifier
                        .height(34.dp)
                        .testTag("reshare_card_button_${match.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Reshare",
                        modifier = Modifier.size(14.dp),
                        tint = PadelLime
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = androidx.compose.ui.res.stringResource(com.example.R.string.reshare_card_btn),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = HeroTextLight
                    )
                }
            }
        }
    }
}

@Composable
fun ScoreSetBlock(
    scoreA: Int,
    scoreB: Int,
    activeWinnerA: Boolean,
    activeWinnerB: Boolean
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF1E293B))
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = scoreA.toString(),
                fontWeight = FontWeight.Black,
                fontSize = 13.sp,
                color = if (activeWinnerA) PadelLime else HeroTextMuted
            )
            Box(modifier = Modifier.width(10.dp).height(1.dp).background(Color(0xFF334155)))
            Text(
                text = scoreB.toString(),
                fontWeight = FontWeight.Black,
                fontSize = 13.sp,
                color = if (activeWinnerB) ElectricCyan else HeroTextMuted
            )
        }
    }
}

@Composable
fun LeaderboardView(leaderboard: List<PlayerEntity>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
            // Header Columns label
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(androidx.compose.ui.res.stringResource(com.example.R.string.leaderboard_rank_name), fontSize = 11.sp, fontWeight = FontWeight.Black, color = TextMuted, modifier = Modifier.weight(1f))
                Row(modifier = Modifier.width(140.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(androidx.compose.ui.res.stringResource(com.example.R.string.leaderboard_wl_rate), fontSize = 11.sp, fontWeight = FontWeight.Black, color = TextMuted, modifier = Modifier.width(70.dp), textAlign = TextAlign.Center)
                    Text(androidx.compose.ui.res.stringResource(com.example.R.string.leaderboard_elo), fontSize = 11.sp, fontWeight = FontWeight.Black, color = TextMuted, modifier = Modifier.width(60.dp), textAlign = TextAlign.End)
                }
            }
        }

        items(leaderboard.size) { index ->
            val player = leaderboard[index]
            val rankNum = index + 1
            val winPct = if (player.matches_played > 0) {
                ((player.matches_won.toDouble() / player.matches_played) * 100).toInt()
            } else 0

            val topRankColor = when (rankNum) {
                1 -> Color(0xFFFCD34D) // Vivid Gold
                2 -> Color(0xFFCBD5E1) // Sleek Silver
                3 -> Color(0xFFFDBA74) // Bronze / Coral
                else -> Color.Transparent
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SlateSurface)
                    .border(
                        BorderStroke(
                            width = if (rankNum <= 3) 2.dp else 1.dp,
                            color = if (rankNum <= 3) topRankColor else SlateSurfaceVariant
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Rank Medal/Badge + Player details
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        // Rank status visual badge
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    when (rankNum) {
                                        1 -> topRankColor
                                        2 -> topRankColor
                                        3 -> topRankColor
                                        else -> SlateSurfaceVariant
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = when (rankNum) {
                                    1 -> "👑"
                                    2 -> "🥈"
                                    3 -> "🥉"
                                    else -> rankNum.toString()
                                },
                                fontSize = if (rankNum <= 3) 12.sp else 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (rankNum <= 3) Color.DarkGray else TextLight
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = player.name,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextLight,
                                fontSize = 15.sp,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            // Small decorative track bar representing win rate visually
                            Box(
                                modifier = Modifier
                                    .width(70.dp)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(SlateSurfaceVariant)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(winPct / 100f)
                                        .background(if (rankNum == 1) PadelLime else ElectricCyan)
                                )
                            }
                        }
                    }

                    // Win rate + ELO High contrast block
                    Row(
                        modifier = Modifier.width(140.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.width(70.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${player.matches_won}W - ${player.matches_played - player.matches_won}L",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextLight
                            )
                            Text(
                                text = androidx.compose.ui.res.stringResource(com.example.R.string.win_rate_suffix, winPct),
                                fontSize = 10.sp,
                                color = TextMuted,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // High contrast ELO block
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(HeroCardBg)
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = player.current_elo.toString(),
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                color = if (rankNum == 1) PadelLime else ElectricCyan,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryEmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(SlateSurface),
            contentAlignment = Alignment.Center
        ) {
            Text("🎾", fontSize = 28.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = androidx.compose.ui.res.stringResource(com.example.R.string.history_empty_title),
            fontWeight = FontWeight.Black,
            color = TextMuted,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = androidx.compose.ui.res.stringResource(com.example.R.string.history_empty_desc),
            color = TextMuted,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LeaderboardEmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(SlateSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Star, contentDescription = null, tint = PadelLime, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = androidx.compose.ui.res.stringResource(com.example.R.string.leaderboard_empty_title),
            fontWeight = FontWeight.Black,
            color = TextMuted,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = androidx.compose.ui.res.stringResource(com.example.R.string.leaderboard_empty_desc),
            color = TextMuted,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}
