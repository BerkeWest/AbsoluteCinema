package com.example.absolutecinema.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DoubleSwitch(
    modifier: Modifier = Modifier,
    options: List<String> = listOf("Day", "Week"),
    selectedIndex: Int?,
    onSelectedChange: (Int) -> Unit
) {
    val animatedOffset by animateDpAsState(
        targetValue = (selectedIndex?.times(50))?.dp ?: 0.dp,
        label = "switchOffset"
    )

    Box(
        modifier = modifier
            .fillMaxWidth(0.3f)
            .fillMaxHeight(0.5f)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFF2A2A2A))
            .border(width = 1.dp, color = Color(0xFF2A80FF), shape = RoundedCornerShape(50)),
    ) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset)
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .background(Color(0xFF2A80FF))
        )
        Row(Modifier.fillMaxSize()) {
            options.forEachIndexed { index, text ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onSelectedChange },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        color = if (selectedIndex == index) Color.Black else Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}