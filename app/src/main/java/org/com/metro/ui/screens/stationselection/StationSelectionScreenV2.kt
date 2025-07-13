package org.com.metro.ui.screens.stationselection


import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.com.metro.Screen
import org.com.metro.Station
import org.com.metro.ui.screens.metro.buyticket.FareMatrixViewModel
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxScope

private val AppWhite = Color(0xFFFFFFFF)
private val AppLightGray = Color(0xFFF0F0F0)
private val AppMediumGray = Color(0xFF676767)
private val AppDarkGray = Color(0xFF424242)
private val BlueDark = Color(0xFF1565C0)

enum class ActiveInput { FROM, TO }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationSelectionScreen(
    navController: NavHostController,
    stationViewModel: StationSelectionViewModel = hiltViewModel(),
    fareMatrixViewModel: FareMatrixViewModel = hiltViewModel()
) {
    val uiState by stationViewModel.uiState.collectAsState()
    val fareMatrixUiState by fareMatrixViewModel.uiState.collectAsState()
    var selectedEntryStationObj by remember { mutableStateOf<Station?>(null) }
    var selectedExitStationObj by remember { mutableStateOf<Station?>(null) }
    var fromStationText by remember { mutableStateOf("") }
    var toStationText by remember { mutableStateOf("") }
    var activeInput by remember { mutableStateOf<ActiveInput?>(null) }
    var isNavigationTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        stationViewModel.fetchStations()
    }

    LaunchedEffect(fareMatrixUiState) {
        if (!isNavigationTriggered) {
            return@LaunchedEffect
        }

        if (fareMatrixUiState.isLoading) {
            return@LaunchedEffect
        }

        if (fareMatrixUiState.calculatedFare != null) {
            val entryStationId = selectedEntryStationObj?.stationId
            val exitStationId = selectedExitStationObj?.stationId

            if (entryStationId != null && exitStationId != null) {
                Log.d(TAG, "Navigating to CalculatedFareScreen with fare: ${fareMatrixUiState.calculatedFare}")
                navController.navigate(
                    Screen.CalculatedFare.createRoute(
                        entryStationId = entryStationId,
                        exitStationId = exitStationId
                    )
                )
            }
        } else if (fareMatrixUiState.errorMessage != null) {
            Log.e(TAG, "Fare calculation failed: ${fareMatrixUiState.errorMessage}")
            isNavigationTriggered = false
        }
    }

    Scaffold(
        bottomBar = {
            ActionButtons(
                navController = navController,
                isCompleteEnabled = selectedEntryStationObj != null && selectedExitStationObj != null && !fareMatrixUiState.isLoading
            ) {
                if (selectedEntryStationObj != null && selectedExitStationObj != null) {
                    isNavigationTriggered = true
                    fareMatrixViewModel.getFareForStations(
                        selectedEntryStationObj!!.stationId,
                        selectedExitStationObj!!.stationId
                    )
                }
            }
        },
        containerColor = AppLightGray
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StationSelectionInputs(
                fromStationText = fromStationText,
                toStationText = toStationText,
                activeInput = activeInput,
                onInputFocus = { focusedInput -> activeInput = focusedInput }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.weight(1f)) {
                StationList(
                    uiState = uiState,
                    onStationSelected = { selectedStation ->
                        when (activeInput) {
                            ActiveInput.FROM -> {
                                selectedEntryStationObj = selectedStation
                                fromStationText = selectedStation.name
                                if (selectedExitStationObj == null) {
                                    activeInput = ActiveInput.TO
                                } else {
                                    activeInput = null
                                }
                            }
                            ActiveInput.TO -> {
                                if (selectedStation == selectedEntryStationObj) {
                                    Log.w(TAG, "Điểm đi và Điểm đến không thể trùng nhau.")
                                    return@StationList
                                }
                                selectedExitStationObj = selectedStation
                                toStationText = selectedStation.name
                                activeInput = null // Deselect input after choosing
                            }
                            null -> {
                                activeInput = ActiveInput.FROM
                                selectedEntryStationObj = selectedStation
                                fromStationText = selectedStation.name
                                if (selectedExitStationObj == null) {
                                    activeInput = ActiveInput.TO
                                } else {
                                    activeInput = null
                                }
                            }
                        }
                    },
                    selectedEntryStation = selectedEntryStationObj,
                    selectedExitStation = selectedExitStationObj
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = { Text(title, color = BlueDark, fontWeight = FontWeight.Bold) },
        navigationIcon = navigationIcon ?: {},
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = AppWhite
        )
    )
}

@Composable
private fun StationSelectionInputs(
    fromStationText: String,
    toStationText: String,
    activeInput: ActiveInput?,
    onInputFocus: (ActiveInput) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ReadOnlyTextField(
                label = "Điểm đi",
                value = fromStationText,
                placeholder = "Chọn ga khởi hành...",
                isActive = activeInput == ActiveInput.FROM,
                onClick = { onInputFocus(ActiveInput.FROM) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ReadOnlyTextField(
                label = "Điểm đến",
                value = toStationText,
                placeholder = "Chọn ga đến...",
                isActive = activeInput == ActiveInput.TO,
                onClick = { onInputFocus(ActiveInput.TO) }
            )
        }
    }
}

@Composable
private fun ReadOnlyTextField(
    label: String,
    value: String,
    placeholder: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Column {
        Text(text = label, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = AppDarkGray)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = if (isActive) 2.dp else 1.dp,
                    color = if (isActive) BlueDark else AppMediumGray.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable(onClick = onClick)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = BlueDark)
                Spacer(modifier = Modifier.width(8.dp))
                val textToShow = value.ifEmpty { placeholder }
                val color = if (value.isEmpty()) AppMediumGray else AppDarkGray
                Text(text = textToShow, color = color, fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun StationListItem(station: Station, onClick: () -> Unit, isSelected: Boolean, isEnabled: Boolean) {
    val backgroundColor = when {
        isSelected -> BlueDark.copy(alpha = 0.1f)
        !isEnabled -> AppLightGray.copy(alpha = 0.5f)
        else -> AppWhite
    }
    val borderColor = when {
        isSelected -> BlueDark
        !isEnabled -> Color.Transparent
        else -> Color.Transparent
    }
    val textColor = if (!isEnabled) AppMediumGray else AppDarkGray

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isEnabled, onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = if (isSelected) BorderStroke(2.dp, borderColor) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = null,
                tint = BlueDark
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    station.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
                Text(
                    station.address,
                    fontSize = 12.sp,
                    color = AppMediumGray
                )
            }
        }
    }
}

@Composable
private fun BoxScope.StationList(
    uiState: StationSelectionUiState,
    onStationSelected: (Station) -> Unit,
    selectedEntryStation: Station?,
    selectedExitStation: Station?
) {
    when {
        uiState.isLoading -> {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        uiState.errorMessage != null -> {
            Text(
                text = "Lỗi: ${uiState.errorMessage}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        uiState.stations.isNotEmpty() -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                item {
                    Text(
                        "Vui lòng chọn một nhà ga",
                        fontWeight = FontWeight.SemiBold,
                        color = AppDarkGray
                    )
                }
                items(uiState.stations, key = { it.stationId }) { station ->
                    val isSelected = station == selectedEntryStation || station == selectedExitStation
                    val isEnabled = (station != selectedEntryStation)

                    StationListItem(
                        station = station,
                        onClick = { onStationSelected(station) },
                        isSelected = isSelected,
                        isEnabled = isEnabled
                    )
                }
            }
        }
        else -> {
            Text(
                text = "Không có ga nào được tìm thấy.",
                color = AppMediumGray,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun ActionButtons(
    navController: NavHostController,
    isCompleteEnabled: Boolean,
    onComplete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppMediumGray.copy(alpha = 1f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại", tint = AppWhite)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Quay lại", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AppWhite)
        }

        Button(
            onClick = onComplete,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .height(56.dp),
            enabled = isCompleteEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueDark,
                disabledContainerColor = BlueDark.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            // Show loading indicator inside button when fare is being calculated
            val fareMatrixViewModel: FareMatrixViewModel = hiltViewModel() // Get ViewModel to check loading state
            val fareMatrixUiState by fareMatrixViewModel.uiState.collectAsState()

            if (fareMatrixUiState.isLoading && fareMatrixUiState.calculatedFare == null) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = AppWhite,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Hoàn tất", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AppWhite)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Hoàn tất", tint = AppWhite)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ActionButtons() {

}