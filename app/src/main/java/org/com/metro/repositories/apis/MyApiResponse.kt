package org.com.metro.repositories.apis

data class MyApiResponse<T>(
    val status: Int,
    val message: String,
    val data: T?
) {


}