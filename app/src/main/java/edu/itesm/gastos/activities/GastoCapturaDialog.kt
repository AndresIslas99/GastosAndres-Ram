package edu.itesm.gastos.activities

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import edu.itesm.gastos.databinding.ActivityCapturaGastoBinding
import edu.itesm.gastos.entities.Gasto

class GastoCapturaDialog ( private val onSubmitClickListener: (Gasto) -> Unit):
 DialogFragment(){
     private lateinit var binding: ActivityCapturaGastoBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ActivityCapturaGastoBinding.inflate(LayoutInflater.from(context)) //construye con base en el XML
        val constructor = AlertDialog.Builder(requireActivity())
        constructor.setView(binding.root)
        binding.agregaGasto.setOnClickListener{
            val concepto = binding.conceptoGastoCifra.text.toString()
            val monto = binding.montoGastoCifra.text.toString().toDouble()
            val gasto = Gasto(0,concepto,monto)
            onSubmitClickListener.invoke(gasto)
            dismiss()
        }
        val dialog = constructor.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}