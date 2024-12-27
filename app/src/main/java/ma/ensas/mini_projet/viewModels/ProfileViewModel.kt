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
import ma.ensas.mini_projet.data.entities.User
import ma.ensas.mini_projet.utils.SessionManager

class ProfileViewModel(app: Application) : AndroidViewModel(app) {
    private val userDao: UserDao = MediMarketDatabase.getDatabase(app).userDao()
    private val sessionManager: SessionManager = SessionManager(app)

    private val _userDetails = MutableLiveData<User?>()
    val userDetails: LiveData<User?> get() = _userDetails

    fun getUserDetails() {
        viewModelScope.launch {
            val email = sessionManager.getUsername()
            if (email == null) {
                _userDetails.postValue(null)
                Log.e("ProfileViewModel", "User is not authenticated")
            }
            else {
                try {
                    val user = userDao.getUserByEmail(email)
                    _userDetails.postValue(user)
                } catch (ex: RuntimeException) {
                    Log.e("loadUserDetails", "Failed To Load User Details")
                    _userDetails.postValue(null)
                }
            }
        }
    }

    fun applyChanges(user: User) {
        viewModelScope.launch {
            try {
                userDao.updateUser(user)
                _userDetails.postValue(user)
            } catch (ex: Exception) {
                Log.e("applyChanges", "Failed to update user: ${ex.message}")
            }
        }
    }
}