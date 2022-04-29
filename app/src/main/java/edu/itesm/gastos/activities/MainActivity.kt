package edu.itesm.gastos.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import edu.itesm.gastos.CapturaGastoActivity
import edu.itesm.gastos.R
import edu.itesm.gastos.dao.GastoDao
import edu.itesm.gastos.database.GastoApp
import edu.itesm.gastos.database.GastosDB
import edu.itesm.gastos.databinding.ActivityMainBinding
import edu.itesm.gastos.entities.Gasto
import edu.itesm.gastos.mvvm.MainActivityViewModel
import edu.itesm.perros.adapter.GastosAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gastoDao: GastoDao
    private lateinit var  gastos: List<Gasto>
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GastosAdapter
    private lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(this@MainActivity, GastosDB::class.java, "gastos").build()
        gastoDao = db.gastoDao()
        //Este cachito es algo nuevo

        initRecycler()
        initViewModel()
        fabAgregaDatos()
    }
    private fun initRecycler(){
        gastos = mutableListOf<Gasto>()
        adapter = GastosAdapter(gastos)
        binding.gastos.layoutManager = LinearLayoutManager(this)
        binding.gastos.adapter = adapter
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, Observer {
            if(!it.isEmpty()){
                adapter.setGastos(it)
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.getGastos(gastoDao)
    }
    private val agregaDatosLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado->
        if (resultado.resultCode== RESULT_OK){
            val gasto : Gasto = resultado.data?.getSerializableExtra("gasto") as Gasto
            Toast.makeText(baseContext, gasto.description, Toast.LENGTH_LONG).show()
        }

    }
    private fun fabAgregaDatos() {
        binding.fab.setOnClickListener{
            /*
            val intento = Intent(baseContext,
            CapturaGastoActivity::class.java)
            agregaDatosLauncher.launch(intento)
            */

            GastoCapturaDialog(onSubmitClickListener = {gasto ->
                Toast.makeText(baseContext,gasto.description,Toast.LENGTH_LONG).show()
                viewModel.getGastos2(gastoDao,gasto)

            }).show(supportFragmentManager,"")
        }


        binding.buttonGastosTot.setOnClickListener{
            MainScope().launch {
                viewModel.sumaDeGastos(gastoDao, this@MainActivity)
            }
        }
    }
}