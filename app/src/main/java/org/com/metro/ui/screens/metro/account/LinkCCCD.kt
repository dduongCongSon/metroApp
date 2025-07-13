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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

// Định nghĩa lại bảng màu theo yêu cầu
private val BluePrimary = Color(0xFF2196F3)      // Màu xanh chủ đạo
private val BlueLight = Color(0xFF64B5F6)       // Màu xanh nhạt hơn, dùng cho primaryContainer
private val BlueDark = Color(0xFF1976D2)        // Màu xanh đậm hơn, dùng cho secondary

// Các màu phụ trợ để tạo giao diện đẹp mắt hơn
private val BackgroundLightGray = Color(0xFFF0F2F5) // Nền tổng thể nhẹ nhàng
private val TextDark = Color(0xFF333333)            // Màu chữ chính
private val TextMedium = Color(0xFF666666)          // Màu chữ phụ
private val CardBackgroundLight = Color(0xFFE0F7FA) // Nền card nhẹ, có thể dùng cho Info Card


@Composable
fun LinkCCCDScreen(navController: NavController) {
    var cccdNumber by remember { mutableStateOf("") }

    // Column thay thế Scaffold và sẽ là root Composable của màn hình này
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLightGray) // Áp dụng màu nền mới
            .padding(20.dp), // Padding tổng thể cho toàn màn hình
        verticalArrangement = Arrangement.spacedBy(20.dp) // Giảm khoảng cách giữa các thành phần
    ) {
        // Header Card (đã đổi màu)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            // Sử dụng BlueLight làm màu nền cho Card header
            colors = CardDefaults.cardColors(containerColor = BlueLight),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.ContactPage,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = Color.White // Icon màu trắng trên nền xanh nhạt
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Xác thực CCCD gắn chip Online",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White, // Tiêu đề màu trắng
                    textAlign = TextAlign.Center
                )
                Text(
                    "Hành khách chọn nút Xác thực Online và tiến hành chụp ảnh, quét NFC để xác thực căn cước công dân.",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f), // Text phụ màu trắng mờ
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Input Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Số CCCD *",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextDark // Sử dụng màu chữ tối
                )

                OutlinedTextField(
                    value = cccdNumber,
                    onValueChange = { cccdNumber = it },
                    placeholder = {
                        Text("Nhập số CCCD của bạn", color = TextMedium) // Placeholder màu trung tính
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BluePrimary, // Border khi focus màu xanh chủ đạo
                        unfocusedBorderColor = Color.LightGray, // Border khi không focus
                        focusedLabelColor = BluePrimary,
                        cursorColor = BluePrimary,
                        focusedLeadingIconColor = BluePrimary,
                        unfocusedLeadingIconColor = TextMedium
                    ),
                    leadingIcon = {
                        Icon(
                            Icons.Default.CreditCard,
                            contentDescription = null,
                            tint = BluePrimary // Icon màu xanh chủ đạo
                        )
                    }
                )
            }
        }

        // Action Button
        Button(
            onClick = { /* Handle verification */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary) // Nút màu xanh chủ đạo
        ) {
            Icon(
                Icons.Default.VerifiedUser,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.White // Icon trên nút màu trắng
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Xác thực Online",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White // Chữ trên nút màu trắng
            )
        }

        // Info Card (đã đổi màu)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundLight), // Nền card thông tin nhẹ hơn
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = BlueDark, // Icon màu xanh đậm
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Lưu ý quan trọng",
                        fontWeight = FontWeight.Bold,
                        color = BlueDark // Tiêu đề lưu ý màu xanh đậm
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "• Đảm bảo CCCD của bạn có gắn chip NFC\n" +
                            "• Kích hoạt NFC trên điện thoại\n" +
                            "• Chụp ảnh rõ nét, đầy đủ thông tin\n" +
                            "• Quá trình xác thực có thể mất 2-3 phút",
                    fontSize = 14.sp,
                    color = TextMedium // Nội dung lưu ý màu trung tính
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LinkCCCDScreenPreview() {
    // Preview the LinkCCCDScreen with a mock NavController
    LinkCCCDScreen(navController = NavController(context = LocalContext.current))
}