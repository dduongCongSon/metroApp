package org.com.metro.repositories.apis.auth

interface TokenProvider {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}