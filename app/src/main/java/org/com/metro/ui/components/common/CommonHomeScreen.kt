package org.com.metro.ui.components.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.com.metro.R
import org.com.metro.constant.UserRole
import org.com.metro.ui.components.floatingButton.FloatingButton
import org.com.metro.ui.components.quickaction.QuickActionsSection

@Composable
fun AppHomeScreen(
    navController: NavHostController,
    showFloatingButton: Boolean = true,
    role: UserRole = UserRole.GUEST,
    userName: String = "Guest",
    contentAfterBanner: LazyListScope.() -> Unit
) {

    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset > 0 }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFE8F5E8), Color.White)
                    )
                ),
            contentPadding = PaddingValues(top = 0.dp, bottom = 240.dp)
        ) {
            item {
                Box(modifier = Modifier.height(450.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Black.copy(alpha = 0.3f), Color.Transparent)
                                )
                            )
                            .padding(horizontal = 20.dp)
                            .padding(top = 40.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Good Morning,",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = userName,
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(Color.White, CircleShape)
                                    .clickable { /* TODO: Open QR Code */ },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    "Which station are you going to?",
                                    color = Color.Gray.copy(alpha = 0.7f)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.Gray
                                )
                            }
                        )


                        Row(
                            modifier = Modifier
                                .offset(y = 30.dp)
                        ) {
                            QuickActionsSection(navController, userRole = role)
                        }

                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            contentAfterBanner()
        }

        if (showFloatingButton) {
            FloatingButton(
                onClick = {
                    Log.d("FloatingButton", "Clicked!")
                },
                icon = Icons.Default.Phone,
                contentDescription = "Phone Icon",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 50.dp, end = 16.dp)
            )
        }
    }
}