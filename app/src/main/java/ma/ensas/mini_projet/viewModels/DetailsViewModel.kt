package ma.ensas.mini_projet.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ma.ensas.mini_projet.data.dao.ProductDao
import ma.ensas.mini_projet.data.dao.ReservationDao
import ma.ensas.mini_projet.data.database.MediMarketDatabase
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.data.entities.Reservation
import ma.ensas.mini_projet.utils.enumerations.ReservationStatus
import java.util.Date

class DetailsViewModel(app: Application) : AndroidViewModel(app) {
    private val productDao: ProductDao = MediMarketDatabase.getDatabase(app).productDao()
    private val reservationDao : ReservationDao = MediMarketDatabase.getDatabase(app).reservationDao()
        private val _product = MutableLiveData<Product?>()
        val product: LiveData<Product?> get() = _product

        fun getProductById(productId: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                val productById = productDao.getProductById(productId)

                _product.postValue(productById)
            }
        }
        suspend fun saveReservation(userId:Int, productId: Int, stock:Int):Long{
            val reservation = Reservation(
                id = 0,
                reservedAt = Date(),
                status = if(stock > 0) ReservationStatus.CONFIRMED else ReservationStatus.PENDING,
                userId = userId,
                productId = productId
            )
            return withContext(Dispatchers.IO) {
                reservationDao.insertReservation(reservation)
            }
        }
    }
