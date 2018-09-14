package br.com.renanjardel.vetappjava.helper;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.FormClienteActivity;
import br.com.renanjardel.vetappjava.model.Cliente;
import br.com.renanjardel.vetappjava.model.Sexo;

public class FormularioClienteHelper {

    private final EditText campoNome;
    private final EditText campoSobrenome;
    private final EditText campoCpf;
    private final EditText campoTelefone;
    private final EditText campodataNascimento;
    private final RadioGroup sexos;

    private Cliente cliente;

    public FormularioClienteHelper(FormClienteActivity activity) {
        campoNome = activity.findViewById(R.id.campo_cliente_nome);
        campoSobrenome = activity.findViewById(R.id.campo_cliente_sobrenome);
        campoCpf = activity.findViewById(R.id.campo_cliente_cpf);
        campoTelefone = activity.findViewById(R.id.campo_cliente_telefone);
        campodataNascimento = activity.findViewById(R.id.campo_cliente_dataNascimento);
        sexos = activity.findViewById(R.id.radioGroup_sexos);
        cliente = new Cliente();
    }

    public Cliente pegaCliente() {
        cliente.setNome(campoNome.getText().toString());
        cliente.setSobrenome(campoSobrenome.getText().toString());
        cliente.setCpf(campoCpf.getText().toString());
        cliente.setTelefone(campoTelefone.getText().toString());

        cliente.setDataNascimento(campodataNascimento.getText().toString());

        //Pegando qual radiobutton foi selecinada

        int selectecId = sexos.getCheckedRadioButtonId();

        switch (selectecId) {
            case R.id.campo_cliente_masculino:
                cliente.setSexo(Sexo.MASCULINO);
                break;

            case R.id.campo_cliente_feminino:
                cliente.setSexo(Sexo.FEMININO);
                break;
        }

        return cliente;
    }
}
