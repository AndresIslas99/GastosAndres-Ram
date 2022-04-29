package edu.itesm.gastos.dao

import androidx.room.*
import edu.itesm.gastos.entities.Gasto

@Dao
interface GastoDao{
   @Query("SELECT * from Gasto")
   suspend fun getAllGastos(): List<Gasto>

   @Query("SELECT SUM(monto) from Gasto")
   suspend fun getSumGastos(): Double
   //Esta linea suma todo desde la base d datos haciendo el programa más eficiente

   @Insert
   suspend fun insertGasto(gasto: Gasto)
}