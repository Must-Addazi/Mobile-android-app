package ma.ensas.mini_projet.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import ma.ensas.mini_projet.utils.enumerations.Roles

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USERID = "userId"
        private const val KEY_USER_ROLE = "userRole"
    }

    fun saveUserId(userId: Int) {
        prefs.edit().putInt(KEY_USERID, userId).apply()
    }
     fun getUserId(): Int {
         return prefs.getInt(KEY_USERID,-1)
     }
    fun saveUserRole(userRole: Roles) {
        prefs.edit().putString(KEY_USER_ROLE, userRole.name).apply()
    }
    fun getUserRole(): String? {
        return prefs.getString(KEY_USER_ROLE, null)
    }
    fun clearSession() {
        prefs.edit().clear().apply()
    }
}