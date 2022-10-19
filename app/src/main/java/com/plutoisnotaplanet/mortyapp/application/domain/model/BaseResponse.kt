package com.plutoisnotaplanet.mortyapp.application.domain.model

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.mortyapp.application.data.rest.response.PagingInfoDto

data class BaseResponse<T>(
    val pagingInfo: PagingInfo?,
    val results: List<T>
) {
}