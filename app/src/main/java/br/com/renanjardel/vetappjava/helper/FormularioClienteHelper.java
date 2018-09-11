package br.com.renanjardel.vetappjava.helper;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.FormClienteActivity;
import br.com.renanjardel.vetappjava.modelo.Cliente;
import br.com.renanjardel.vetappjava.modelo.Sexo;

public class FormularioClienteHelper {

    private final EditText campoNome;
    private final EditText campoSobrenome;
    private final EditText campoCpf;
    private final EditText campoTelefone;
    private final EditText campodataNascimento;
    private final RadioGroup sexos;
    //private final RadioButton masculino;
    //private final RadioButton feminino;

    private Cliente cliente;

    public FormularioClienteHelper(FormClienteActivity activity) {
        campoNome = activity.findViewById(R.id.campo_cliente_nome);
        campoSobrenome = activity.findViewById(R.id.campo_cliente_sobrenome);
        campoCpf = activity.findViewById(R.id.campo_cliente_cpf);
        campoTelefone = activity.findViewById(R.id.campo_cliente_telefone);
        campodataNascimento = activity.findViewById(R.id.campo_cliente_dataNascimento);
        sexos = activity.findViewById(R.id.radioGroup_sexos);
        //masculino = activity.findViewById(R.id.campo_cliente_masculino);
        //feminino = activity.findViewById(R.id.campo_cliente_feminino);
        cliente = new Cliente();
    }

    public Cliente pegaAluno() {
        cliente.setNome(campoNome.getText().toString());
        cliente.setSobrenome(campoSobrenome.getText().toString());
        cliente.setCpf(campoCpf.getText().toString());
        cliente.setTelefone(campoTelefone.getText().toString());

        //final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //String dataString = campodataNascimento.getText().toString();
        //final LocalDate dataLD = LocalDate.parse(dataString, formatter);

        cliente.setDataNascimento(campodataNascimento.getText().toString());

        //Pegando qual radiobutton foi selecinada
        sexos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.campo_cliente_masculino:
                        cliente.setSexo(Sexo.MASCULINO);
                        break;

                    case R.id.campo_cliente_feminino:
                        cliente.setSexo(Sexo.FEMININO);
                        break;
                }
            }
        });

        return cliente;
    }
}
