package org.com.metro.repositories.apis.user

import org.com.metro.model.UserProfile
import retrofit2.Response
import retrofit2.http.GET

interface ProfileApi {
    @GET("profiles")
    suspend fun getProfiles(): Response<List<UserProfile>>
}