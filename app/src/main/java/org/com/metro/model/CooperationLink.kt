package org.com.metro.model

data class CooperationLink(
    val id: String,
    val title: String,
    val iconRes: Int,
    val tapUrl: String = "https://metro-fe.vercel.app"
)