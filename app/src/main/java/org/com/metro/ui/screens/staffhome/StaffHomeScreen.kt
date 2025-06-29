package org.com.metro.ui.screens.staffhome

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.com.metro.constant.UserRole
import org.com.metro.ui.components.common.AppHomeScreen

@Composable
fun StaffHomeScreen(navController: NavHostController) {
    AppHomeScreen(
        navController = navController,
        showFloatingButton = false,
        role = UserRole.STAFF
    ) {
    }
}


@Preview(showBackground = true)
@Composable
fun StaffHomeScreenPreview() {
    val navController = rememberNavController()
    StaffHomeScreen(navController = navController)
}