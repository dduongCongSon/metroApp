package org.com.metro.ui.screens.metro.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.com.metro.Screen

// Định nghĩa bảng màu theo yêu cầu của bạn
private val BluePrimary = Color(0xFF2196F3)      // Màu xanh chủ đạo
private val BlueLight = Color(0xFF64B5F6)       // Màu xanh nhạt hơn, dùng cho primaryContainer
private val BlueDark = Color(0xFF1976D2)        // Màu xanh đậm hơn, dùng cho secondary

// Các màu phụ trợ để đảm bảo giao diện đẹp mắt
private val BackgroundLightGray = Color(0xFFF0F2F5) // Nền tổng thể nhẹ nhàng
private val TextDark = Color(0xFF333333)            // Màu chữ chính
private val TextMedium = Color(0xFF666666)          // Màu chữ phụ
private val WarningColor = Color(0xFFFF9800)       // Màu cam cho cảnh báo
private val WarningBackground = Color(0xFFFFF3E0)   // Nền nhẹ cho thẻ cảnh báo


@Composable
fun CCCDScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLightGray) // Áp dụng màu nền tổng thể
            .padding(horizontal = 16.dp, vertical = 16.dp) // Padding chung cho toàn màn hình
            .verticalScroll(rememberScrollState()), // Cho phép cuộn
        verticalArrangement = Arrangement.spacedBy(20.dp) // Khoảng cách giữa các phần tử
    ) {
        // Hero Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(16.dp),
            // Sử dụng BlueLight cho nền Hero Card
            colors = CardDefaults.cardColors(containerColor = BlueLight),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.CreditCard,
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                    tint = Color.White // Icon màu trắng trên nền BlueLight
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Liên kết CCCD gắn chip",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White, // Tiêu đề màu trắng
                    textAlign = TextAlign.Center
                )
            }
        }

        // Introduction Card
        InfoCard(
            title = "Giới thiệu",
            content = "Đối tượng học sinh, sinh viên bắt buộc phải liên kết " +
                    "định danh cá nhân để mua vé tháng HSSV trên app HCMC Metro HURC. Ngoài ra, hành khách " +
                    "đã mua vé tháng cũng có thể xác thực căn cước công dân gắn chip" +
                    " trên app để sử dụng các tính năng liên quan đến định danh " +
                    "cá nhân, như liên kết vé với CCCD.",
            icon = Icons.Default.Info
        )


        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionButton(
                text = "Xác thực Online",
                icon = Icons.Default.VerifiedUser,
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Screen.LinkCCCD.route) }
            )
            ActionButton(
                text = "Đăng ký mới",
                icon = Icons.Default.PersonAdd,
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Screen.RegisterCCCD.route) }
            )
        }

        // Features Grid
        Text(
            "Tính năng chính",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = BlueDark, // Tiêu đề tính năng chính màu BlueDark
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureCard(
                title = "Xác thực nhanh",
                description = "Xác thực CCCD chỉ trong vài phút",
                icon = Icons.Default.Speed,
                modifier = Modifier.weight(1f)
            )
            FeatureCard(
                title = "Bảo mật cao",
                description = "Mã hóa dữ liệu an toàn",
                icon = Icons.Default.Security,
                modifier = Modifier.weight(1f)
            )
        }

        // Note Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = WarningBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    tint = WarningColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Lưu ý: Mỗi căn cước công dân chỉ có thể được liên kết với một tài khoản Metro duy nhất.",
                    fontSize = 14.sp,
                    color = WarningColor
                )
            }
        }
    }
}

// InfoCard Composable
@Composable
fun InfoCard(
    title: String,
    content: String,
    icon: ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White), // Giữ nền trắng
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = BluePrimary, // Icon màu BluePrimary
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BlueDark // Tiêu đề màu BlueDark
                )
            }
            Text(
                content,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = TextMedium // Nội dung màu TextMedium
            )
        }
    }
}

// ActionButton Composable
@Composable
fun ActionButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(72.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = BluePrimary), // Nút màu BluePrimary
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = Color.White // Icon màu trắng
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White // Chữ màu trắng
            )
        }
    }
}

// FeatureCard Composable
@Composable
fun FeatureCard(
    title: String,
    description: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White), // Nền trắng
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = BluePrimary, // Icon màu BluePrimary
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = BlueDark, // Tiêu đề màu BlueDark
                textAlign = TextAlign.Center
            )
            Text(
                description,
                fontSize = 12.sp,
                color = TextMedium, // Mô tả màu TextMedium
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CCCDScreenPreview() {
    val navController = rememberNavController()
    CCCDScreen(navController = navController)
}