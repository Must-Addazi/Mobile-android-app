package ma.ensas.mini_projet.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ma.ensas.mini_projet.data.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

//    @Transaction
//    @Query("SELECT * FROM users")
//    fun getAllUsersWithReservations() : Flow<List<UserWithReservations>>
//
//    @Transaction
//    @Query("SELECT * FROM users WHERE id = :userId")
//    suspend fun getUserById(userId: Int) : UserWithReservations
//
//    @Transaction
//    @Query("SELECT * FROM users WHERE email = :userEmail")
//    suspend fun getUserByEmail(userEmail: String) : UserWithReservations

}