package com.aadityaverma.solutionexplorer.data.datasource

data class Hobby(
    val color: String,
    val name: String,
    val iconUrl: String
)

data class Detail(
    val profession: String,
    val image: String,
    val completion: Int, // Updated to match the API data
//    val distance: List<Float?>,
    val distance: List<Float>,
    val calculateddistance: Float,
    val name: String,
    val location: String,
    val status: String,
    val hobbies: List<Hobby>? = null // Default to null
) {
    // Provide getters with null handling
    val safeHobbies: List<Hobby>
        get() = hobbies ?: listOf(
            Hobby(color = "null", name = "null", iconUrl = "null"),
            Hobby(color = "null", name = "null", iconUrl = "null"),
            Hobby(color = "null", name = "null", iconUrl = "null")
        )

    // Optional: Provide default values for new fields if necessary
    val safeCompletion: Int
        get() = completion

//    val safeDistance: List<Double>
//        get() = distance.ifEmpty { listOf(0.0, 0.0) }
}
