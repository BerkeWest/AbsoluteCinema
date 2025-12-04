package com.example.absolutecinema.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.absolutecinema.R
import com.example.absolutecinema.presentation.theme.BackgroundColor
import com.example.absolutecinema.presentation.theme.RatingColor
import com.example.absolutecinema.presentation.theme.SelectedColor
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    sheetState: SheetState,
    initialValue: Float = 5f,
    onDismiss: () -> Unit,
    onRatingSelected: (Float?) -> Unit,
    rated: Boolean = false
) {
    var rating by remember { mutableFloatStateOf(initialValue) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = BackgroundColor,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Close button
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = stringResource(R.string.close_button),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onDismiss() },
                    tint = Color.White
                )
            }

            Text(
                text = stringResource(R.string.rate_this_movie),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )

            Text(
                text = rating.toString(),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Slider(
                value = rating,
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(RatingColor, CircleShape)
                    )
                },
                onValueChange = { rating = (it / 0.5f).roundToInt() * 0.5f },
                valueRange = 0.5f..10f,
                steps = 0,
                colors = SliderDefaults.colors(
                    thumbColor = RatingColor,
                    activeTrackColor = RatingColor,
                    inactiveTrackColor = Color.LightGray,
                ),
                modifier = Modifier.padding(vertical = 24.dp)
            )
            if (!rated) {
                Button(
                    onClick = {
                        onRatingSelected(rating)
                        onDismiss()
                    },
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SelectedColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(40.dp)
                ) {
                    Text(
                        text = stringResource(R.string.save_rating),
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            onRatingSelected(rating)
                            onDismiss()
                        },
                        shape = RoundedCornerShape(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SelectedColor
                        ),
                        modifier = Modifier
                            .fillMaxHeight(0.08f)
                    ) {
                        Text(
                            text = stringResource(R.string.edit_rating),
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.fillMaxWidth(0.08f))
                    Button(
                        onClick = {
                            onRatingSelected(null)
                            onDismiss()
                        },
                        shape = RoundedCornerShape(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        modifier = Modifier
                            .fillMaxHeight(0.08f)
                    ) {
                        Text(
                            text = stringResource(R.string.delete_rating),
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun BottomSheetPreview() {
    val fakeState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    BottomSheet(
        onDismiss = {},
        onRatingSelected = {},
        sheetState = fakeState,
        initialValue = 5f,
        rated = true
    )
}