package ma.ensas.mini_projet.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ma.ensas.mini_projet.data.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Transaction
    @Query("SELECT * FROM users")
    fun getUsers() : Flow<List<User>>

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

//    @Update
//    suspend fun updateUser(user: User)

//    @Query("SELECT * FROM users WHERE userId = :userId")
//    suspend fun getUserById(userId: Int): User?

    // For Image Handling
    @Query("UPDATE users SET imageUri = :imageUri WHERE userId = :userId")
    suspend fun updateUserImageUri(userId: Int, imageUri: String)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: Int): User?

    @Update
    suspend fun updateUser(user: User)
}