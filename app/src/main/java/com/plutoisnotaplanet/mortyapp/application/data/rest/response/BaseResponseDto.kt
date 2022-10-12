package com.plutoisnotaplanet.mortyapp.application.data.rest.response

import com.google.gson.annotations.SerializedName

data class BaseResponseDto<T>(
    @SerializedName("info")
    val pagingInfo: PagingInfoDto?,
    @SerializedName("results")
    val results: List<T>
) {
}