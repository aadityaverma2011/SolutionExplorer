package com.aadityaverma.solutionexplorer.presentation.refine

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import com.aadityaverma.solutionexplorer.R
import com.google.accompanist.flowlayout.FlowRow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefineScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf(
        "Available|Hey Let Us Connect",
        "Away| Stay Discrete And Watch",
        "Busy| Do Not Disturb | Will Catch Up Later",
        "SOS| Emergency Need Assistance! HELP"
    )
    val chips = listOf("Gym", "Friendship", "Horror", "Gaming", "Sports", "Business", "Yoga", "Singing")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(coffeeDrinks[0]) }
    var sliderValue by remember { mutableStateOf(1f) }
    var selectedChips by remember { mutableStateOf(setOf<String>()) } // State to track selected chips
    var statusText by remember { mutableStateOf("") } // State for TextField input

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(R.string.AvailabilityText),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .width(310.dp)
                    .height(70.dp),
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    TextField(
                        value = selectedText,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .height(56.dp),
                        textStyle = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    )
                }

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    coffeeDrinks.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item, fontSize = 12.sp) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(R.string.StatusText),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = statusText, // Use the state for text input
                onValueChange = { newText -> statusText = newText }, // Update the state with new text
                modifier = Modifier
                    .height(50.dp)
                    .width(310.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.StatusPlaceholder),
                        style = TextStyle(fontSize = 12.sp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(R.string.DistanceSelect),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Slider(
                        value = sliderValue,
                        onValueChange = { newValue -> sliderValue = newValue },
                        valueRange = 1f..1000f,
                        steps = 99,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Text(
                        text = "${sliderValue.toInt()}km",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(
                                x = (-480f + sliderValue - 1f) / 999f * (310.dp - 32.dp),
                                y = (-24).dp
                            )
                            .background(MaterialTheme.colorScheme.onPrimaryContainer)
                            .padding(8.dp)
                            .clip(shape = MaterialTheme.shapes.small)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "1km", fontSize = 12.sp)
                    Text(text = "1000km", fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(R.string.PurposeSelect),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Display chips in a FlowRow
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp
                ) {
                    chips.forEach { chip ->
                        ElevatedFilterChip(
                            selected = selectedChips.contains(chip), // Check if chip is selected
                            onClick = {
                                selectedChips = if (selectedChips.contains(chip)) {
                                    selectedChips - chip // Remove chip from selected set
                                } else {
                                    selectedChips + chip // Add chip to selected set
                                }
                            },
                            modifier = Modifier.padding(4.dp),
                            label = { Text(chip) },
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val chipsToSend = if (selectedChips.isEmpty()) chips.toSet() else selectedChips
                        val chipsQueryParam = chipsToSend.joinToString(",")
                        val distance = if (sliderValue <= 1f) "10000" else sliderValue.toInt().toString()
                        navController.navigate("explore_screen/$distance/$chipsQueryParam")
                    },
                    modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.extraLarge)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(text = stringResource(R.string.Save), fontSize = 20.sp, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}
