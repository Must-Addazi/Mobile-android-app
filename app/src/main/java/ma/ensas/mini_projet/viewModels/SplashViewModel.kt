package ma.ensas.mini_projet.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.utils.SessionManager

class SplashViewModel(app: Application) : AndroidViewModel(app) {

    private val sessionManager: SessionManager = SessionManager(app)

    private val _userAlreadyLoggedIn = MutableLiveData<Boolean>()
    val userAlreadyLoggedIn: LiveData<Boolean> get() = _userAlreadyLoggedIn

    fun isAlreadyAuthenticated() {
        viewModelScope.launch {
            if(sessionManager.getUsername() != null) {
                _userAlreadyLoggedIn.postValue(true)
            }
            else {
                _userAlreadyLoggedIn.postValue(false)
            }
        }
    }

}