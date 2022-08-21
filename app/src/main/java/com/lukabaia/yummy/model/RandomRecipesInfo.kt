package com.lukabaia.yummy.model

data class RandomRecipesInfo(
    val recipes: List<RandomRecipe>?
) {
    data class RandomRecipe(
        val vegetarian: Boolean?,
        val vegan: Boolean?,
        val glutenFree: Boolean?,
        val dairyFree: Boolean?,
        val veryHealthy: Boolean?,
        val cheap: Boolean?,
        val veryPopular: Boolean?,
        val healthScore: Int?,
        val pricePerServing: Double?,
        val extendedIngredients: List<Ingredient>?,
        val id: Long?,
        val title: String?,
        val readyInMinutes: Int?,
        val sourceUrl: String?,
        val image: String?,
        val summary: String?,
        val dishTypes: List<String>?,
        val diets: List<String>?,
        val instructions: String?,
        val analyzedInstructions: List<Instruction>?
    ) {
        data class Ingredient(
            val id: Long?,
            val aisle: String?,
            val consistency: String?,
            val name: String?,
            val nameClean: String?,
            val original: String?,
            val originalName: String?,
            val amount: Double?,
            val unit: String?
        )
        data class Instruction(
            val name: String?,
            val steps: List<Step>?
        ) {
            data class Step(
                val number: Int?,
                val step: String?,
                val ingredients: List<IngredientDemo>?,
                val equipment: List<Equipment>?
            ) {
                data class IngredientDemo(
                    val id: Long?,
                    val name: String?,
                )
                data class Equipment(
                    val id: Long?,
                    val name: String?
                )
            }
        }
    }
}
