package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

// GameScreen.kt
@Composable
fun GameScreen(
    fontFamily: FontFamily?=null,
    viewModel: GameViewModel = viewModel { GameViewModel(DreamHunterGame()) }
        //GameViewModel(DreamHunterGame())

) {
    val playerState by viewModel.playerState.collectAsState()
    
    val showCongratulations = remember { mutableStateOf(false) }
    val lastPowerCount = remember { playerState.unlockedPowers.size }
    
    LaunchedEffect(playerState.unlockedPowers.size) {
        if (playerState.unlockedPowers.size > lastPowerCount) {
            showCongratulations.value = true
            delay(3000)
            showCongratulations.value = false
        }
    }
    
    if (showCongratulations.value) {
        AlertDialog(
            onDismissRequest = { showCongratulations.value = false },
            title = { 
                Text(
                    text = "🎊 مبروك!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontFamily = fontFamily
                )
            },
            text = { 
                Text("لقد فتحت قوة خارقة جديدة!\n'وَمَا تَدْرِي نَفْسٌ مَّاذَا تَكْسِبُ غَداً'",fontFamily = fontFamily)
            },
            confirmButton = {
                Button(onClick = { showCongratulations.value = false }) {
                    Text("استمرار",
                        fontFamily = fontFamily)
                }
            }
        )
    }
    
    Scaffold(
        topBar = { GameTopBar(playerState,fontFamily) },
        content = { padding ->
            GameContent(
                player = playerState,
                onTaskComplete = { taskId -> viewModel.completeTask(taskId) },
                onNewTask = { viewModel.generateNewTasks() },
                modifier = Modifier.padding(padding),
                fontFamily = fontFamily

            )
        }
    )
}

@Composable
fun GameTopBar(player: Player,fontFamily: FontFamily?=null) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF4CAF50))
            .padding(16.dp)
    ) {
        Text(
            text = "صائد الأحلام الذكي ",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontFamily = fontFamily
                )
                Text(
                    text = "المستوى: ${player.level}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    fontFamily = fontFamily
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${player.dreamPoints} نقطة",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontFamily = fontFamily
                )
                Text(
                    text = "${player.strengthBalls.size}/10 كرات",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    fontFamily = fontFamily
                )
            }
        }
    }
}

@Composable
fun GameContent(
    player: Player,
    onTaskComplete: (String) -> Unit,
    onNewTask: () -> Unit,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily?=null
) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            StrengthBallsSection(balls = player.strengthBalls,fontFamily=fontFamily)
            Spacer(modifier = Modifier.height(24.dp))

        }
        
        item {
            Text(
                text = "مهام اليوم 🎯",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp),
                fontFamily = fontFamily
            )
        }
        
        items(player.completedTasks) { task ->
            TaskCard(
                task = task,
                onComplete = { onTaskComplete(task.id) }
                ,fontFamily=fontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        item {
            Button(
                onClick = onNewTask,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
            ) {
                Text(text = "مهمة جديدة 🎉",
                    fontFamily = fontFamily)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            if (player.unlockedPowers.isNotEmpty()) {
                SuperPowersSection(powers = player.unlockedPowers,fontFamily=fontFamily)
            }
        }
    }
}

@Composable
fun StrengthBallsSection(balls: List<BallColor>, fontFamily: FontFamily?) {
    Column {
        Text(
            text = "كرات القوة الخاصة بك:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp),
            fontFamily = fontFamily
        )
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.height(60.dp)
        ) {
            items(10) { index ->
                val ball = balls.getOrNull(index)
                BallItem(color = ball)
            }
        }
        
        if (balls.size >= 8) {
            Text(
                text = "🎉 تقريباً وصلت! ${10 - balls.size} كرات متبقية لفتح قوة خارقة!",
                color = Color(0xFFFF9800),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp),
                fontFamily = fontFamily
            )
        }

        LinearProgressIndicator(
        progress = { balls.size / 10f },
        modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .padding(top = 8.dp),
        color = Color(0xFFFF9800),
        trackColor = ProgressIndicatorDefaults.linearTrackColor,
        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
        )
    }
}

@Composable
fun BallItem(color: BallColor?) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(
                color = when (color) {
                    BallColor.GREEN -> Color(0xFF4CAF50)
                    BallColor.BLUE -> Color(0xFF2196F3)
                    BallColor.PURPLE -> Color(0xFF9C27B0)
                    BallColor.GOLD -> Color(0xFFFFC107)
                    null -> Color(0xFFE0E0E0)
                },
                shape = CircleShape
            )
            .border(1.dp, Color.Gray, CircleShape)
    )
}

@Composable
fun TaskCard(task: GameTask, onComplete: () -> Unit, fontFamily: FontFamily?) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily
                    )
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp),
                        fontFamily = fontFamily
                    )
                }
                
                if (!task.isCompleted) {
                    Button(
                        onClick = onComplete,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (task.type) {
                                TaskType.BASIC -> Color(0xFF4CAF50)
                                TaskType.CREATIVE -> Color(0xFF2196F3)
                                TaskType.CHARITY -> Color(0xFF9C27B0)
                            }
                        )
                    ) {
                        Text("أكمل",
                            fontFamily = fontFamily)
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "مكتمل",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            if (task.isCompleted) {
                Text(
                    text = "مكتملة ✅ +${task.points} نقطة",
                    color = Color(0xFF4CAF50),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp),
                    fontFamily = fontFamily
                )
            }
        }
    }
}

@Composable
fun SuperPowersSection(powers: List<SuperPower>, fontFamily: FontFamily?) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "🌟 قواك الخارقة المفتوحة",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFF57C00),
                modifier = Modifier.padding(bottom = 12.dp),
                fontFamily = fontFamily
            )
            
            powers.forEach { power ->
                SuperPowerItem(power = power, fontFamily = fontFamily)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SuperPowerItem(power: SuperPower, fontFamily: FontFamily?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = "قوة خارقة",
            tint = Color(0xFFFFC107),
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = power.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF57C00),
                fontFamily = fontFamily
            )
            Text(
                text = power.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF757575),
                fontFamily = fontFamily
            )
        }
    }
}
@Preview
@Composable
fun GameScreenPreview()
{
    GameScreen()
}