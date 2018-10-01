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
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioClienteHelper {

    private final EditText campoNome;
    private final EditText campoSobrenome;
    private final EditText campoCpf;
    private final EditText campoTelefone;
    private final EditText campodataNascimento;
    private final RadioGroup sexos;
    private final RadioButton campoMasculino;
    private final RadioButton campoFeminino;

    private Cliente cliente;

    public FormularioClienteHelper(FormClienteActivity activity) {
        campoNome = activity.findViewById(R.id.campo_cliente_nome);
        campoSobrenome = activity.findViewById(R.id.campo_cliente_sobrenome);
        campoCpf = activity.findViewById(R.id.campo_cliente_cpf);
        campoTelefone = activity.findViewById(R.id.campo_cliente_telefone);
        campodataNascimento = activity.findViewById(R.id.campo_cliente_dataNascimento);
        sexos = activity.findViewById(R.id.radioGroup_sexos);
        campoMasculino = activity.findViewById(R.id.campo_cliente_masculino);
        campoFeminino = activity.findViewById(R.id.campo_cliente_feminino);
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

    public void preencheFormulario(final Cliente cliente) {
        Long codigo = cliente.getCodigo();

        Call<Cliente> retrofit = new RetrofitInicializador().getClienteService().buscarPorId(codigo);

        retrofit.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {

                Cliente cliente = response.body();

                campoNome.setText(cliente.getNome());
                campoSobrenome.setText(cliente.getSobrenome());
                campoCpf.setText(cliente.getCpf());
                campoTelefone.setText(cliente.getTelefone());
                campodataNascimento.setText(cliente.getDataNascimento());

                final Sexo sexo = cliente.getSexo();

                if (sexo == Sexo.MASCULINO) {
                    campoMasculino.setChecked(true);
                    campoFeminino.setChecked(false);

                } else {
                    campoFeminino.setChecked(true);
                    campoMasculino.setChecked(false);
                }

                FormularioClienteHelper.this.cliente = cliente;

                campoTrue(false);

            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {

            }
        });
    }

    public void campoTrue(boolean value) {
        campoNome.setEnabled(value);
        campoSobrenome.setEnabled(value);
        campoCpf.setEnabled(value);
        campoTelefone.setEnabled(value);
        campodataNascimento.setEnabled(value);
        campoMasculino.setEnabled(value);
        campoFeminino.setEnabled(value);
    }
}
