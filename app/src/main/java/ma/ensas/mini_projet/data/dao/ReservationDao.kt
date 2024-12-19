package ma.ensas.mini_projet.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ma.ensas.mini_projet.data.entities.Reservation

@Dao
interface ReservationDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertReservation(reservation: Reservation)

    @Delete
    suspend fun deleteReservationById(reservation: Reservation)

    @Transaction
    @Query("SELECT * FROM reservations")
    fun getAllReservations() : Flow<List<Reservation>>

    @Transaction
    @Query("SELECT * FROM reservations WHERE id = :reservationId")
    suspend fun getReservationsById(reservationId: Int) : Reservation

}