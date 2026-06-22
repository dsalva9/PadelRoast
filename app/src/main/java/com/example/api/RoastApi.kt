package com.example.api

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

// --- Request model for Supabase Edge Function ---


@JsonClass(generateAdapter = true)
data class RoastRequest(
    val players: List<String>,
    val scores: List<List<Int>>,
    val tone: String,
    val trigger: String,
    val tags: List<String>,
    val history_context: String,
    val tier: String,
    val locale: String,
    val game_type: String = "standard",
    val custom_context: String? = null
)

// --- Response models (kept from original, unchanged) ---

@JsonClass(generateAdapter = true)
data class PlayerTitles(
    val player_1: String = "Underdog",
    val player_2: String = "Baseline Hugger",
    val player_3: String = "Fence Smasher",
    val player_4: String = "No-Show"
)

@JsonClass(generateAdapter = true)
data class RoastCardText(
    val headline: String,
    val body_text: String,
    val pre_filled_whatsapp: String,
    val match_recap: String? = null,
    val mvp_line: String? = null,
    val rematch_hype: String? = null
)

@JsonClass(generateAdapter = true)
data class RoastResponseSchema(
    val narrative_trigger_detected: String,
    val player_titles_assigned: PlayerTitles,
    val roast_cards: List<RoastCardText>,
    val match_recap: String,
    val mvp_line: String,
    val rematch_hype: String
)

@JsonClass(generateAdapter = true)
data class TranscriptionResponse(
    val text: String
)

// --- Retrofit service for Supabase Edge Function ---

interface RoastApiService {
    @POST("functions/v1/generate-roast")
    suspend fun generateRoast(
        @Header("Authorization") authHeader: String,
        @Header("apikey") apiKey: String,
        @Body request: RoastRequest
    ): RoastResponseSchema

    @Multipart
    @POST("functions/v1/transcribe-speech")
    suspend fun transcribeSpeech(
        @Header("Authorization") authHeader: String,
        @Header("apikey") apiKey: String,
        @Part file: MultipartBody.Part
    ): TranscriptionResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://obkgcybxanfsmhcwarsn.supabase.co/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val service: RoastApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(RoastApiService::class.java)
    }

    val moshiParser: Moshi = moshi
}
