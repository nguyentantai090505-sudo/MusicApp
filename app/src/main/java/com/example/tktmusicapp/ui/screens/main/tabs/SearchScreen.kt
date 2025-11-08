package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.tktmusicapp.ui.theme.GradientEnd
import com.example.tktmusicapp.ui.theme.GradientMiddle
import com.example.tktmusicapp.ui.theme.GradientStart
import com.example.tktmusicapp.ui.theme.TextHint
import com.example.tktmusicapp.ui.theme.TextPrimary

@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
) {
    var query by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GradientStart,
                        GradientMiddle,
                        GradientEnd
                    )
                )
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 🔙 Nút Back
        IconButton(onClick = onNavigateBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 🔍 Ô tìm kiếm
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search songs, albums, artists...", color = TextHint) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextHint) },
            textStyle = LocalTextStyle.current.copy(color = TextPrimary),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Type something to start searching 🔎",
            color = TextHint
        )
    }
}
