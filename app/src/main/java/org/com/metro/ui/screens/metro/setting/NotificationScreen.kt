package org.com.metro.ui.screens.metro.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBack // Import ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton // Import IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import org.com.metro.ui.theme.BlueDark
import org.com.metro.ui.theme.BluePrimary
import org.com.metro.ui.theme.BlueLight
import org.com.metro.ui.theme.AppCyan // Giữ nguyên AppCyan nếu nó được định nghĩa ở đây

// Các màu phụ trợ
private val BackgroundLightGray = Color(0xFFF0F2F5)
private val TextDark = Color(0xFF333333)
private val TextMedium = Color(0xFF666666)
private val TextLight = Color(0xFF999999)
private val WarningColor = Color(0xFFFF9800)
private val ImportantColor = Color(0xFFFF5722)
private val CardBackground = Color.White // Thêm màu nền cho Card

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLightGray) // Áp dụng màu nền tổng thể
            .verticalScroll(rememberScrollState()) // Cho phép cuộn
    ) {
        // Header Stats Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp), // Điều chỉnh padding
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BlueLight.copy(alpha = 0.5f)),

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Đảm bảo Row chiếm toàn bộ chiều rộng
                    .padding(vertical = 20.dp), // Padding dọc lớn hơn
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = BluePrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp)) // Tăng khoảng cách
                    Text(
                        "5",
                        fontSize = 28.sp, // Kích thước chữ lớn hơn
                        fontWeight = FontWeight.ExtraBold, // Đậm hơn
                        color = BlueDark
                    )
                    Text(
                        "Mới",
                        fontSize = 14.sp, // Kích thước chữ vừa phải
                        color = TextMedium
                    )
                }

                Divider(
                    modifier = Modifier
                        .height(80.dp) // Divider cao hơn
                        .width(1.dp),
                    color = BluePrimary.copy(alpha = 0.4f)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Archive,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = AppCyan
                    )
                    Spacer(modifier = Modifier.height(10.dp)) // Tăng khoảng cách
                    Text(
                        "12",
                        fontSize = 28.sp, // Kích thước chữ lớn hơn
                        fontWeight = FontWeight.ExtraBold, // Đậm hơn
                        color = BlueDark
                    )
                    Text(
                        "Đã đọc",
                        fontSize = 14.sp, // Kích thước chữ vừa phải
                        color = TextMedium
                    )
                }
            }
        }

        // Filter Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp), // Điều chỉnh padding
            horizontalArrangement = Arrangement.spacedBy(10.dp) // Tăng khoảng cách giữa các chip
        ) {
            NotificationFilterChip(label = "Tất cả", selected = true) { /* Handle filter */ }
            NotificationFilterChip(label = "Chưa đọc", selected = false) { /* Handle filter */ }
            NotificationFilterChip(label = "Quan trọng", selected = false) { /* Handle filter */ }
        }

        Spacer(modifier = Modifier.height(4.dp)) // Khoảng cách nhỏ hơn trước danh sách

        // Notifications List
        NotificationItem(
            title = "Xác thực CCCD thành công",
            message = "Căn cước công dân của bạn đã được xác thực thành công. Bạn có thể sử dụng các tính năng liên kết với CCCD.",
            time = "2 phút trước",
            isRead = false,
            isImportant = true,
            icon = Icons.Default.CheckCircle,
            iconColor = BluePrimary
        )

        NotificationItem(
            title = "Cập nhật ứng dụng",
            message = "Phiên bản mới 1.3.20 đã có sẵn với nhiều tính năng cải tiến và sửa lỗi.",
            time = "1 giờ trước",
            isRead = false,
            isImportant = false,
            icon = Icons.Default.SystemUpdate,
            iconColor = BluePrimary
        )

        NotificationItem(
            title = "Gia hạn CCCD sắp hết hạn",
            message = "CCCD của bạn sẽ hết hạn trong 30 ngày. Vui lòng liên hệ cơ quan có thẩm quyền để gia hạn.",
            time = "3 giờ trước",
            isRead = false,
            isImportant = true,
            icon = Icons.Default.Warning,
            iconColor = WarningColor
        )

        NotificationItem(
            title = "Bảo trì hệ thống",
            message = "Hệ thống sẽ được bảo trì từ 2:00 - 4:00 sáng ngày mai. Các tính năng có thể bị gián đoạn.",
            time = "5 giờ trước",
            isRead = true,
            isImportant = false,
            icon = Icons.Default.Build,
            iconColor = TextMedium
        )

        NotificationItem(
            title = "Khuyến mãi vé tháng",
            message = "Giảm 20% cho vé tháng học sinh - sinh viên. Áp dụng từ ngày 1-15 tháng này.",
            time = "1 ngày trước",
            isRead = true,
            isImportant = false,
            icon = Icons.Default.LocalOffer,
            iconColor = ImportantColor
        )

        NotificationItem(
            title = "Cập nhật chính sách bảo mật",
            message = "Chúng tôi đã cập nhật chính sách bảo mật để bảo vệ thông tin cá nhân của bạn tốt hơn.",
            time = "2 ngày trước",
            isRead = true,
            isImportant = false,
            icon = Icons.Default.Security,
            iconColor = BluePrimary
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationFilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        onClick = onClick,
        label = { Text(label, fontSize = 13.sp) }, // Điều chỉnh kích thước chữ
        selected = selected,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = BluePrimary,
            selectedLabelColor = Color.White,
            containerColor = CardBackground, // Nền trắng khi không chọn
            labelColor = TextDark // Chữ màu tối khi không chọn
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = BluePrimary.copy(alpha = 0.5f),
            selectedBorderColor = BluePrimary,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.5.dp // Border dày hơn khi chọn
        ),
        shape = RoundedCornerShape(20.dp) // Bo góc nhiều hơn cho chip
    )
}

@Composable
fun NotificationItem(
    title: String,
    message: String,
    time: String,
    isRead: Boolean,
    isImportant: Boolean,
    icon: ImageVector,
    iconColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp) // Tăng padding dọc giữa các item
            .clickable { /* TODO: Handle notification item click */ }, // Thêm clickable cho mỗi item
        shape = RoundedCornerShape(16.dp), // Bo góc nhiều hơn
        colors = CardDefaults.cardColors(
            containerColor = if (isRead) CardBackground else BlueLight.copy(alpha = 0.1f) // Nền mềm mại hơn cho chưa đọc
        ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon with background
            Card(
                shape = RoundedCornerShape(12.dp), // Bo góc nhiều hơn cho icon background
                colors = CardDefaults.cardColors(
                    containerColor = iconColor.copy(alpha = 0.1f) // Nền icon nhẹ nhàng hơn
                ),
                modifier = Modifier.size(48.dp) // Icon background lớn hơn
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(28.dp) // Icon lớn hơn
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp)) // Tăng khoảng cách

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        title,
                        fontSize = 16.sp, // Tiêu đề lớn hơn
                        fontWeight = if (isRead) FontWeight.SemiBold else FontWeight.Bold, // Điều chỉnh độ đậm
                        color = BlueDark,
                        modifier = Modifier.weight(1f)
                    )

                    if (isImportant) {
                        Icon(
                            Icons.Default.PriorityHigh,
                            contentDescription = "Important",
                            tint = ImportantColor,
                            modifier = Modifier.size(18.dp) // Icon quan trọng lớn hơn
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    if (!isRead) {
                        Box(
                            modifier = Modifier
                                .size(10.dp) // Dấu chấm tròn lớn hơn
                                .background(
                                    BluePrimary,
                                    shape = RoundedCornerShape(5.dp)
                                )
                        )
                    }
                }

                Text(
                    message,
                    fontSize = 14.sp, // Nội dung lớn hơn
                    color = TextMedium,
                    lineHeight = 20.sp, // Tăng chiều cao dòng
                    modifier = Modifier.padding(top = 6.dp) // Tăng khoảng cách trên
                )

                Text(
                    time,
                    fontSize = 12.sp, // Thời gian lớn hơn một chút
                    color = TextLight,
                    modifier = Modifier.padding(top = 10.dp) // Tăng khoảng cách trên
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(
        navController = NavController(LocalContext.current))
}