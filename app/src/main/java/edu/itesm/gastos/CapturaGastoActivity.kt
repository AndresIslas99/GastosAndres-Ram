package edu.itesm.gastos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.itesm.gastos.databinding.ActivityCapturaGastoBinding
import edu.itesm.gastos.entities.Gasto

class CapturaGastoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCapturaGastoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCapturaGastoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.agregaGasto.setOnClickListener{
            val concepto = binding.conceptoGastoCifra.text.toString()
            val monto = binding.montoGastoCifra.text.toString().toDouble()
            val gasto = Gasto(0,concepto,monto)
            val intento = Intent()
            intento.putExtra("gasto",gasto)
            setResult(RESULT_OK, intento)
            finish() //que segun esta madre mata el activity
            }
    }
}