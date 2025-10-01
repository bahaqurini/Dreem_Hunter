package org.example.project

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

// Player.kt
data class Player(
    val id: String = "",
    val name: String = "",
    val dreamPoints: Int = 0,
    val level: Int = 1,
    val strengthBalls: List<BallColor> = emptyList(),
    val completedTasks: List<GameTask> = emptyList(),
    val unlockedPowers: List<SuperPower> = emptyList()
)

enum class BallColor {
    GREEN, BLUE, PURPLE, GOLD
}

data class GameTask @OptIn(ExperimentalTime::class) constructor(
    val id: String = "",
    val title: String,
    val description: String,
    val type: TaskType,
    val points: Int = 10,
    val isCompleted: Boolean = false,
    val createdAt: Long = Clock.System.currentTimeMillis()
)

@OptIn(ExperimentalTime::class)
private fun Clock.System.currentTimeMillis(): Long {
    return this.now().toEpochMilliseconds()
}

enum class TaskType {
    BASIC, CREATIVE, CHARITY
}

data class SuperPower(
    val id: String = "",
    val name: String,
    val description: String,
    val requiredBalls: Int = 10
)