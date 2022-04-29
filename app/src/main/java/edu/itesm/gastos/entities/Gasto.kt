package edu.itesm.gastos.entities

import android.accounts.AuthenticatorDescription
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class  Gasto(
    @PrimaryKey(autoGenerate = true)
    val id: Int, val description: String, val monto: Double
): Serializable