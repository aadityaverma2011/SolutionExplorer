package com.aadityaverma.solutionexplorer.data.datasource



data class Review(
    val review: String,
    val stars: Int
)


data class Product(
    val productId: String,
    val productName: String,
    val price: Int,
    val availableAt: String,
    val availability: Boolean,
    val aisle: String? = null, // Default to null if not applicable
    val productImageUrl: String,
    val reviews: List<Review>? = null // Default to null
) {
    // Provide getters with null handling
    val safeReviews: List<Review>
        get() = reviews ?: listOf(
            Review(review = "No review available", stars = 0)
        )

    // Optional: Provide default values for new fields if necessary
    val safeAisle: String
        get() = aisle ?: "N/A"
}
