package com.aadityaverma.solutionexplorer.presentation.components

import android.graphics.Color.parseColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.aadityaverma.solutionexplorer.R
import com.aadityaverma.solutionexplorer.ui.theme.newColor
import com.aadityaverma.solutionexplorer.ui.theme.newColor2

import com.aadityaverma.solutionexplorer.data.datasource.Detail
import com.aadityaverma.solutionexplorer.domain.LocationManager
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Async

fun String.toColor(): Color {
    return try {
        Color(this.toLong(16))
    } catch (e: IllegalArgumentException) {
        Color.Black // Default color in case of error
    }
}
@Composable
fun DetailCard(
    modifier: Modifier = Modifier,
    detail: Detail,
    onClick: (() -> Unit)? = null
) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color.LightGray, Color.White ),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Card(
        modifier = modifier
            .clickable { onClick?.invoke() }
            .padding(8.dp).padding(8.dp).drawSharpCorners(
                topLeft = true,
                bottomRight = true,
                color = Color.LightGray// Background color for sharp corners
            ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent), // Make containerColor transparent

    ) {
        Box(
            modifier = Modifier
                .background(gradientBrush)
                .padding(12.dp) // Adjust padding as needed
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color.Transparent)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(MaterialTheme.shapes.medium),
                        model = R.drawable.profile,
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = detail.name.takeIf { it.isNotEmpty() } ?: "null",
                        maxLines = 1,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black,
                    )
                    Divider(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .width(140.dp)
                    )
                    Row {
                        Text(
                            text = detail.location.takeIf { it.isNotEmpty() } ?: "null",
                            maxLines = 1,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        AsyncImage(model = R.drawable.divider, contentDescription = "divider", modifier = Modifier.size(8.dp).align(Alignment.CenterVertically))
                        Text(
                            modifier = Modifier.align(alignment = Alignment.CenterVertically),
                            text = detail.profession.takeIf { it.isNotEmpty() } ?: "null",
                            maxLines = 1,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${detail.calculateddistance.toInt()} km away",
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Profile", style = MaterialTheme.typography.labelSmall)
                    LinearProgressIndicator(
                        progress = detail.completion / 100f, // Assuming detail.completion ranges from 0 to 100
                        modifier = Modifier.width(140.dp).clip(shape = MaterialTheme.shapes.extraLarge),
                        color = Color.Black,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Display hobbies in a row
                    Row(modifier = Modifier.fillMaxWidth()) {
                        var count = 3
                        detail.hobbies?.take(3)?.forEach { hobby ->
                            Row(
                                modifier = Modifier
                            ) {
                                AsyncImage(
                                    model = hobby.iconUrl,
                                    contentDescription = "icon",
                                    Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = hobby.name,
                                    maxLines = 1,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                    color = parseColorSafe(hobby.color)
                                )
                                count -= 1
                                if (count >= 1) {
                                    AsyncImage(model = R.drawable.divider, contentDescription = "divider", modifier = Modifier.size(8.dp).align(Alignment.CenterVertically))
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .height(4.dp)
                            .zIndex(999f)
                    )
                    Text(
                        modifier = Modifier.zIndex(999f),
                        text = detail.status.takeIf { it.isNotEmpty() } ?: "null",
                        maxLines = 2,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End
                ) {
                    AsyncImage(
                        model = R.drawable.invite,
                        contentDescription = "",
                        modifier = Modifier.size(24.dp).clickable { /* Handle click */ },
                    )
                }
            }
        }
    }
}


fun Modifier.drawSharpCorners(
    topLeft: Boolean = false,
    bottomRight: Boolean = false,
    color: Color = Color.Transparent
) = this.then(
    Modifier.drawBehind {
        val radius = 16.dp.toPx() // Adjust radius as needed

        drawRoundRect(
            color = color,
            size = size,
            cornerRadius = CornerRadius(
                x = if (topLeft) radius else 0f,
                y = if (topLeft) radius else 0f
            )
        )
        drawRoundRect(
            color = color,
            size = size,
            cornerRadius = CornerRadius(
                x = if (bottomRight) radius else 0f,
                y = if (bottomRight) radius else 0f
            ),
            topLeft = if (topLeft) Offset(radius, radius) else Offset.Zero,
        )
    }
)
fun parseColorSafe(colorString: String): Color {
    return try {
        if (colorString.startsWith("rgb")) {
            val rgbValues = colorString.removePrefix("rgb(").removeSuffix(")").split(",")
            val red = rgbValues[0].trim().toInt()
            val green = rgbValues[1].trim().toInt()
            val blue = rgbValues[2].trim().toInt()
            Color(red, green, blue)
        } else {
            Color(parseColor(colorString))
        }
    } catch (e: IllegalArgumentException) {
        Color.Black
    }
}

//@Preview
//@Composable
//fun DetailCardPreview() {
//    DetailCard(detail = Detail(
//        profession = "Engineer",
//        image = "",
//        distance = 0,
//        name = "John Doe",
//        location = "San Francisco",
//        status = "Active",
//        hobbies = listOf(
//            Hobby(color = "#FF5733", name = "Coding", iconUrl = ""),
//            Hobby(color = "#33FF57", name = "Reading", iconUrl = ""),
//            Hobby(color = "#3357FF", name = "Gaming", iconUrl = "")
//        ),
//        locationLatitude = 37.7749,
//        locationLongitude = -122.4194,
//        completion = 75
//    ))
//}