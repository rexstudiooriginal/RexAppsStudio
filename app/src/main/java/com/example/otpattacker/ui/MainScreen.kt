package com.example.otpattacker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.otpattacker.viewmodel.OtpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: OtpViewModel) {
    val phone by viewModel.phone.collectAsState()
    val mode by viewModel.mode.collectAsState()
    val result by viewModel.result.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Banner sederhana (tanpa ASCII)
        Text(
            text = "⚡ OTP ATTACKER ⚡",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Input nomor
        OutlinedTextField(
            value = phone,
            onValueChange = { viewModel.updatePhone(it) },
            label = { Text("Nomor Target (08xxx)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Spinner Mode
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val modes = listOf("single", "loop", "pairing", "status", "info")
            modes.forEach { m ->
                FilterChip(
                    selected = mode == m,
                    onClick = { viewModel.updateMode(m) },
                    label = { Text(m.uppercase()) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol Kirim
        Button(
            onClick = { viewModel.sendOtp() },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Mengirim...")
            } else {
                Text("🚀 KIRIM OTP")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hasil
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                text = result.ifEmpty { "Hasil akan muncul di sini" },
                modifier = Modifier.padding(16.dp),
                fontSize = 14.sp
            )
        }
    }
}