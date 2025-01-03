package ma.ensas.mini_projet.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
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

    private var availableProductsObserver: Observer<List<Product>>? = null
    private var outOfStockProductsObserver: Observer<List<Product>>? = null
    private var usersObserver: Observer<List<User>>? = null

    init {
        loadUsers()
        loadProducts()
        loadReservations()
    }

    private fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                usersObserver = Observer { usersList ->
                    _users.postValue(usersList)
                }
                userDao.getUsers().observeForever(usersObserver!!)
            } catch (ex: Exception) {
                Log.e("DashboardViewModel", "Error loading users: ${ex.message}")
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                availableProductsObserver = Observer { availableProductsList ->
                    _availableProducts.postValue(availableProductsList)
                }
                productDao.getAvailableProducts().observeForever(availableProductsObserver!!)

                outOfStockProductsObserver = Observer { outOfStockProductsList ->
                    _outOfStockProducts.postValue(outOfStockProductsList)
                }
                productDao.getOutOfStockProducts().observeForever(outOfStockProductsObserver!!)
            } catch (ex: Exception) {
                Log.e("DashboardViewModel", "Error loading products: ${ex.message}")
            }
        }
    }

    private fun loadReservations() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                reservationDao.getAllReservations().collect { reservationsList ->
                    _reservations.postValue(reservationsList)
                }
            } catch (ex: Exception) {
                Log.e("DashboardViewModel", "Error loading reservations: ${ex.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Supprimer les observateurs ajoutés avec observeForever
        availableProductsObserver?.let { productDao.getAvailableProducts().removeObserver(it) }
        outOfStockProductsObserver?.let { productDao.getOutOfStockProducts().removeObserver(it) }
        usersObserver?.let { userDao.getUsers().removeObserver(it) }
    }
}
