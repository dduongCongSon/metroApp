package org.com.metro.ui.screens.stationselection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import org.com.metro.FareMatrix
import org.com.metro.Screen
import org.com.metro.ui.screens.metro.buyticket.FareMatrixViewModel
import org.com.metro.ui.theme.DarkGreen

private val PrimaryBlue = Color(0xFF1976D2)
private val LightBackgroundGradientStart = Color(0xFFE3F2FD)
private val LightBackgroundGradientEnd = Color(0xFFFFFFFF)
private val TextDark = Color(0xFF333333)
private val TextLight = Color(0xFF666666)
private val AccentWarning = Color(0xFFE53935)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatedFareScreen(
    navController: NavHostController,
    entryStationId: Int,
    exitStationId: Int,
    viewModel: FareMatrixViewModel = hiltViewModel(),
    stationViewModel: StationSelectionViewModel = hiltViewModel()

) {
    val uiState by viewModel.uiState.collectAsState()
    val fare = uiState.calculatedFare
    val stationUiState by stationViewModel.uiState.collectAsState()
    val currentFareResponse = uiState.calculatedFare

    val entryStation = stationUiState.stations.find { it.stationId == entryStationId }
    val exitStation = stationUiState.stations.find { it.stationId == exitStationId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(LightBackgroundGradientStart, LightBackgroundGradientEnd)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryBlue)
                }
            } else if (currentFareResponse != null && entryStation != null && exitStation != null) {
                Spacer(modifier = Modifier.height(24.dp))
                FareDetailCard(
                    entryStationName = entryStation.name,
                    exitStationName = exitStation.name,
                    fare = currentFareResponse.data!!
                )
                Spacer(modifier = Modifier.height(24.dp))
            } else {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = uiState.errorMessage ?: "Không thể tính giá vé. Vui lòng thử lại.",
                    color = AccentWarning,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (entryStation != null && exitStation != null) {
                        navController.navigate(
                            Screen.OrderFareInfo.createRoute(
                                entryStationId = entryStation.stationId,
                                exitStationId = exitStation.stationId
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                enabled = fare != null
            ) {
                Text(
                    "Xác nhận mua vé",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, PrimaryBlue.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryBlue
                )
            ) {
                Text(
                    "Chọn lại ga",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun FareDetailCard(entryStationName: String, exitStationName: String, fare: FareMatrix) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "THÔNG TIN LƯỢT ĐI",
                fontSize = 14.sp,
                color = TextLight,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                StationDisplay(name = entryStationName, isEntry = true)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "to",
                    tint = TextLight,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                StationDisplay(name = exitStationName, isEntry = false)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = PrimaryBlue.copy(alpha = 0.1f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoRow(
                    label = "Hạn sử dụng:",
                    value = "Không giới hạn",
                    valueColor = TextDark
                )
                InfoRow(
                    label = "Lưu ý:",
                    value = "Vé lượt có hiệu lực cho một chuyến đi duy nhất.",
                    valueColor = AccentWarning
                )
                InfoRow(
                    label = "Mô tả:",
                    value = "Vé cho phép di chuyển một lượt giữa ${entryStationName} và ${exitStationName}.",
                    valueColor = TextLight
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = PrimaryBlue.copy(alpha = 0.1f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(24.dp))

            // Giá Highlight
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = PrimaryBlue.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Tổng cộng: ${fare.price} đ",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun StationDisplay(name: String, isEntry: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(120.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(if (isEntry) PrimaryBlue else Color(0xFFFF9800)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.DirectionsBus,
                contentDescription = "Station",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 20.sp,
            color = TextDark
        )
    }
}


@Composable
private fun InfoRow(label: String, value: String, valueColor: Color = TextDark) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = PrimaryBlue,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = value,
            fontSize = 16.sp,
            color = valueColor,
            lineHeight = 22.sp
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CalculatedFareScreenPreview() {
//    CalculatedFareScreen(
//        navController = rememberNavController(),
//        entryStationId = 1,
//        exitStationId = 2,
//        viewModel = FareMatrixViewModel(),
//        stationViewModel = StationSelectionViewModel()
//    )
//}