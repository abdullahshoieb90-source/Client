
package com.bedrock.client.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bedrock.client.repository.LauncherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LauncherViewModel(private val repo: LauncherRepository = LauncherRepository()) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun launchGame(instanceId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repo.launch(instanceId)
            _uiState.value = if (result.isSuccess) UiState.Success else UiState.Error(result.exceptionOrNull()?.message ?: "Error")
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val msg: String) : UiState()
    }
}
