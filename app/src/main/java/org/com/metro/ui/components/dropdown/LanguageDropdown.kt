package org.com.metro.ui.components.dropdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.com.metro.R
import org.com.metro.utils.LanguageManager

@Composable
fun LanguageDropdown(
    selected: String,
    onLanguageChange: (String) -> Unit,
    isScrolled: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val vietnameseFlag = "\uD83C\uDDFB\uD83C\uDDF3"
    val englishFlag = "\uD83C\uDDFA\uD83C\uDDF8"

    val currentDisplayText = when (selected) {
        "Vietnamese" -> "$vietnameseFlag ${stringResource(R.string.vietnamese)}"
        "English" -> "$englishFlag ${stringResource(R.string.english)}"
        else -> "$vietnameseFlag ${stringResource(R.string.vietnamese)}"
    }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)
        .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Card(
            modifier = Modifier.clickable { expanded = true },
            colors = CardDefaults.cardColors(
                containerColor = if (isScrolled) Color.White.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = currentDisplayText,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                color = if (isScrolled) Color.White else Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf(
                "Vietnamese" to "vi",
                "English" to "en"
            ).forEach { (langName, langCode) ->
                DropdownMenuItem(
                    text = {
                        val flag = if (langCode == "vi") vietnameseFlag else englishFlag
                        val displayName = if (langCode == "vi") stringResource(R.string.vietnamese) else stringResource(R.string.english)
                        Text("$flag $displayName")
                    },
                    onClick = {
                        LanguageManager.setLocale(context, langCode)
                        onLanguageChange(langName)
                        expanded = false
                        // Recreate activity to apply language change
                        (context as? android.app.Activity)?.recreate()
                    }
                )
            }
        }
    }
}