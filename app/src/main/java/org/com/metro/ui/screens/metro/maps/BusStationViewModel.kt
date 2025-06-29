package org.com.metro.ui.screens.metro.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.com.metro.model.BusStop
import org.com.metro.repositories.apis.station.BusStationRepository
import javax.inject.Inject

@HiltViewModel
class BusStationViewModel @Inject constructor(
    private val repository: BusStationRepository
) : ViewModel() {

    private val _busStops = MutableStateFlow<List<BusStop>>(emptyList())
    val busStops: StateFlow<List<BusStop>> = _busStops

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getBusStations() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _busStops.value = repository.getBusStations()
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }
}