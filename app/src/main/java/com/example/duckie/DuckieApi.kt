package com.example.duckie

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

class DuckieApi {

    private val httpClient = HttpClient(OkHttp) {

        install(DefaultRequest) {
            url("https://random-d.uk/api/v2")
        }

        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun quack(): Duckie? {
        return httpClient.get("quack").body()
    }

}

