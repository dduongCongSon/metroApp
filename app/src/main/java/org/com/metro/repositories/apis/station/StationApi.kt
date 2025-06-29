package org.com.metro.repositories.apis.station

import org.com.metro.StationResponse
import retrofit2.Response
import retrofit2.http.GET

interface StationApi {
    @GET("api/stations") // The endpoint path relative to the base URL
    suspend fun getStations(): Response<StationResponse>
}