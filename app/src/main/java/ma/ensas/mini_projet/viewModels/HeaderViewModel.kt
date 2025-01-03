package ma.ensas.mini_projet.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.data.dao.UserDao
import ma.ensas.mini_projet.data.database.MediMarketDatabase
import ma.ensas.mini_projet.utils.SessionManager

class HeaderViewModel(app: Application) : AndroidViewModel(app) {

    private val userDao: UserDao = MediMarketDatabase.getDatabase(app).userDao()
    private val sessionManager: SessionManager = SessionManager(app)

    private val _username = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()
    private val _imageResId = MutableLiveData<Int>()

    val username: LiveData<String> get() = _username
    val email: LiveData<String> get() = _email
    val imageResId: LiveData<Int> get() = _imageResId

    fun getHeaderDetails() {
        viewModelScope.launch {
            val userId: Int = sessionManager.getUserId()
            try {
                val user = userDao.getUserById(userId) ?: return@launch
                _username.postValue(user.username)
                _email.postValue(user.email)
                _imageResId.postValue(user.imageUri)
            }
            catch(ex: Exception) {
                Log.i("HeaderDrawerDetails", "Failed To Load Header User Details: ${ex.message}")
            }
        }
    }
}