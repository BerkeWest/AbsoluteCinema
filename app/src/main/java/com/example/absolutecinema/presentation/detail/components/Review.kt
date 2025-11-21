package com.example.absolutecinema.presentation.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.absolutecinema.BuildConfig
import com.example.absolutecinema.R

@Composable
fun Review(
    author: String,
    rating: String,
    content: String,
    avatarPath: String?
) {

    var expand by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clickable(onClick = { expand = !expand })
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(BuildConfig.AVATAR_URL + avatarPath)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.account),
                placeholder = painterResource(R.drawable.account),
                contentDescription = author,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = rating,
                color = Color.Cyan
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Column {
            Text(
                text = author,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = content,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                overflow = if (expand) TextOverflow.Visible else TextOverflow.Ellipsis,
                maxLines = if (expand) Int.MAX_VALUE else 2,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }

}

@Preview
@Composable
fun ReviewPreview() {
    Review(
        author = "John Doe",
        rating = "5/10",
        content = "This movie was amazing! I loved every minute of it.",
        avatarPath = null
    )
}