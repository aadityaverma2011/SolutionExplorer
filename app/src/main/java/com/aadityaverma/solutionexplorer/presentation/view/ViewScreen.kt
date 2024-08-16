package com.aadityaverma.solutionexplorer.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.aadityaverma.solutionexplorer.R
import com.aadityaverma.solutionexplorer.data.datasource.Product
import com.aadityaverma.solutionexplorer.data.datasource.Review
import com.aadityaverma.solutionexplorer.presentation.explore.ExploreViewModel
import com.aadityaverma.solutionexplorer.ui.theme.SolutionExplorerTheme

@Composable
fun ViewScreen(
    navController: NavController,
    product: Product,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            model = R.drawable.walmartrectangle,
            contentDescription = "logo"
        )
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            model = product.productImageUrl,
            contentDescription = "product photo"
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = product.productName,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Stock: X", style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "₹ ${product.price}",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Reviews",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(product.safeReviews) { review ->
                ReviewCard(review = review)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = if (product.availability) {
                    "Location: ${product.safeAisle}"
                } else {
                    "Not available in the current store but can be ordered online from ${product.availableAt} store."
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly, Alignment.Bottom
        ) {
            Button(
                onClick = { /* Handle "Shop Current" button click */ },
                enabled = product.availability, // Disable if product is not available
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Shop From Current")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { /* Handle "Order Online" button click */ },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Order Online")
            }
        }
    }
}

@Composable
fun ReviewCard(review: Review) {
    Card(
        modifier = Modifier
            .padding(end = 8.dp)
            .width(150.dp),

        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Row {
                repeat(review.stars) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                repeat(5 - review.stars) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star Border",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = review.review,
                maxLines = 3,
                style = MaterialTheme.typography.bodySmall
            )


        }
    }
}





val sampleProduct = Product(
    productId = "1",
    productName = "Sample Product",
    price = 19,
    availableAt = "Store A",
    availability = false,
    aisle = "Aisle 5",
    productImageUrl = "https://pngfile.net/download/OoeOLg1l9LwBmHztf7bChEPclYEXuAe3cFv5lpvWCcmMkXr13cIi7r2baLBSqE9bdHB2AEASkewdSU8m3h2JWKO81WUbzUCrsEjOm6MDP7TJIZyBAUVgaEEBwGe50TYDWU4iDjNQ4AVguYsNMwuPJRNyFtFNMrVV6Ngns1LnbmQEAMdsbO8fup0HN8t2IVtPWhIgyfzQ/large",
    reviews = listOf(
        Review(review = "Great product!", stars = 5),
        Review(review = "Highly recommend.", stars = 4)
    )
)

@Preview(showBackground = true)
@Composable
fun ViewScreenPreview() {
    // Mock NavController
    val navController = rememberNavController()

    // Wrap in the theme for consistency with your app's styling
    SolutionExplorerTheme {
        ViewScreen(
            navController = navController,
            product = sampleProduct
        )
    }
}