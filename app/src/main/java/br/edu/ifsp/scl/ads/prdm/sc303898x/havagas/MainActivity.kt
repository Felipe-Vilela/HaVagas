package br.edu.ifsp.scl.ads.prdm.sc303898x.havagas

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.havagas.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        with(activityMainBinding) {



            dataNascimentoEt.setOnClickListener {
                val calendar = Calendar.getInstance()
                val ano = calendar.get(Calendar.YEAR)
                val mes = calendar.get(Calendar.MONTH)
                val dia = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    this@MainActivity,
                    R.style.DatePickerTheme,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(selectedYear, selectedMonth, selectedDay)
                        val formatoData = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        dataNascimentoEt.setText(formatoData.format(dataSelecionada.time))
                    },
                    ano, mes, dia
                )
                datePickerDialog.show()
            }

            formacaoSp.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        anoLl.visibility = View.GONE
                        instituicaoLl.visibility = View.GONE
                        tituloOrientadorLl.visibility = View.GONE

                        anoEt.setText("")
                        instituicaoEt.setText("")
                        tituloMonografiaEt.setText("")
                        orientadorEt.setText("")

                        when (position) {
                            0, 1 -> {
                                anoLl.visibility = View.VISIBLE
                            }

                            2, 3 -> {
                                anoLl.visibility = View.VISIBLE
                                instituicaoLl.visibility = View.VISIBLE
                            }

                            4, 5 -> {
                                anoLl.visibility = View.VISIBLE
                                instituicaoLl.visibility = View.VISIBLE
                                tituloOrientadorLl.visibility = View.VISIBLE
                            }
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }

            salvarBt.setOnClickListener {
                val nomeCompleto = nomeEt.text.toString()
                val email = emailEt.text.toString()
                val desejaReceberEmails = receberEmailsCb.isChecked
                val dataNascimento = dataNascimentoEt.text.toString()

                val mensagemDoPopup =
                    "Nome Completo: ${nomeCompleto}\n" +
                    "E-mail:  ${email.ifEmpty { "Não Informado" }}\n" +
                    "Deseja receber emails: ${if (desejaReceberEmails) "Sim" else "Não"}\n" +
                    "Data de Nascimento: ${dataNascimento.ifEmpty { "Não selecionada" }}"

                val builder = AlertDialog.Builder(this@MainActivity)

                builder.setTitle("Dados do Formulário")
                builder.setMessage(mensagemDoPopup)

                builder.setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        }
    }
}