package edu.itesm.gastos.mvvm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import edu.itesm.gastos.dao.GastoDao
import edu.itesm.gastos.database.GastosDB
import edu.itesm.gastos.entities.Gasto
import kotlinx.coroutines.*
import kotlin.random.Random


class MainActivityViewModel : ViewModel(){
     var liveData: MutableLiveData<List<Gasto>>
    var totalData: Double = 0.0
    init {
        liveData = MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<List<Gasto>>{
        return liveData
    }

    fun getGastos(gastoDao: GastoDao){
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0..10){
                gastoDao.insertGasto(Gasto(0,"Gasto ${i}", Random.nextDouble()*100))
            }
            liveData.postValue(gastoDao.getAllGastos())
        }
    }

    fun getGastos2(gastoDao: GastoDao, gastito : Gasto){
        CoroutineScope(Dispatchers.IO).launch {
            gastoDao.insertGasto(Gasto(0,gastito.description, gastito.monto))
            liveData.postValue(gastoDao.getAllGastos())
        }
    }

     suspend fun sumaDeGastos(gastoDao: GastoDao, contexto : Context){
         val job = MainScope().launch {
             totalData = gastoDao.getSumGastos()
         }
         job.join()
         Toast.makeText(contexto,"$totalData",Toast.LENGTH_LONG).show()
    }

}