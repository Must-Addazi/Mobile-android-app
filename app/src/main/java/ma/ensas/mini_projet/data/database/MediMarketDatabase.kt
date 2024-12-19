package ma.ensas.mini_projet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ma.ensas.mini_projet.data.dao.ProductDao
import ma.ensas.mini_projet.data.dao.ReservationDao
import ma.ensas.mini_projet.data.dao.UserDao
import ma.ensas.mini_projet.utils.Convertors
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.data.entities.Reservation
import ma.ensas.mini_projet.data.entities.User

@Database(entities = [User::class, Product::class, Reservation::class], version = 1, exportSchema = false)
@TypeConverters(Convertors::class)
abstract class MediMarketDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun ReservationDao(): ReservationDao

}