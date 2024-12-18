package ma.ensas.mini_projet.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.data.dao.UserDao
import ma.ensas.mini_projet.data.database.DatabaseProvider
import ma.ensas.mini_projet.data.entities.User

class LoginViewModel(app: Application) : AndroidViewModel(app) {
    private val userDao: UserDao = DatabaseProvider.getDatabase(app).userDao()

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    private val _loggedInUser = MutableLiveData<User?>()
    val loggedInUser: LiveData<User?> get() = _loggedInUser

    fun authenticateUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                val user = userDao.login(username, password)
                if (user != null) {
                    _loggedInUser.postValue(user)
                    _loginStatus.postValue(true)
                } else {
                    _loginStatus.postValue(false)
                }
            }
            catch (ex: Exception) {
                Log.e("Login", "Failed To Login")
            }
        }
    }
}