package org.com.metro.ui.components.quickaction

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.com.metro.R
import org.com.metro.constant.ScreenTitle
import org.com.metro.constant.UserRole
import org.com.metro.ui.theme.LightOrange
import org.com.metro.utils.getNavigationRoute
import org.com.metro.utils.screenTitleIconMap
import kotlin.collections.chunked

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuickActionsSection(
    navController: NavHostController,
    userRole: UserRole = UserRole.GUEST
) {
    // Filter the list based on user role
    val allScreenTitles = ScreenTitle.values().toList()

    // Define which items should only be visible to STAFF
    val staffOnlyScreens = listOf(
        ScreenTitle.SCAN_QR_CODE
        // Add any other STAFF-only screens here
    )

    // Filter the list based on user role
    val list = if (userRole == UserRole.STAFF) {
        allScreenTitles
    } else {
        allScreenTitles.filter { it !in staffOnlyScreens }
    }

    val pages = list.chunked(8)
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { pages.size }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp) // đủ để chứa cả Card và indicator
            .padding(horizontal = 10.dp)
    ) {
        // Card chứa grid
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .align(Alignment.TopCenter), // đảm bảo nó nằm trên
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(pages[page].size) { index ->
                            val item = pages[page][index]
                            val iconRes = screenTitleIconMap[item] ?: R.drawable.btn_5
                            val painter = painterResource(id = iconRes)
                            val localizedTitle = stringResource(id = item.titleRes)

                            QuickActionItem(
                                title = localizedTitle,
                                icon = painter,
                                onClick = {
                                    val route = getNavigationRoute(item)
                                    try {
                                        navController.navigate(route)
                                    } catch (e: Exception) {
                                        println("Navigation failed: $route - ${e.message}")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // ✅ Page indicator floating ở đáy
        if (pages.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter) // ✅ Bảo đảm nằm ở đáy của Box
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pages.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                if (index == pagerState.currentPage) LightOrange else Color.Gray.copy(alpha = 0.3f),
                                CircleShape
                            )
                            .padding(horizontal = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun QuickActionItem(
    title: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(90.dp) // FIXED: Set fixed height to prevent overlap
            .padding(4.dp) // FIXED: Reduced padding to fit better
            .clickable { onClick() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp), // FIXED: Reduced internal padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // FIXED: Better spacing distribution
        ) {
            // Icon section
            Box(
                modifier = Modifier
                    .size(32.dp) // FIXED: Slightly smaller icon to save space
                    .clip(CircleShape)
                    .background(Color(0xFF4CAF50).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = title,
                    modifier = Modifier.size(18.dp), // FIXED: Smaller icon size
                    tint = Color(0xFF4CAF50)
                )
            }

            // Text section
            Text(
                text = title,
                fontSize = 10.sp, // FIXED: Slightly smaller text
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 12.sp // FIXED: Better line height for 2-line text
            )
        }
    }
}

