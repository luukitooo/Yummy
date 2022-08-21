package com.lukabaia.yummy.model

data class SearchedRecipesInfo(
    val results: List<Results>?,
    val offset: Int?,
    val number: Int?,
    val totalResults: Int?,
    ) {
    data class Results(
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
