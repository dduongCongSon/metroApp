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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import org.com.metro.Screen
import org.com.metro.repositories.apis.ticket.TicketType
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import org.com.metro.FareMatrix

data class TicketOption(
    val title: String,
    val price: String,
    val icon: ImageVector = Icons.Default.ConfirmationNumber
)
data class RouteInfo(
    val from: String,
    val to: String,
    val details: String = "Xem chi tiết"
)

private val PrimaryGreen = Color(0xFF4CAF50)
private val DarkGreen = Color(0xFF388E3C)
private val LightGreenBackground = Color(0xFFE8F5E9)
private val TextPrimaryColor = Color(0xFF212121)
private val TextSecondaryColor = Color(0xFF757575)

// --- TOP BAR ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyTicketTopBar(onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Mua vé",
                color = PrimaryGreen,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Trở về",
                    tint = PrimaryGreen
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
        )
    )
}

// --- WELCOME CARD ---
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

// --- SECTION HEADER ---
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

// --- TICKET CARD ---
@Composable
fun TicketCard(
    ticket: TicketType,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // LOGIC GỐC: Được giữ nguyên
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

// --- ROUTE CARD ---
@Composable
fun RouteCard(fareMatrix: FareMatrix) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* LOGIC GỐC: Handle route selection */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
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
                        imageVector = Icons.Default.Route,
                        contentDescription = fareMatrix.name,
                        tint = PrimaryGreen,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Tuyến: ${fareMatrix.name}",
                        color = TextPrimaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Giá: ${fareMatrix.price} đ",
                        fontSize = 14.sp,
                        color = TextSecondaryColor
                    )
                }
            }
            Text(
                text = "Xem",
                color = PrimaryGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { /* LOGIC GỐC: Handle details */ }
            )
        }
    }
}

// --- CÁC SECTION CHÍNH ---
@Composable
fun TicketOptionsSection(
    navController: NavHostController,
    viewModel: BuyTicketViewModel
) {
    // LOGIC GỐC: Được giữ nguyên
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
            ticketOptions.forEach { ticket ->
                TicketCard(ticket = ticket, navController = navController)
            }
        }
    }
}

@Composable
fun RoutesSection(viewModel: FareMatrixViewModel) {
    // LOGIC GỐC: Được giữ nguyên
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (uiState.fareMatrices.isEmpty() && !uiState.isLoading && uiState.errorMessage == null) {
            viewModel.fetchFareMatrices()
        }
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = PrimaryGreen)
        }
    } else if (uiState.errorMessage != null) {
        Text(text = "Lỗi tải tuyến đường: ${uiState.errorMessage}", color = Color.Red, modifier = Modifier.padding(16.dp))
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            uiState.fareMatrices.forEach { fareMatrix ->
                RouteCard(fareMatrix = fareMatrix)
            }
        }
    }
}


// --- MÀN HÌNH CHÍNH: BUY TICKET SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyTicketScreen(
    navController: NavHostController,
    buyTicketViewModel: BuyTicketViewModel = hiltViewModel(),
    fareMatrixViewModel: FareMatrixViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { BuyTicketTopBar(onBackClick = { navController.popBackStack() }) },
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

            SectionHeader(title = "Mua vé theo lượt", icon = Icons.Default.LocalActivity)
            Spacer(modifier = Modifier.height(12.dp))
            TicketOptionsSection(navController, buyTicketViewModel) // LOGIC GỐC

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

            SectionHeader(title = "Các tuyến nổi bật", icon = Icons.Default.Star)
            Spacer(modifier = Modifier.height(12.dp))
            RoutesSection(fareMatrixViewModel)

            Spacer(modifier = Modifier.height(24.dp))

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






