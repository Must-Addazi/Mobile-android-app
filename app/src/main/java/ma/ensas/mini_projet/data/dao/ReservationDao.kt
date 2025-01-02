package ma.ensas.mini_projet.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.data.entities.Reservation
import ma.ensas.mini_projet.utils.enumerations.ReservationStatus
import java.util.Date

@Dao
interface ReservationDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertReservation(reservation: Reservation):Long

    @Delete
    suspend fun deleteReservationById(reservation: Reservation)

    @Transaction
    @Query("SELECT * FROM reservations")
    fun getAllReservations() : Flow<List<Reservation>>

    @Transaction
    @Query("SELECT * FROM reservations WHERE id = :reservationId")
    suspend fun getReservationsById(reservationId: Int) : Reservation

    @Query("SELECT * FROM reservations WHERE productId = :productId Limit 1")
    suspend fun getReservationsByProductId(productId: Int) : Reservation?

    @Query("DELETE FROM reservations")
    suspend fun deleteAllReservations()
    @Query("UPDATE reservations SET quantity = quantity + :quant,status= :upStatus,reservedAt= :date WHERE id = :reservationId")
    suspend fun updateQuantity(reservationId: Int,upStatus:ReservationStatus,quant:Int, date:Date):Int
}