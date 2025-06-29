package org.com.metro.ui.screens.metro.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.Language

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable() { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF666666),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = Color(0xFF424242)
            )
        }

        if (subtitle != null) {
            Text(
                text = subtitle,
                fontSize = 16.sp,
                color = Color(0xFF1976D2),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Navigate",
            tint = Color(0xFF999999),
            modifier = Modifier.size(20.dp)
        )
    }
}
@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen() {
    Column (modifier = Modifier.fillMaxSize()
        .background(Color(0xFFF5F5F5))) {
        //Top app bar
        CenterAlignedTopAppBar(
            title = {
                Text(text="Cài đặt",
                    color =Color.White,
                    fontSize =18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign= TextAlign.Center
                )
            },

            navigationIcon = {
                IconButton (onClick = { /* Handle back navigation */ }) {
                    Icon(Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF1976D2)
            )
        )
        // Setting card
        Card (
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape= RoundedCornerShape(12.dp)
        ){
              Column {
                  SettingsItem(
                    icon=Icons.Default.Notifications,
                      title="Thông báo",
                      onClick={ }
                  )
                  Divider(
                      modifier = Modifier.fillMaxWidth()
                          .padding(start = 56.dp),
                      color = Color(0xFFE0E0E0),
                      thickness = 0.5.dp
                  )
                  SettingsItem(
                      icon = Icons.Default.Lock,
                      title = "Quyền truy cập",
                      onClick = { /* Handle privacy */ }
                  )
                  Divider(
                      modifier = Modifier.fillMaxWidth()
                          .padding(start = 56.dp),
                      color = Color(0xFFE0E0E0),
                      thickness = 0.5.dp
                  )
                  SettingsItem(
                      icon = Icons.Default.Language,
                      title = "Ngôn ngữ",
                      subtitle = "Tiếng Việt",
                      onClick = { /* Handle language */ }
                  )
              }



        }



    }


}