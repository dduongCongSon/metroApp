package org.com.metro.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.com.metro.repositories.apis.station.BusStationRepository
import org.com.metro.repositories.apis.ticket.FareMatrixRepository
import org.com.metro.repositories.apis.station.MetroStationRepository
import org.com.metro.repositories.SharedPreferencesTokenProvider
import org.com.metro.repositories.apis.ticket.TicketRepository
import org.com.metro.repositories.apis.auth.AuthApi
import org.com.metro.repositories.apis.order.OrderSingleApi
import org.com.metro.repositories.apis.station.BusStationApi
import org.com.metro.repositories.apis.ticket.FareMatrixApi
import org.com.metro.repositories.apis.station.MetroStationApi
import org.com.metro.repositories.apis.station.StationApi
import org.com.metro.repositories.apis.station.StationRepository
import org.com.metro.repositories.apis.user.ProfileApi
import org.com.metro.repositories.apis.ticket.TicketApi
import org.com.metro.security.TokenProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val BASE_URL = "http://10.0.2.2:4003/"
    private val BASE_BLOG = "http://10.0.2.2:4007/"
    private val BASE_STATION = "http://192.168.88.172:4004/"
    private val BASE_PHONE = "http://192.168.1.14:4003/"
    private val BASE_STATION_ = "http://10.0.2.2:4004/"

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    @Named("Auth")
    fun provideAuthInterceptor(tokenProvider: TokenProvider): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val token = tokenProvider.getToken()

            val requestBuilder: Request.Builder = original.newBuilder()
            token?.let {
                requestBuilder.header("Authorization", "Bearer $it")
            }

            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideTokenProvider(@ApplicationContext context: Context): TokenProvider {
        val sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        return SharedPreferencesTokenProvider(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideCookieManager(): CookieManager {
        return CookieManager().apply {
            setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("Auth")   authInterceptor: Interceptor,
        @Named("ApiKey") apiKeyInterceptor: Interceptor,
        cookieManager: CookieManager
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .cookieJar(JavaNetCookieJar(cookieManager))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ProfileApi {
        return retrofit.create(ProfileApi::class.java)
    }
    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }


    @Provides
    @Singleton
    @Named("ApiKey")
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Content-Type", "application/json") // luôn set JSON
                .header(
                    "x-api-key",
                    "c761c9f0bb379612afbfd6ffeca90261db961bb93bce17728bc2a74430a66c0a"
                ) // API key của bạn
                .build()
            chain.proceed(request)
        }
    }

    //metro station api
    @Provides
    @Singleton
    fun provideMetroStationApi(
        @Named("stationRetrofit") retrofit: Retrofit
    ): MetroStationApi {
        return retrofit.create(MetroStationApi::class.java)
    }

    @Provides
    @Named("stationRetrofit")
    @Singleton
    fun provideStationRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_STATION_)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMetroStationRepository(api: MetroStationApi): MetroStationRepository {
        return MetroStationRepository(api)
    }

    //mock bus station api
    @Provides
    @Singleton
    fun provideBusStationApi(
        @Named("busRetrofit") retrofit: Retrofit
    ): BusStationApi {
        return retrofit.create(BusStationApi::class.java)
    }

    @Provides
    @Named("busRetrofit")
    @Singleton
    fun provideMockyRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_STATION)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideBusStationRepository(api: BusStationApi): BusStationRepository {
        return BusStationRepository(api)
    }

    @Provides
    @Singleton
    fun provideTicketApi(

         retrofit: Retrofit // Inject the specific Retrofit instance
    ): TicketApi {
        return retrofit.create(TicketApi::class.java)
    }

    // Provide the TicketRepository
    @Provides
    @Singleton
    fun provideTicketRepository(api: TicketApi): TicketRepository {
        return TicketRepository(api)
    }

    @Provides
    @Singleton
    fun provideFareMatrixApi(
        retrofit: Retrofit // Reusing the main Retrofit instance
    ): FareMatrixApi {
        return retrofit.create(FareMatrixApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFareMatrixRepository(api: FareMatrixApi): FareMatrixRepository {
        return FareMatrixRepository(api)
    }

    // Stations
    @Provides
    @Singleton
    fun provideStationApi(
        retrofit: Retrofit // Reusing the main Retrofit instance
    ): StationApi {
        return retrofit.create(StationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStationRepository(api: StationApi): StationRepository {
        return StationRepository(api)
    }

    //order
    @Provides
    @Singleton
    fun provideOrderApi(
        retrofit: Retrofit
    ): OrderSingleApi {
        return retrofit.create(OrderSingleApi::class.java)
    }
}