package ma.ensas.mini_projet.viewModels

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
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
    val availableProducts: LiveData<List<Product>> get() = _availableProducts
    val outOfStockProducts: LiveData<List<Product>> get() = _outOfStockProducts
    val reservations: LiveData<List<Reservation>> get() = _reservations


    init {
        loadUsers()
        loadProducts()
        loadReservations()
    }

    private fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.i("loadUsers","load users")
                val usersList = userDao.getUsers()
                _users.postValue(usersList)
            } catch (ex: Exception) {
                Log.e("loadUsers", "Error loading users: ${ex.message}")
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val availableProductsList = productDao.getAvailableProducts()
                _availableProducts.postValue(availableProductsList)

                Log.i("products"," available product $availableProductsList")

                val outOfStockProductsList = productDao.getOutOfStockProducts()
                    _outOfStockProducts.postValue(outOfStockProductsList)
                Log.i("outOfStockProducts"," not available product ${outOfStockProductsList}")

            } catch (ex: Exception) {
                Log.e("DashboardViewModel", "Error loading products: ${ex.message}")
            }
        }
    }

    private fun loadReservations() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
              val  reservationsList= reservationDao.getAllReservations1()
                    _reservations.postValue(reservationsList)

            } catch (ex: Exception) {
                Log.e("DashboardViewModel", "Error loading reservations: ${ex.message}")
            }
        }
    }

}
