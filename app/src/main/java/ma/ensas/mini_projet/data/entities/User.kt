package ma.ensas.mini_projet.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ma.ensas.mini_projet.utils.enumerations.Roles
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val birthDate: Date,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val profileImage: ByteArray?,
    val role: Roles
)
