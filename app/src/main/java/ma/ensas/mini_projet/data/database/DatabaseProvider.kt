package ma.ensas.mini_projet.data.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
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