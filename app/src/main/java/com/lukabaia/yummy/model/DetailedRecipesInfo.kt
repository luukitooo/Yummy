package com.lukabaia.yummy.model

data class DetailedRecipesInfo(
    val vegetarian: Boolean?,
    val vegan: Boolean?,
    val dairyFree: Boolean?,
    val veryHealthy: Boolean?,
    val cheap: Boolean?,
    val veryPopular: Boolean?,
    val sustainable: Boolean?,
    val lowFodmap: Boolean?,
    val weightWatcherSmartPoints: Int?,
    val gaps: String?,
    val preparationMinutes: Int?,
    val cookingMinutes: Int?,
    val aggregateLikes: Int?,
    val healthScore: Int?,
    val creditsText: String?,
    val license: String?,
    val sourceName: String?,
    val pricePerServing: Double?,
    val extendedIngredients: List<ExtendedIngredients>,
    val id: Long?,
    val title: String?,
    val readyInMinutes: Int?,
    val servings: Int?,
    val glutenFree: Boolean?,
    val sourceUrl: String?,
    val image: String?,
    val imageType: String?,
    val nutrition: Nutrition,
    val summary: String?,
    val cuisines: List<Any>?,
    val dishTypes: List<String>?,
    val diets: List<Any>?,
    val occasions: List<Any>?,
    val winePairing: WinePairing?,
    val instructions: String?,
    val analyzedInstructions: List<Any>?,
    val originalId: Any?,
    val spoonacularSourceUrl: String,
) {
    data class ExtendedIngredients(
        val id: Int?,
        val aisle: String?,
        val image: String,
        val consistency: String?,
        val name: String?,
        val nameClean: String?,
        val original: String?,
        val originalName: String?,
        val amount: Double?,
        val unit: String?,
        val meta: List<String>,
        val measures: Measures,
    ) {
        data class Measures(
            val us: Us,
            val metric: Metric,
        ) {
            data class Metric(
                val amount: Double,
                val unitShort: String,
                val unitLong: String,
            )

            data class Us(
                val amount: Double,
                val unitShort: String,
                val unitLong: String,
            )
        }
    }
    data class Nutrition(
        val nutrients: List<Nutrient>,
        val caloricBreakdown: CaloricBreakdown,
        val flavonoids: List<Flavonoid>,
        val ingredients: List<Ingredient>,
        val properties: List<Property>,
        val weightPerServing: WeightPerServing,
    ) {
        data class Nutrient(
            val name: String?,
            val amount: Double?,
            val unit: String?,
            val percentOfDailyNeeds: Double?,
        )

        data class Property(
            val name: String?,
            val amount: Double?,
            val unit: String?,
        )

        data class Flavonoid(
            val name: String?,
            val amount: Double?,
            val unit: String?,
        )

        data class Ingredient(
            val id: Int?,
            val name: String?,
            val amount: String?,
            val unit: String?,
            val nutrients: List<Nutrient>,
        )

        data class CaloricBreakdown(
            val percentProtein: Double?,
            val percentFat: Double?,
            val percentCarbs: Double?,
        )

        data class WeightPerServing(
            val amount: Int?,
            val unit: String?,
        )
    }

    data class WinePairing(
        val pairedWines: List<Any>?,
        val pairingText: String?,
        val productMatches: List<Any>?,
    )
}
