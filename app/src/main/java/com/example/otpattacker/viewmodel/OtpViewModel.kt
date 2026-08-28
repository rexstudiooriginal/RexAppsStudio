package com.example.otpattacker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otpattacker.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OtpViewModel : ViewModel() {
    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    private val _mode = MutableStateFlow("single")
    val mode: StateFlow<String> = _mode

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun updatePhone(value: String) {
        _phone.value = value
    }

    fun updateMode(value: String) {
        _mode.value = value
    }

    fun sendOtp() {
        val phoneNumber = _phone.value.trim()
        if (phoneNumber.isEmpty()) {
            _result.value = "⚠️ Nomor tidak boleh kosong"
            return
        }
        // Format nomor sederhana (hanya digit, tambahkan 62 jika mulai 0)
        val formatted = phoneNumber.replace(Regex("[^0-9]"), "").let {
            if (it.startsWith("0")) "62${it.drop(1)}" else if (it.startsWith("62")) it else "62$it"
        }

        _isLoading.value = true
        _result.value = "Mengirim..."

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.sendOtp(
                    phone = formatted,
                    mode = _mode.value
                )
                _result.value = if (response.success) {
                    "✅ ${response.message ?: "Berhasil"}"
                } else {
                    "❌ ${response.message ?: response.error ?: "Gagal"}"
                }
            } catch (e: Exception) {
                _result.value = "❌ Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}