package com.example.tktmusicapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tktmusicapp.ui.theme.BackgroundDark

@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit
) {
    var query by remember { mutableStateOf(TextFieldValue("")) }

    val gradient = Brush.verticalGradient(
        listOf(
            Color(0xFF6C63FF),
            Color(0xFF352295),
            BackgroundDark
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // 🔙 Back button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // giảm từ 40dp xuống 16dp → gần top hơn

        // 🔍 Search Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp) // cao hơn
                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(14.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                BasicTextField(
                    value = query,
                    onValueChange = { query = it },
                    singleLine = true,
                    textStyle = TextStyle(color = Color.White, fontSize = 17.sp),
                    decorationBox = { innerTextField ->
                        if (query.text.isEmpty()) {
                            Text(
                                text = "Search songs, albums, artists...",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 15.sp
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = if (query.text.isEmpty()) "Type something to start searching 🔍"
            else "Showing results for: ${query.text}",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
