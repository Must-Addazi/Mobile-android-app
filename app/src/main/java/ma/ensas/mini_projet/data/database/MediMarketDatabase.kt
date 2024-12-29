package ma.ensas.mini_projet.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ma.ensas.mini_projet.data.dao.ProductDao
import ma.ensas.mini_projet.data.dao.ProductImageDao
import ma.ensas.mini_projet.data.dao.ReservationDao
import ma.ensas.mini_projet.data.dao.UserDao
import ma.ensas.mini_projet.utils.Convertors
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.data.entities.ProductImage
import ma.ensas.mini_projet.data.entities.Reservation
import ma.ensas.mini_projet.data.entities.User

@Database(entities = [User::class, Product::class, ProductImage::class, Reservation::class], version = 1, exportSchema = false)
@TypeConverters(Convertors::class)
abstract class MediMarketDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun reservationDao(): ReservationDao
    abstract fun productImageDao(): ProductImageDao

    companion object {
        @Volatile
        private var INSTANCE: MediMarketDatabase? = null

        fun getDatabase(context: Context): MediMarketDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MediMarketDatabase::class.java,
                    "medi_market_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}