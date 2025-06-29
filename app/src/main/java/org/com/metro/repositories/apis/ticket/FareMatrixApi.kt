package org.com.metro.repositories.apis.ticket

import com.google.gson.annotations.SerializedName
import org.com.metro.FareMatricesResponse
import org.com.metro.FareMatrix
import org.com.metro.FareMatrixResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FareMatrixApi {

    @GET("api/ts/fare-matrices")
    suspend fun getFareMatrices(): Response<FareMatricesResponse>

    @POST("api/ts/fare-matrices/get-fare")
    suspend fun getFareForRoute(
        @Body fareRequest: FareRequest
    ): Response<FareMatrixResponse>
}

data class FareRequest(
    @SerializedName("startStationId")
    val startStationId: Int,

    @SerializedName("endStationId")
    val endStationId: Int
)
