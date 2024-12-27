package ma.ensas.mini_projet.ui.reservation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.data.DTOS.ReservationDTO
import ma.ensas.mini_projet.data.dao.ProductDao
import ma.ensas.mini_projet.data.dao.ReservationDao
import ma.ensas.mini_projet.data.database.MediMarketDatabase
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.data.entities.Reservation

class ReservationViewModel(app: Application) : AndroidViewModel(app) {
    private val reservationDao: ReservationDao = MediMarketDatabase.getDatabase(app).reservationDao()
    private val productDao : ProductDao = MediMarketDatabase.getDatabase(app).productDao()
    private val _reservations = MutableLiveData<List<Reservation>>()
    val reservations: LiveData<List<Reservation>> get() = _reservations
    private val _reservationDTOs = MutableLiveData<List<ReservationDTO>>()
    val reservationDTOs: LiveData<List<ReservationDTO>> get() = _reservationDTOs

    init {
        loadReservationsFromDatabase()
    }
    private fun loadReservationsFromDatabase() {
        viewModelScope.launch {
            reservationDao.getAllReservations().collect { reservations ->
                val reservationDTOList = reservations.map { reservation ->
                    val productName = productDao.getProductNameById(reservation.productId)
                    mapToReservationDTO(reservation, productName)
                }
                _reservationDTOs.postValue(reservationDTOList)
            }
        }
    }

    private fun mapToReservationDTO(reservation: Reservation, productName: String): ReservationDTO {
        return ReservationDTO(
            id = reservation.id,
            reservedAt = reservation.reservedAt,
            status = reservation.status,
            userId = reservation.userId,
            productName = productName
        )
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is reservation Fragment"
    }
    val text: LiveData<String> = _text
}