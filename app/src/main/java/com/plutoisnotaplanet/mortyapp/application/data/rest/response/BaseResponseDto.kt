package com.plutoisnotaplanet.mortyapp.application.data.rest.response

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.mortyapp.application.domain.model.BaseResponse

data class BaseResponseDto<T>(
    @SerializedName("info")
    val pagingInfo: PagingInfoDto?,
    @SerializedName("results")
    val results: List<T>
) {

    fun <M>toModel(list: List<M>): BaseResponse<M> {
        return BaseResponse(
            pagingInfo = pagingInfo?.toModel(),
            results = list
        )
    }
}