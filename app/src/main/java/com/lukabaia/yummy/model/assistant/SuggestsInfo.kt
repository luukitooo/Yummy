package com.lukabaia.yummy.model.assistant

import com.google.gson.annotations.SerializedName

data class SuggestsInfo(
    val suggests: Suggest?
) {
    data class Suggest(
        @SerializedName("_")
        val names: List<Name>?
    ) {
        data class Name(
            val name: String?
        )
    }
}