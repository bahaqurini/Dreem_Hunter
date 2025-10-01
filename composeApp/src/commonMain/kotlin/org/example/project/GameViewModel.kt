package org.example.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// GameViewModel.kt

class GameViewModel(
    private val gameEngine: DreamHunterGame
) : ViewModel() {
    
    private val _playerState = MutableStateFlow(
        Player(
            id = "1",
            name = "بطل الأحلام",
            dreamPoints = 0,
            level = 1,
            strengthBalls = emptyList(),
            completedTasks = emptyList(),
            unlockedPowers = emptyList()
        )
    )
    val playerState: StateFlow<Player> = _playerState.asStateFlow()
    
    init {
        generateInitialTasks()
    }
    
    fun completeTask(taskId: String) {
        viewModelScope.launch {
            val updatedPlayer = gameEngine.completeTask(_playerState.value, taskId)
            _playerState.value = updatedPlayer
        }
    }
    
    fun generateNewTasks() {
        viewModelScope.launch {
            val newTasks = gameEngine.getDailyTasks()
            newTasks.forEach { task ->
                val updatedPlayer = gameEngine.addTaskToPlayer(_playerState.value, task)
                _playerState.value = updatedPlayer
            }
        }
    }
    
    private fun generateInitialTasks() {
        generateNewTasks()
    }
}