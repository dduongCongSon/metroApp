package org.com.metro.ui.screens.metro.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.com.metro.constant.UserRole
import org.com.metro.ui.components.common.AppHomeScreen
import org.com.metro.ui.screens.guide.GuideSection
import org.com.metro.ui.screens.news.NewsSection

@Composable
fun HomeScreen(navController: NavHostController) {
    AppHomeScreen(
        navController = navController,
        showFloatingButton = true,
        role = UserRole.USER
        ) {
        item {
            Spacer(modifier = Modifier.height(30.dp))
            Box(modifier = Modifier.padding(start = 16.dp)) {
                GuideSection()
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.padding(start = 16.dp)) {
                NewsSection(navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeMetroScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}