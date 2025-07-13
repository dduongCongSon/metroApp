package org.com.metro.ui.screens.stationselection

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.com.metro.ui.screens.metro.buyticket.FareMatrixViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.com.metro.FareMatrix
import org.com.metro.Station
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import org.com.metro.R
import org.com.metro.Screen

// Định nghĩa lại màu sắc để thống nhất với style mới
private val AccentGreen = Color(0xFF4CAF50) // Màu xanh lá cây cho nút, checkmark
private val BackgroundColor = Color(0xFFF5F5F5) // Màu nền tổng thể
private val CardBackgroundColor = Color.White // Màu nền cho Card
private val TextPrimaryColor = Color(0xFF1A237E) // Màu xanh đậm cho tiêu đề section
private val TextSecondaryColor = Color(0xFF333333) // Màu chữ thông thường
private val HintTextColor = Color(0xFF999999) // Màu chữ gợi ý/xám
private val AccentBlue = Color(0xFF4A90E2) // Màu xanh dương cho điều khoản
private val WarningRed = Color(0xFFE53935) // Màu đỏ cho lưu ý quan trọng
private val DividerColorCustom = Color(0xFFE0E0E0) // Màu divider thống nhất

data class LocalPaymentMethod(
    val id: Int,
    val name: String,
    val iconRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderFareInfoScreen(
    navController: NavHostController,
    entryStationId: Int,
    exitStationId: Int,
    fareMatrixViewModel: FareMatrixViewModel = hiltViewModel(), // Thêm default hiltViewModel
    stationViewModel: StationSelectionViewModel = hiltViewModel() // Thêm default hiltViewModel
) {
    val fareMatrixUiState by fareMatrixViewModel.uiState.collectAsState()
    val stationUiState by stationViewModel.uiState.collectAsState()

    val fareInfo = fareMatrixUiState.calculatedFare?.data
    val entryStation = stationUiState.stations.find { it.stationId == entryStationId }
    val exitStation = stationUiState.stations.find { it.stationId == exitStationId }

    var showPaymentSheet by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }

    val paymentMethods = listOf(
        LocalPaymentMethod(1, "VNPAY", R.drawable.ic_vnpay),
        LocalPaymentMethod(2, "MoMo", R.drawable.ic_momo)
    )
    var selectedPaymentMethod by remember { mutableStateOf(paymentMethods.first()) }
    val context = LocalContext.current

    LaunchedEffect(key1 = fareMatrixUiState.createOrderResponse, key2 = fareMatrixUiState.createOrderError) {
        val response = fareMatrixUiState.createOrderResponse
        if (response != null) {
            if (response.status == 200 && response.data != null) {
                Toast.makeText(context, "Tạo đơn hàng thành công!", Toast.LENGTH_SHORT).show()
                navController.navigate(Screen.MyTicket.route)
            } else {
                Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
            }
            fareMatrixViewModel.clearCreateOrderStatus()
        }

        val error = fareMatrixUiState.createOrderError
        if (error != null) {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            fareMatrixViewModel.clearCreateOrderStatus()
        }
    }

    if (showPaymentSheet) {
        PaymentMethodBottomSheet(
            paymentMethods = paymentMethods,
            selectedMethod = selectedPaymentMethod,
            onDismiss = { showPaymentSheet = false },
            onSelectMethod = { method ->
                selectedPaymentMethod = method
                showPaymentSheet = false
            }
        )
    }

    if (showTermsDialog) {
        TermsAndConditionsDialog(onDismiss = { showTermsDialog = false })
    }

    // Thay thế Scaffold bằng Column chính
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor) // Nền màu xám nhạt
            .verticalScroll(rememberScrollState()) // Có thể cuộn
            .padding(vertical = 16.dp), // Padding tổng thể cho nội dung màn hình
        horizontalAlignment = Alignment.CenterHorizontally // Căn giữa các phần tử theo chiều ngang
    ) {
        // Top Bar (nếu muốn, có thể thêm vào đây, nhưng yêu cầu là không cần)
        // Nếu bạn muốn top bar nhưng không phải của Scaffold, hãy đặt nó ở đây.
        // Ví dụ: OrderFareInfoTopBar(...)

        // Nội dung chính của màn hình
        if (fareInfo != null && entryStation != null && exitStation != null) {
            PaymentMethodSection(
                selectedMethod = selectedPaymentMethod,
                onClick = { showPaymentSheet = true }
            )
            Spacer(modifier = Modifier.height(16.dp)) // Khoảng cách giữa các section
            PaymentInfoSection(fare = fareInfo, entryStation = entryStation, exitStation = exitStation)
            Spacer(modifier = Modifier.height(16.dp))
            TicketDetailsSection(entryStation = entryStation, exitStation = exitStation)
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Đang tải thông tin đơn hàng...")
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Khoảng cách trước bottom bar/nút

        // Bottom Bar (đặt trực tiếp vào Column chính)
        if (fareInfo != null) {
            PaymentBottomBar(
                price = fareInfo.price,
                isLoading = fareMatrixUiState.isCreatingOrder,
                onPayClick = {
                    fareMatrixViewModel.createSingleOrder(
                        fareMatrixId = fareInfo.fareMatrixId,
                        paymentMethodId = selectedPaymentMethod.id
                    )
                },
                onTermsClick = { showTermsDialog = true }
            )
        }
        Spacer(modifier = Modifier.height(16.dp)) // Khoảng cách cuối cùng
    }
}

// Hàm TopBar riêng, không sử dụng ở đây do yêu cầu "ko cần topbot bar"
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderFareInfoTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(title, fontWeight = FontWeight.SemiBold, color = DarkGreenCustom) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkGreenCustom)
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = CardBackgroundColor)
    )
}
*/

@Composable
private fun PaymentMethodSection(selectedMethod: LocalPaymentMethod, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp) // Padding horizontal cho cả Column
    ) {
        Text("Phương thức thanh toán", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = TextPrimaryColor)
        Spacer(Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp) // Độ bóng nhẹ hơn
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = selectedMethod.iconRes),
                        contentDescription = selectedMethod.name,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(selectedMethod.name, color = TextSecondaryColor, fontWeight = FontWeight.Medium, fontSize = 14.sp) // Kích thước chữ
                }
                Icon(Icons.Filled.ChevronRight, contentDescription = "Select", tint = HintTextColor) // Màu xám
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaymentMethodBottomSheet(
    paymentMethods: List<LocalPaymentMethod>,
    selectedMethod: LocalPaymentMethod,
    onDismiss: () -> Unit,
    onSelectMethod: (LocalPaymentMethod) -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                "Chọn phương thức thanh toán",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryColor, // Màu tiêu đề
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            paymentMethods.forEach { method ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectMethod(method) }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = method.iconRes),
                        contentDescription = method.name,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(method.name, modifier = Modifier.weight(1f), fontSize = 16.sp, color = TextSecondaryColor)
                    if (method == selectedMethod) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = "Selected",
                            tint = AccentBlue
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TermsAndConditionsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Điều khoản dịch vụ", fontWeight = FontWeight.Bold, color = TextPrimaryColor) }, // Màu tiêu đề
        text = {
            Text(
                "Bằng việc sử dụng dịch vụ, bạn đồng ý tuân thủ tất cả các quy định về vận chuyển hành khách công cộng. " +
                        "Vé đã mua không thể hoàn trả. Vui lòng giữ vé cẩn thận để xuất trình khi có yêu cầu. " +
                        "Mọi hành vi gian lận sẽ bị xử lý theo quy định của pháp luật. " +
                        "Cảm ơn bạn đã sử dụng dịch vụ của Metro.",
                fontSize = 14.sp,
                color = TextSecondaryColor // Màu chữ nội dung
            )
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue) // Màu nút
            ) {
                Text("Đã hiểu", color = Color.White)
            }
        }
    )
}

@Composable
private fun PaymentBottomBar(
    price: Int,
    onTermsClick: () -> Unit,
    onPayClick: () -> Unit,
    isLoading: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, spotColor = Color.Black.copy(alpha = 0.1f)) // Đổ bóng nhẹ nhàng
            .background(CardBackgroundColor)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            val annotatedString = buildAnnotatedString {
                append("Bằng việc bấm thanh toán, bạn đồng ý với ")
                pushStringAnnotation(tag = "TERMS", annotation = "TERMS")
                withStyle(
                    style = SpanStyle(
                        color = AccentBlue, // Màu xanh dương cho điều khoản
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("điều khoản")
                }
                pop()
                append(" của Metro")
            }

            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "TERMS", start = offset, end = offset)
                        .firstOrNull()?.let {
                            onTermsClick()
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                style = LocalTextStyle.current.copy(
                    fontSize = 12.sp,
                    color = HintTextColor,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(Modifier.height(8.dp))
            Button(
                onClick = onPayClick,
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentBlue,
                    disabledContainerColor = HintTextColor.copy(alpha = 0.5f)
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                } else {
                    Text("Thanh toán: ${price}đ", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun PaymentInfoSection(fare: FareMatrix, entryStation: Station, exitStation: Station) {
    val routeName = "${entryStation.name} – ${exitStation.name}"

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp) // Padding horizontal cho cả Column
    ) {
        Text("Thông tin thanh toán", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = TextPrimaryColor)
        Spacer(Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow(label = "Sản phẩm:", value = "Vé lượt: $routeName")
                Divider(color = DividerColorCustom, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                InfoRow(label = "Đơn giá:", value = "${fare.price}đ")
                Divider(color = DividerColorCustom, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                InfoRow(label = "Số lượng:", value = "1")
                Divider(color = DividerColorCustom, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                InfoRow(label = "Thành tiền:", value = "${fare.price}đ")
                Divider(color = DividerColorCustom, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                InfoRow(label = "Tổng giá tiền:", value = "${fare.price}đ", isTotal = true)
            }
        }
    }
}

@Composable
private fun TicketDetailsSection(entryStation: Station, exitStation: Station) {
    val routeName = "${entryStation.name} – ${exitStation.name}"

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp) // Padding horizontal cho cả Column
    ) {
        Text("Thông tin vé lượt: $routeName", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = TextPrimaryColor)
        Spacer(Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoRow(label = "Loại vé:", value = "Vé lượt")
                InfoRow(label = "HSD:", value = "30 ngày kể từ ngày mua")
                InfoRow(label = "Lưu ý:", value = "Vé sử dụng một lần", valueColor = WarningRed) // Màu đỏ cho lưu ý
                InfoRow(label = "Mô tả:", value = "Vé lượt: $routeName")
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String, isTotal: Boolean = false, valueColor: Color? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = HintTextColor,
            fontSize = if (isTotal) 15.sp else 14.sp,
            fontWeight = if (isTotal) FontWeight.Medium else FontWeight.Normal
        )
        Text(
            text = value,
            color = valueColor ?: if (isTotal) AccentGreen else TextSecondaryColor, // Màu xanh lá cho tổng cộng
            fontSize = if (isTotal) 15.sp else 14.sp,
            fontWeight = if (isTotal) FontWeight.Medium else FontWeight.Normal
        )
    }
}