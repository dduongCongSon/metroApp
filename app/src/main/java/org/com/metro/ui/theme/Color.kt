package org.com.metro.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val GreenPrimary = Color(0xFF347433)
val LightOrange = Color(0xFFFF6F3C)
val LightYellow = Color(0xFFFFC107)
val LightBeige = Color(0xFFB4D2BA)
val PaleYellow = Color(0xFFDCE2AA)
val EarthBrown = Color(0xFFB57F50)
val DarkGreen = Color(0xFF4B543B)
val ErrorRed = Color(0xFFD32F2F)

val BluePrimary = Color(0xFF2196F3)      // Màu xanh chủ đạo
val BlueLight = Color(0xFF64B5F6)       // Màu xanh nhạt hơn, dùng cho primaryContainer
val BlueDark = Color(0xFF1976D2)        // Màu xanh đậm hơn, dùng cho secondary

val AppWhite = Color(0xFFFFFFFF)        // Trắng tinh khiết
val AppLightGray = Color(0xFFF5F5F5)    // Rất nhạt, tốt cho nền background
val AppMediumGray = Color(0xFF757575)   // Xám trung bình cho văn bản phụ, icon phụ
val AppDarkGray = Color(0xFF212121)     // Xám đậm cho văn bản chính
val AppRedError = Color(0xFFE53935)     // Màu đỏ cho lỗi/hành động nguy hiểm (logout)
val AppLightRed = Color(0xFFFFEBEE)     // Nền nhạt cho màu đỏ (errorContainer)
val AppCyan = Color(0xFF00BCD4)         // Màu Cyan bạn đã dùng cho secondary trước đây



val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = BlueDark,
    surface = LightBeige,
    onSurface = DarkGreen,
    secondary = EarthBrown,
    onSecondary = Color.White
)

val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    onPrimary = Color.Black,
    secondary = EarthBrown,
    onSecondary = Color.White,
    background = Color.White,
    surface = LightBeige,
)
