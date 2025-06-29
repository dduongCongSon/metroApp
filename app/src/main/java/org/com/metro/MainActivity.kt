package org.com.metro

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import org.com.metro.ui.screens.login.LoginViewModel
import org.com.metro.ui.theme.AppTheme
import org.com.metro.utils.LanguageManager

@dagger.hilt.android.AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Apply saved language
        val currentLang = LanguageManager.getLocale(this)
        LanguageManager.setLocale(this, currentLang)

        // Handle OAuth success from redirect activity
        intent?.let { intent ->
            if (intent.getBooleanExtra("oauth_success", false)) {
                // Use the new method to handle sign-in
                loginViewModel.handleGoogleSignInResult(intent)
            }

            val error = intent.getStringExtra("oauth_error")
            if (error != null) {
                // Update LoginViewModel with error
                loginViewModel.updateLoginError(error)
            }
        }

        setContent {
            AppTheme {
                Navigation()
            }
        }
    }
}