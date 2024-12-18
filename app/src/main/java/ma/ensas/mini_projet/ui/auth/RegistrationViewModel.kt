package ma.ensas.mini_projet.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.data.dao.UserDao
import ma.ensas.mini_projet.data.database.DatabaseProvider
import ma.ensas.mini_projet.data.entities.User

class RegistrationViewModel(app : Application) : AndroidViewModel(app) {
    private val userDao: UserDao = DatabaseProvider.getDatabase(app).userDao()

    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus: LiveData<Boolean> get() = _registrationStatus

    fun registerUser(username: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            val user = User(
                username, email, password,
                phoneNumber = null,
                birthDate = null,
                profileImage = null
            )

            userDao.insertUser(user)

            _registrationStatus.postValue(true)
        }
    }
}