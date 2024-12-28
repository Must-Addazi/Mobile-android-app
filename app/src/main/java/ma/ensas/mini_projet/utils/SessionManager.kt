package ma.ensas.mini_projet.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USERID ="userId"
    }

    fun saveUserId(userId: Int) {
        prefs.edit().putInt(KEY_USERID, userId).apply()
    }
     fun getUserId(): Int {
         return prefs.getInt(KEY_USERID,-1)
     }
    fun clearSession() {
        prefs.edit().clear().apply()
    }
}