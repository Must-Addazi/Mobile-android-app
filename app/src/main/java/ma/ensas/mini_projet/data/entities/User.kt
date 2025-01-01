package ma.ensas.mini_projet.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ma.ensas.mini_projet.R
import ma.ensas.mini_projet.utils.enumerations.Roles
import java.util.Date

@Entity(tableName = "users")
data class User(
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String?,
    val birthDate: Date?,
    val imageUri: String? = null,
    val role: Roles = Roles.USER,

    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
)