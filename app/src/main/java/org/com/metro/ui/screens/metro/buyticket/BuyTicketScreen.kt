package org.com.metro.ui.screens.metro.buyticket

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star // Giữ lại nếu muốn icon cho SectionHeader khác
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface // Import Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.com.metro.Screen // Đảm bảo Screen được import đúng từ package của bạn
import org.com.metro.repositories.apis.ticket.TicketType
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel

// Thêm các imports cần thiết từ file đầu tiên
import androidx.navigation.compose.currentBackStackEntryAsState // Cho selectedStationFrom/To
import androidx.compose.material.icons.filled.Search // Icon tìm kiếm
import androidx.compose.ui.text.style.TextOverflow // Cho TextOverflow.Ellipsis

// Định nghĩa lại các màu từ file đầu tiên để sử dụng trong SearchStationCard
private val AppWhite = Color(0xFFFFFFFF)
private val AppLightGray = Color(0xFFF0F0F0) // Có thể điều chỉnh cho phù hợp với LightGreenBackground
private val AppMediumGray = Color(0xFFB0B0B0)
private val AppDarkGray = Color(0xFF424242)


// Data classes (giữ nguyên hoặc đã chỉnh sửa nếu cần)
data class TicketOption(
    val title: String,
    val price: String,
    val icon: ImageVector = Icons.Default.ConfirmationNumber
)
data class RouteInfo( // Giữ lại nếu bạn vẫn dùng đâu đó, nếu không có thể xóa
    val from: String,
    val to: String,
    val details: String = "Xem chi tiết"
)

// Màu sắc chủ đạo (giữ nguyên)
private val PrimaryGreen = Color(0xFF4CAF50)
private val DarkGreen = Color(0xFF388E3C)
private val LightGreenBackground = Color(0xFFE8F5E9)
private val TextPrimaryColor = Color(0xFF212121)
private val TextSecondaryColor = Color(0xFF757575)


// --- WELCOME CARD --- (Giữ nguyên)
@Composable
fun WelcomeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreen),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.WavingHand,
                contentDescription = "Welcome",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Chào mừng!",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Trải nghiệm mới cùng Metro ngay hôm nay!",
                    fontSize = 14.sp,
                    color = Color(0xB3FFFFFF) // White with 70% opacity
                )
            }
        }
    }
}

// --- SECTION HEADER --- (Giữ nguyên)
@Composable
fun SectionHeader(title: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = DarkGreen,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            color = TextPrimaryColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// --- TICKET CARD --- (Giữ nguyên)
@Composable
fun TicketCard(
    ticket: TicketType,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (ticket.name == "Single") {
                    navController.navigate(Screen.StationSelection.route)
                } else {
                    navController.navigate(Screen.BuyTicketDetail.createRoute(ticket.id))
                }
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(LightGreenBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ConfirmationNumber,
                        contentDescription = ticket.description,
                        tint = PrimaryGreen,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = ticket.description,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimaryColor
                    )
                    Text(
                        text = "${ticket.price} đ",
                        fontSize = 14.sp,
                        color = TextSecondaryColor
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Mua vé",
                tint = TextSecondaryColor.copy(alpha = 0.7f)
            )
        }
    }
}

// --- COMPONENT TÌM KIẾM GA (MỚI TỪ FILE ĐẦU TIÊN) ---
@Composable
fun SearchStationCard(
    navController: NavHostController,
    selectedStationFrom: String,
    selectedStationTo: String // Giữ lại nếu bạn muốn dùng cho ga đến, hoặc xóa nếu chỉ có ga đi
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Tìm kiếm ga:",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppDarkGray
            )
            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable {
                        navController.navigate(Screen.Home.route + "?focusField=from")
                    },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, AppMediumGray.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = AppMediumGray
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = if (selectedStationFrom != "Chọn ga khởi hành") selectedStationFrom else "Nhập tên ga...",
                        color = if (selectedStationFrom != "Chọn ga khởi hành") AppDarkGray else AppMediumGray,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            // Bạn có thể thêm Search cho "ga điểm đến" tương tự nếu cần
            /*
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable {
                        navController.navigate(Screen.SearchStation.route + "?focusField=to")
                    },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, AppMediumGray.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = AppMediumGray
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = if (selectedStationTo != "Chọn ga điểm đến") selectedStationTo else "Nhập ga điểm đến...",
                        color = if (selectedStationTo != "Chọn ga điểm đến") AppDarkGray else AppMediumGray,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            */
        }
    }
}


// --- CÁC SECTION CHÍNH ---
@Composable
fun TicketOptionsSection(
    navController: NavHostController,
    viewModel: BuyTicketViewModel
) {
    val ticketOptions by viewModel.ticketTypes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        if (ticketOptions.isEmpty() && !isLoading && errorMessage == null) {
            viewModel.fetchTicketTypes()
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = PrimaryGreen)
        }
    } else if (errorMessage != null) {
        Text(text = "Lỗi tải dữ liệu: $errorMessage", color = Color.Red, modifier = Modifier.padding(16.dp))
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Hiển thị thông báo khi danh sách rỗng sau khi tải thành công
            if (ticketOptions.isEmpty()) {
                Text(
                    text = "Hiện không có loại vé nào khả dụng.",
                    color = TextSecondaryColor,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else {
                ticketOptions.forEach { ticket ->
                    TicketCard(ticket = ticket, navController = navController)
                }
            }
        }
    }
}

// --- ROUTE CARD --- (Đã bị loại bỏ khỏi BuyTicketScreen chính)
// @Composable
// fun RouteCard(fareMatrix: FareMatrix) { ... }

// --- ROUTES SECTION --- (Đã bị loại bỏ khỏi BuyTicketScreen chính)
// @Composable
// fun RoutesSection(viewModel: FareMatrixViewModel) { ... }


// --- MÀN HÌNH CHÍNH: BUY TICKET SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyTicketScreen(
    navController: NavHostController,
    buyTicketViewModel: BuyTicketViewModel = hiltViewModel(),
    // FareMatrixViewModel không còn được sử dụng trực tiếp trong UI này nữa,
    // nhưng vẫn có thể giữ lại nếu nó cần cho các phần khác hoặc xử lý nền.
    // Nếu không, bạn có thể xóa nó khỏi tham số.
    fareMatrixViewModel: FareMatrixViewModel = hiltViewModel()
) {
    // Lấy trạng thái của ga đã chọn từ NavController's savedStateHandle
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedStationFrom = navBackStackEntry?.savedStateHandle?.get<String>("selectedFromStation") ?: "Chọn ga khởi hành"
    val selectedStationTo = navBackStackEntry?.savedStateHandle?.get<String>("selectedToStation") ?: "Chọn ga điểm đến"


    Scaffold(
        containerColor = Color.White // Nền trắng cho toàn màn hình
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, LightGreenBackground),
                        startY = 0f,
                        endY = 1500f // Gradient nhẹ nhàng hơn
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            WelcomeCard()
            Spacer(modifier = Modifier.height(24.dp))

            // Thêm component tìm kiếm ga vào đây
            SearchStationCard(
                navController = navController,
                selectedStationFrom = selectedStationFrom,
                selectedStationTo = selectedStationTo
            )
            Spacer(modifier = Modifier.height(24.dp)) // Khoảng cách sau search card

            SectionHeader(title = "Mua vé theo lượt", icon = Icons.Default.LocalActivity)
            Spacer(modifier = Modifier.height(12.dp))
            TicketOptionsSection(navController, buyTicketViewModel)

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(title = "Ưu đãi Học sinh - Sinh viên", icon = Icons.Default.School)
            Spacer(modifier = Modifier.height(12.dp))

            TicketCard(
                ticket = TicketType(
                    id = 0, name = "Student Monthly", description = "Vé tháng HSSV", price = 150000,
                    validityDuration = "ONE_MONTH", isActive = true, createdAt = "", updatedAt = ""
                ),
                navController = navController
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Bỏ phần "Các tuyến nổi bật" và RoutesSection
            // SectionHeader(title = "Các tuyến nổi bật", icon = Icons.Default.Star)
            // Spacer(modifier = Modifier.height(12.dp))
            // RoutesSection(fareMatrixViewModel) // Đã xóa

            // Spacer(modifier = Modifier.height(24.dp)) // Có thể bỏ nếu không còn phần trên

            SectionHeader(title = "Vé dài hạn", icon = Icons.Default.DateRange)
            Spacer(modifier = Modifier.height(12.dp))
            TicketOptionsSection(navController, buyTicketViewModel)

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BuyTicketScreenPreview() {
    val navController = rememberNavController()
    BuyTicketScreen(navController = navController)
}