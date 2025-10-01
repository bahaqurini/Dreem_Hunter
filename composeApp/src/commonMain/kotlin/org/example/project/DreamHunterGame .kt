package org.example.project

import kotlin.random.Random

// DreamHunterGame.kt
class DreamHunterGame {
    
    private val taskRepository = TaskRepository()
    private val powerRepository = PowerRepository()
    
    fun addTaskToPlayer(player: Player, task: GameTask): Player {
        val updatedTasks = player.completedTasks + task
        return player.copy(completedTasks = updatedTasks)
    }
    
    fun completeTask(player: Player, taskId: String): Player {
        val updatedTasks = player.completedTasks.map { task ->
            if (task.id == taskId) task.copy(isCompleted = true) else task
        }
        
        val completedTask = player.completedTasks.find { it.id == taskId }
        val newPoints = player.dreamPoints + (completedTask?.points ?: 0)
        
        val newPlayer = player.copy(
            completedTasks = updatedTasks,
            dreamPoints = newPoints
        )
        
        return addStrengthBall(newPlayer)
    }
    
    private fun addStrengthBall(player: Player): Player {
        val random = Random.nextInt(100)
        val newBall = when {
            random < 40 -> BallColor.GREEN
            random < 70 -> BallColor.BLUE
            random < 90 -> BallColor.PURPLE
            else -> BallColor.GOLD
        }
        
        val newBalls = player.strengthBalls + newBall
        val updatedPlayer = player.copy(strengthBalls = newBalls)
        
        return checkSuperPowerUnlock(updatedPlayer)
    }
    
    private fun checkSuperPowerUnlock(player: Player): Player {
        if (player.strengthBalls.size >= 10) {
            val availablePowers = powerRepository.getAvailablePowers(player.level)
            val unlockedPowerIds = player.unlockedPowers.map { it.id }
            val newPower = availablePowers.firstOrNull { it.id !in unlockedPowerIds }
            
            newPower?.let {
                val newPowers = player.unlockedPowers + it
                return player.copy(
                    unlockedPowers = newPowers,
                    strengthBalls = emptyList(),
                    level = player.level + 1
                )
            }
        }
        return player
    }
    
    fun getDailyTasks(): List<GameTask> {
        return taskRepository.generateDailyTasks()
    }
}