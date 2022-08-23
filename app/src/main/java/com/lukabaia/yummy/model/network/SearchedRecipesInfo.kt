package com.lukabaia.yummy.model.network

data class SearchedRecipesInfo(
    val results: List<SearchedRecipe>?,
    val offset: Int?,
    val number: Int?,
    val totalResults: Int?,
    ) {
    data class SearchedRecipe(
        val id: Int?,
        val title: String?,
        val image: String?,
        val imageType: String?,
        val nutrition: Nutrition?,
    ) {
        data class Nutrition(
            val nutrients: List<Nutrient>?,
        ) {
            data class Nutrient(
                val name: String?,
                val amount: Double?,
                val unit: String?,
            )
        }
    }
}
