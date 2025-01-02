package ma.ensas.mini_projet.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Transaction
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
    @Transaction
    suspend fun saveReservation(userId: Int, productId: Int, stock: Int, quantity:Int): Long {
        return withContext(Dispatchers.IO) {
            try {
                val upProduct = productDao.decreaseStock(productId,quantity)
                val existingReservation = reservationDao.getReservationsByProductId(productId)
                Log.i("mustpha","existing reservation $existingReservation")
                if (existingReservation != null) {
                    val product= productDao.getProductById(productId)
                    if (product != null) {
                        if(product.stock<0) {
                            reservationDao.updateQuantity(existingReservation.id,ReservationStatus.PENDING,quantity, Date())
                        }else{
                            reservationDao.updateQuantity(existingReservation.id,ReservationStatus.CONFIRMED,quantity, Date())
                        }
                    }
                    existingReservation.id.toLong()
                } else {
                    val reservation = Reservation(
                        id = 0,
                        reservedAt = Date(),
                        status = if (stock > 0) ReservationStatus.CONFIRMED else ReservationStatus.PENDING,
                        userId = userId,
                        productId = productId,
                        quantity = quantity
                    )
                    val reservationId = reservationDao.insertReservation(reservation)
                    val updatedProduct = productDao.getProductById(productId)
                    _product.postValue(updatedProduct)
                    reservationId
                }
            } catch (e: Exception) {
                Log.e("mustapha", "Error during reservation saving: ${e.message}")
                -1L
            }
        }

    }

}

