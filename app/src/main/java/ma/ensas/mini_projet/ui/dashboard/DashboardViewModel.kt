package ma.ensas.mini_projet.ui.dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.data.dao.ProductDao
import ma.ensas.mini_projet.data.dao.ReservationDao
import ma.ensas.mini_projet.data.dao.UserDao
import ma.ensas.mini_projet.data.database.MediMarketDatabase
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.data.entities.Reservation
import ma.ensas.mini_projet.data.entities.User

class DashboardViewModel(app: Application) : AndroidViewModel(app) {

    private val userDao: UserDao = MediMarketDatabase.getDatabase(app).userDao()
    private val productDao: ProductDao = MediMarketDatabase.getDatabase(app).productDao()
    private val reservationDao: ReservationDao = MediMarketDatabase.getDatabase(app).reservationDao()

    private val _users = MutableLiveData<List<User>>()
    private val _availableProducts = MutableLiveData<List<Product>>()
    private val _outOfStockProducts = MutableLiveData<List<Product>>()
    private val _reservations = MutableLiveData<List<Reservation>>()

    val users: LiveData<List<User>> get() = _users
    val availableProduct: LiveData<List<Product>> get() = _availableProducts
    val outOfStockProducts: LiveData<List<Product>> get() = _outOfStockProducts
    val  reservation: LiveData<List<Reservation>> get() = _reservations

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                productDao.getAvailableProducts().observeForever { availableProductsList ->
                    _availableProducts.postValue(availableProductsList)
                }
                productDao.getOutOfStockProducts().observeForever { outOfStockProductsList ->
                    _outOfStockProducts.postValue(outOfStockProductsList)
                }
                userDao.getUsers().observeForever { usersList ->
                    _users.postValue(usersList)
                }
                reservationDao.getAllReservations().collect { reservationsList ->
                    _reservations.postValue(reservationsList)
                }
            }
            catch (ex: Exception){
                Log.i("loadDashboardData", "Failed to load dashboard data: ${ex.message}")
            }
        }
    }

}