package br.edu.ifsp.scl.ads.prdm.sc303898x.havagas

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.RadioButton
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
            adicionarCelularCb.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    celularEt.visibility = View.VISIBLE
                } else {
                    celularEt.visibility = View.GONE
                    celularEt.text.clear()
                }
            }

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
                val telefoneFixoTexto = telefoneFixoEt.text.toString()
                val telefoneFixo = tipoTelefoneRg.checkedRadioButtonId
                val telefoneFixoSelecionado = findViewById<RadioButton>(telefoneFixo).text.toString()
                val celular = celularEt.text.toString()
                val sexo = sexoRg.checkedRadioButtonId
                val sexoSelecionado = findViewById<RadioButton>(sexo).text.toString()
                val dataNascimento = dataNascimentoEt.text.toString()
                val formacaoSelecionada = formacaoSp.selectedItem.toString()

                val builderMensagem = StringBuilder()
                builderMensagem.appendLine("Nome Completo: ${nomeCompleto}")
                builderMensagem.appendLine("E-mail: ${email.ifEmpty { "Não informado" }}")
                builderMensagem.appendLine("Deseja receber e-mails: ${if (desejaReceberEmails) "Sim" else "Não"}")
                builderMensagem.appendLine("Telefone Fixo: ${telefoneFixoTexto} - ${telefoneFixoSelecionado}")

                if (adicionarCelularCb.isChecked && celular.isNotEmpty()) {
                    builderMensagem.appendLine("Celular: $celular")
                }

                builderMensagem.appendLine("Sexo: ${sexoSelecionado}")
                builderMensagem.appendLine("Data de Nascimento: ${dataNascimento.ifEmpty { "Não selecionada" }}")
                builderMensagem.appendLine("Formação: $formacaoSelecionada")

                when (formacaoSp.selectedItemPosition) {
                    0, 1 -> {
                        builderMensagem.appendLine("Ano de Conclusão: ${anoEt.text.ifEmpty { "Não informado" }}")
                    }
                    2, 3 -> {
                        builderMensagem.appendLine("Ano de Conclusão: ${anoEt.text.ifEmpty { "Não informado" }}")
                        builderMensagem.appendLine("Instituição: ${instituicaoEt.text.ifEmpty { "Não informado" }}")
                    }
                    4, 5 -> {
                        builderMensagem.appendLine("Ano de Conclusão: ${anoEt.text.ifEmpty { "Não informado" }}")
                        builderMensagem.appendLine("Instituição: ${instituicaoEt.text.ifEmpty { "Não informado" }}")
                        builderMensagem.appendLine("Título da Monografia: ${tituloMonografiaEt.text.ifEmpty { "Não informado" }}")
                        builderMensagem.appendLine("Orientador: ${orientadorEt.text.ifEmpty { "Não informado" }}")
                    }
                }

                val dialog = AlertDialog.Builder(this@MainActivity)
                    .setTitle("Dados do Formulário")
                    .setMessage(builderMensagem.toString())
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .create()

                dialog.show()
            }

            limparBt.setOnClickListener {
                nomeEt.setText("")
                emailEt.setText("")
                telefoneFixoEt.setText("")
                celularEt.setText("")
                dataNascimentoEt.setText("")
                vagasEt.setText("")
                anoEt.setText("")
                instituicaoEt.setText("")
                tituloMonografiaEt.setText("")
                orientadorEt.setText("")
                adicionarCelularCb.isChecked = false
                receberEmailsCb.isChecked = false
                formacaoSp.setSelection(0)
                tipoTelefoneRg.check(R.id.comercial_rb)
                sexoRg.check(R.id.masculino_rb)
            }
        }
    }
}