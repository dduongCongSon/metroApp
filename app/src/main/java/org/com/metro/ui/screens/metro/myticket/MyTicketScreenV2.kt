package org.com.metro.ui.screens.metro.myticket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.com.metro.R
import org.com.metro.Screen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import org.com.metro.repositories.apis.order.OrderWithTicketDetails
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// Đã loại bỏ các màu trùng lặp nếu đã có trong chủ đề chung hoặc đặt lại theo phong cách Material 3
private val PrimaryBlue = Color(0xFF2196F3)
private val DarkBlue = Color(0xFF1976D2)
private val LightGrayBackground = Color(0xFFF0F2F5) // Màu nền nhẹ nhàng hơn
private val TextDark = Color(0xFF333333) // Màu chữ tối hơn cho dễ đọc
private val TextMedium = Color(0xFF666666) // Màu chữ trung tính
private val GreenStatus = Color(0xFF4CAF50) // Màu cho trạng thái "Đang hoạt động" hoặc "Đã sử dụng"
private val RedStatus = Color(0xFFF44336)   // Màu cho trạng thái "Hết hạn"

@Composable
fun MyTicketScreen(
    navController: NavController,
    viewModel: MyTicketViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf("PENDING") } // Use 'by' for direct access

    // Filter order list based on the selected tab
    val filteredOrders = remember(uiState.orders, selectedTab) {
        uiState.orders.filter { order ->
            when (selectedTab) {
                "PENDING" -> order.status.equals("PENDING", ignoreCase = true) || order.status.equals("ACTIVE", ignoreCase = true)
                "COMPLETED" -> order.status.equals("COMPLETED", ignoreCase = true)
                "EXPIRED" -> order.status.equals("EXPIRED", ignoreCase = true)
                else -> false
            }
        }
    }

    // Column replaces Scaffold's content area
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayBackground) // Sử dụng màu nền mới
    ) {
        // Tabs
        TabRow(
            selectedTabIndex = when (selectedTab) {
                "PENDING" -> 0
                "COMPLETED" -> 1
                else -> 2
            },
            containerColor = Color.White,
            contentColor = PrimaryBlue, // Màu cho indicator và text của tab đang chọn
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp) // Thêm padding cho TabRow
                .background(Color.White, RoundedCornerShape(12.dp)), // Nền trắng bo góc cho TabRow
            indicator = { tabPositions ->
                // Custom indicator để có thể thêm style riêng nếu muốn
                // Mặc định, TabRow sẽ tự vẽ indicator nếu không cung cấp
            }
        ) {
            Tab(
                selected = selectedTab == "PENDING",
                onClick = { selectedTab = "PENDING" },
                text = { Text("Đang hoạt động", fontWeight = FontWeight.Medium) },
                selectedContentColor = PrimaryBlue,
                unselectedContentColor = TextMedium
            )
            Tab(
                selected = selectedTab == "COMPLETED",
                onClick = { selectedTab = "COMPLETED" },
                text = { Text("Đã sử dụng", fontWeight = FontWeight.Medium) },
                selectedContentColor = PrimaryBlue,
                unselectedContentColor = TextMedium
            )
            Tab(
                selected = selectedTab == "EXPIRED",
                onClick = { selectedTab = "EXPIRED" },
                text = { Text("Hết hạn", fontWeight = FontWeight.Medium) },
                selectedContentColor = PrimaryBlue,
                unselectedContentColor = TextMedium
            )
        }
        Spacer(modifier = Modifier.height(12.dp)) // Khoảng cách giữa TabRow và nội dung

        // Content
        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = PrimaryBlue)
            } else if (uiState.errorMessage != null) {
                Text(
                    text = "Lỗi: ${uiState.errorMessage}",
                    modifier = Modifier.align(Alignment.Center),
                    color = RedStatus,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            } else if (filteredOrders.isEmpty()) {
                Text(
                    text = "Bạn không có vé nào trong mục này.",
                    modifier = Modifier.align(Alignment.Center),
                    color = TextMedium,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp) // Giảm khoảng cách giữa các card
                ) {
                    items(filteredOrders) { order ->
                        TicketCard(order = order, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun TicketCard(order: OrderWithTicketDetails, navController: NavController) {
    val ticket = order.ticket ?: return // Handle null ticket gracefully
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Tăng shadow elevation
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Icon vé
                Icon(
                    painter = painterResource(id = R.drawable.ic_ticket_info),
                    contentDescription = "Ticket Icon",
                    tint = PrimaryBlue, // Sử dụng màu xanh chính
                    modifier = Modifier.size(36.dp) // Kích thước icon lớn hơn
                )
                Spacer(Modifier.width(14.dp))
                // Tên vé
                Text(
                    text = "Vé lượt",
                    fontSize = 20.sp, // Kích thước chữ lớn hơn
                    fontWeight = FontWeight.Bold,
                    color = TextDark, // Màu chữ đậm hơn
                    modifier = Modifier.weight(1f)
                )
            }
            Divider(Modifier.padding(vertical = 14.dp), color = LightGrayBackground, thickness = 1.dp) // Divider dày hơn
            // Thông tin chi tiết
            InfoRow(label = "Mã đơn hàng:", value = "#${order.orderId}")
            Spacer(Modifier.height(6.dp)) // Tăng khoảng cách
            InfoRow(label = "Mã vé:", value = order.ticket.ticketCode)
            Spacer(Modifier.height(6.dp)) // Tăng khoảng cách
            InfoRow(
                label = "Trạng thái:",
                value = order.status.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }, // Chuyển đổi chữ cái đầu tiên thành in hoa
                valueColor = getStatusColor(order.status)
            )
            Spacer(Modifier.height(6.dp)) // Tăng khoảng cách
            InfoRow(
                label = "Giá vé:",
                value = "${order.amount.toInt()}đ"
            )
            Spacer(Modifier.height(6.dp)) // Tăng khoảng cách
            InfoRow(
                label = "Hiệu lực:",
                value = "${formatDate(ticket.validFrom)} - ${formatDate(ticket.validUntil)}"
            )

            if (order.status.equals("PENDING", ignoreCase = true) || order.status.equals("ACTIVE", ignoreCase = true)) {
                Spacer(Modifier.height(20.dp)) // Tăng khoảng cách cho nút
                Button(
                    onClick = {
                        navController.navigate(Screen.TicketQRCode.createRoute(ticket.ticketCode))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp), // Chiều cao nút lớn hơn
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(10.dp) // Bo góc nút
                ) {
                    Text("SỬ DỤNG VÉ", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

private fun formatDate(dateString: String): String {
    return try {
        // Xử lý chuỗi ngày giờ có múi giờ không chuẩn để SimpleDateFormat có thể parse
        val cleanedDateString = dateString.replace(Regex("(\\+|\\-)(\\d{2}):(\\d{2})")) {
            "${it.groupValues[1]}${it.groupValues[2]}${it.groupValues[3]}" // Chuyển đổi +HH:mm thành +HHmm
        }

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        val date: Date = parser.parse(cleanedDateString) ?: return dateString

        val formatter = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
        formatter.timeZone = TimeZone.getDefault() // Đảm bảo định dạng theo múi giờ cục bộ

        formatter.format(date)
    } catch (e: Exception) {
        // Log the error for debugging
        e.printStackTrace()
        // Fallback to showing only the date part if parsing fails
        dateString.take(10)
    }
}

@Composable
private fun InfoRow(label: String, value: String, valueColor: Color = TextDark) { // Mặc định màu chữ tối hơn
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = TextMedium,
            fontSize = 15.sp, // Kích thước chữ nhỉnh hơn
            modifier = Modifier.width(130.dp) // Tăng độ rộng nhãn để căn chỉnh
        )
        Text(
            text = value,
            color = valueColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun getStatusColor(status: String): Color {
    return when (status.uppercase(Locale.ROOT)) { // Đảm bảo so sánh không phân biệt chữ hoa chữ thường
        "PENDING", "ACTIVE" -> PrimaryBlue
        "COMPLETED" -> GreenStatus // Sử dụng màu xanh lá cho đã sử dụng
        "EXPIRED", "CANCELLED" -> RedStatus
        else -> TextMedium
    }
}