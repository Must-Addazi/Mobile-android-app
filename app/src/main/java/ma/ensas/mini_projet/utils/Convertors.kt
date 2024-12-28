package ma.ensas.mini_projet.utils

import androidx.room.TypeConverter
import ma.ensas.mini_projet.utils.enumerations.ProductTypes
import ma.ensas.mini_projet.utils.enumerations.ReservationStatus
import ma.ensas.mini_projet.utils.enumerations.Roles
import java.util.Date

class Convertors {

    @TypeConverter
    fun fromReservationStatus(status: ReservationStatus) : String {
        return status.name
    }

    @TypeConverter
    fun toReservationStatus(status: String) : ReservationStatus {
        return ReservationStatus.valueOf(status)
    }

    @TypeConverter
    fun fromProductTypes(prodType: ProductTypes) : String {
        return prodType.name
    }

    @TypeConverter
    fun toProductTypes(prodType: String) : ProductTypes {
        return ProductTypes.valueOf(prodType)
    }

    @TypeConverter
    fun fromRoles(role: Roles) : String {
        return role.name
    }

    @TypeConverter
    fun toRoles(role: String) : Roles {
        return Roles.valueOf(role)
    }

    @TypeConverter
    fun toDate(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

}