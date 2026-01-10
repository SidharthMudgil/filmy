package com.sidharth.network.datasource.response


import com.google.gson.annotations.SerializedName
import com.sidharth.network.datasource.response.auxiliary.ResultResponse

data class MovieSearchResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<ResultResponse?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)