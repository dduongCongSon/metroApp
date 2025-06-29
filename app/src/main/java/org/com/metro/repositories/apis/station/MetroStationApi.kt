package org.com.metro.repositories.apis.station

import org.com.metro.dto.MyApiResponse
import org.com.metro.model.Station
import retrofit2.Response
import retrofit2.http.GET

interface MetroStationApi {
    @GET("/api/stations")
    suspend fun getStations(): Response<MyApiResponse<List<Station>>>
}
