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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DoubleSwitch(
    options: List<String> = listOf("Day", "Week"),
    selectedIndex: Int = 0,
    onSelectedChange: (Int) -> Unit,
    size: Int = 30,
    padding: Dp = 25.dp
) {
    val listLength = arrayListOf(0,0)
    options.forEachIndexed { index, string -> 
        listLength[index] = string.length
    }
    
    val totalWidth = (listLength.sum() * size/2).dp
    
    val animatedOffset by animateDpAsState(
        targetValue = if (selectedIndex == 0) 0.dp else (totalWidth / 2) - padding/2,
        label = "switchOffset"
    )

    Box(
        modifier = Modifier
            .width(totalWidth)
            .height(size.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFF2A2A2A))
            .padding(end = padding)
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
                        .clickable { onSelectedChange(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        color = if (selectedIndex == index) Color.Black else Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = (size/3).sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DoubleSwitchPreview() {
    DoubleSwitch(
        selectedIndex = 0,
        onSelectedChange = {}
    )
}
