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
                val vagas = vagasEt.text.toString()

                val builderMensagem = StringBuilder()

                if (nomeCompleto.isNotEmpty()) {
                    builderMensagem.appendLine("Nome Completo: $nomeCompleto")
                }

                if (email.isNotEmpty()) {
                    builderMensagem.appendLine("E-mail: $email")
                    builderMensagem.appendLine("Deseja receber e-mails: ${if (desejaReceberEmails) "Sim" else "Não"}")
                }

                if (telefoneFixoTexto.isNotEmpty()) {
                    builderMensagem.appendLine("Telefone Fixo: $telefoneFixoTexto - $telefoneFixoSelecionado")
                }

                if (adicionarCelularCb.isChecked && celular.isNotEmpty()) {
                    builderMensagem.appendLine("Celular: $celular")
                }

                if(anoEt.text.isNotEmpty()) {
                    builderMensagem.appendLine("Formação: $formacaoSelecionada")
                }

                if (dataNascimento.isNotEmpty()) {
                    builderMensagem.appendLine("Data de Nascimento: $dataNascimento")
                }

                if (sexoSelecionado == "Feminino" || dataNascimento.isNotEmpty()) {
                    builderMensagem.appendLine("Sexo: $sexoSelecionado")
                }

                when (formacaoSp.selectedItemPosition) {
                    0, 1 -> {
                        if (anoEt.text.isNotEmpty()) {
                            builderMensagem.appendLine("Ano de Conclusão: ${anoEt.text}")
                        }
                    }
                    2, 3 -> {
                        if (anoEt.text.isNotEmpty()) {
                            builderMensagem.appendLine("Ano de Conclusão: ${anoEt.text}")
                        }
                        if (instituicaoEt.text.isNotEmpty()) {
                            builderMensagem.appendLine("Instituição: ${instituicaoEt.text}")
                        }
                    }
                    4, 5 -> {
                        if (anoEt.text.isNotEmpty()) {
                            builderMensagem.appendLine("Ano de Conclusão: ${anoEt.text}")
                        }
                        if (instituicaoEt.text.isNotEmpty()) {
                            builderMensagem.appendLine("Instituição: ${instituicaoEt.text}")
                        }
                        if (tituloMonografiaEt.text.isNotEmpty()) {
                            builderMensagem.appendLine("Título da Monografia: ${tituloMonografiaEt.text}")
                        }
                        if (orientadorEt.text.isNotEmpty()) {
                            builderMensagem.appendLine("Orientador: ${orientadorEt.text}")
                        }
                    }
                }

                if (vagas.isNotEmpty()) {
                    builderMensagem.appendLine("Vagas de interesse:\n$vagas")
                }

                val dados: String
                if (builderMensagem.isEmpty()) {
                    dados = "Não foram informados dados."
                } else {
                    dados = builderMensagem.toString()
                }

                val dialog = AlertDialog.Builder(this@MainActivity)
                    .setTitle("Dados do Formulário")
                    .setMessage(dados)
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