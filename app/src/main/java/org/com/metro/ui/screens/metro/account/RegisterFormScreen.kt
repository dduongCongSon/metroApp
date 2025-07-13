package org.com.metro.ui.screens.metro.account

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
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
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

// Định nghĩa bảng màu theo yêu cầu của bạn
private val BluePrimary = Color(0xFF2196F3)      // Màu xanh chủ đạo
private val BlueLight = Color(0xFF64B5F6)       // Màu xanh nhạt hơn, dùng cho primaryContainer
private val BlueDark = Color(0xFF1976D2)        // Màu xanh đậm hơn, dùng cho secondary

// Các màu phụ trợ để đảm bảo giao diện đẹp mắt
private val BackgroundLightGray = Color(0xFFF0F2F5) // Nền tổng thể nhẹ nhàng
private val TextDark = Color(0xFF333333)            // Màu chữ chính
private val TextMedium = Color(0xFF666666)          // Màu chữ phụ
private val LightGrayBorder = Color(0xFFCCCCCC)     // Màu border cho OutlinedTextField khi không focus


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterFormScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var cccdNumber by remember { mutableStateOf("") }
    var issueDate by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLightGray) // Áp dụng màu nền tổng thể
            .verticalScroll(rememberScrollState()), // Cho phép cuộn
        // Padding trên cùng để bù đắp cho việc bỏ TopAppBar
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp), // Padding cho nội dung form
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Form Fields
            FormTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                placeholder = "Nhập email...",
                icon = Icons.Default.Email
            )

            FormTextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = "Ngày sinh",
                placeholder = "Ngày/Tháng/Năm", // Format gợi ý
                icon = Icons.Default.DateRange
            )

            FormTextField(
                value = cccdNumber,
                onValueChange = { cccdNumber = it },
                label = "Số CCCD hoặc Căn Cước",
                placeholder = "Nhập số CCCD hoặc Căn Cước",
                icon = Icons.Default.CreditCard
            )

            FormTextField(
                value = issueDate,
                onValueChange = { issueDate = it },
                label = "Ngày cấp CCCD hoặc Căn Cước",
                placeholder = "Ngày/Tháng/Năm",
                icon = Icons.Default.DateRange
            )

            FormTextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = "Ngày hết hạn CCCD hoặc Căn Cước",
                placeholder = "Ngày/Tháng/Năm",
                icon = Icons.Default.DateRange
            )

            FormTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Họ và tên",
                placeholder = "Nhập họ và tên",
                icon = Icons.Default.Person
            )

            // Gender Selection Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White) // Nền trắng cho Card giới tính
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Giới tính *",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDark // Màu chữ tối
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp) // Giảm khoảng cách giữa các chip
                    ) {
                        // Gọi GenderFilterChip với tham số 'selected'
                        GenderFilterChip(label = "Nam", selected = selectedGender == "Nam") { selectedGender = "Nam" }
                        GenderFilterChip(label = "Nữ", selected = selectedGender == "Nữ") { selectedGender = "Nữ" }
                        GenderFilterChip(label = "Khác", selected = selectedGender == "Khác") { selectedGender = "Khác" }
                    }
                }
            }

            FormTextField(
                value = address,
                onValueChange = { address = it },
                label = "Địa chỉ",
                placeholder = "Nhập địa chỉ",
                icon = Icons.Default.LocationOn
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Submit Button
            Button(
                onClick = { /* Handle registration */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary) // Nút màu BluePrimary
            ) {
                Icon(
                    Icons.Default.AppRegistration,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.White // Icon màu trắng
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Đăng ký ngay",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Chữ màu trắng
                )
            }
            Spacer(modifier = Modifier.height(20.dp)) // Thêm khoảng trống dưới cùng
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: ImageVector
) {
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "$label *",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark // Màu chữ tối cho label
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = TextMedium) }, // Placeholder màu trung tính
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BluePrimary, // Border khi focus màu BluePrimary
                unfocusedBorderColor = LightGrayBorder, // Border khi không focus
                focusedLabelColor = BluePrimary,
                unfocusedLabelColor = TextMedium,
                cursorColor = BluePrimary,
                focusedLeadingIconColor = BluePrimary,
                unfocusedLeadingIconColor = TextMedium
            ),
            leadingIcon = {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderFilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        onClick = onClick,
        label = { Text(label) },
        selected = selected,
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = LightGrayBorder,
            selectedBorderColor = BluePrimary,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp
        ),
        shape = RoundedCornerShape(8.dp) // Bo góc cho chip
    )
}


@Preview(showBackground = true)
@Composable
fun RegisterFormScreenPreview() {
    RegisterFormScreen(navController = NavController(LocalContext.current))
}