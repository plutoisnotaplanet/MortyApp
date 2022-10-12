package com.plutoisnotaplanet.mortyapp.application.data.rest.response

import com.google.gson.annotations.SerializedName

data class PagingInfoDto(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("pages")
    val pages: Int? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("prev")
    val prev: String? = null
) {
}