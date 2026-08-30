package com.rex.tools.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.rex.tools.data.local.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState

    fun login(email: String, password: String, navController: NavController) {
        if (email.isEmpty() || password.isEmpty()) {
            _loginState.value = _loginState.value.copy(error = "Email & password wajib diisi")
            return
        }

        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(isLoading = true, error = null)

            // hardcode dulu, nanti bisa diganti
            if (email == "rex@tools.com" && password == "123456") {
                userPreferences.saveUser(email, password)
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                _loginState.value = _loginState.value.copy(
                    isLoading = false,
                    error = "Email atau password salah"
                )
            }
        }
    }
}
