package ma.ensas.mini_projet.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.data.dto.ReservationDTO
import ma.ensas.mini_projet.data.dao.ProductDao
import ma.ensas.mini_projet.data.dao.ReservationDao
import ma.ensas.mini_projet.data.database.MediMarketDatabase
import ma.ensas.mini_projet.data.entities.Reservation

class ReservationViewModel(app: Application) : AndroidViewModel(app) {
    private val reservationDao: ReservationDao = MediMarketDatabase.getDatabase(app).reservationDao()
    private val productDao : ProductDao = MediMarketDatabase.getDatabase(app).productDao()
    private val _reservationDTOs = MutableLiveData<List<ReservationDTO>>()
    val reservationDTOs: LiveData<List<ReservationDTO>> get() = _reservationDTOs

    init {
        loadReservations()
    }

    private fun loadReservations() {
        viewModelScope.launch {
            try {
                Log.i("mustapha", "reservation load ")
                reservationDao.getAllReservations().collect { reservations ->
                    Log.i("mustapha","reservations loaded $reservations")
                    val reservationDTOList = reservations.map { reservation ->
                        val productName = productDao.getProductNameById(reservation.productId)
                        mapToReservationDTO(reservation, productName)
                    }
                    _reservationDTOs.postValue(reservationDTOList)
                }
            } catch (e: Exception) {
                Log.e("mustapha", "Error loading reservations", e)
            }
        }

    }

    private fun mapToReservationDTO(reservation: Reservation, productName: String): ReservationDTO {
        return ReservationDTO(
            id = reservation.id,
            reservedAt = reservation.reservedAt,
            status = reservation.status,
            userId = reservation.userId,
            quantity = reservation.quantity,
            productName = productName
        )
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is reservation Fragment"
    }
    val text: LiveData<String> = _text
}