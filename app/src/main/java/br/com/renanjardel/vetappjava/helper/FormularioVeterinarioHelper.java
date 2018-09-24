package br.com.renanjardel.vetappjava.helper;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.FormVeterinarioActivity;
import br.com.renanjardel.vetappjava.model.Veterinario;
import br.com.renanjardel.vetappjava.model.Sexo;
import br.com.renanjardel.vetappjava.model.Veterinario;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioVeterinarioHelper {

    private final EditText campoNome;
    private final EditText campoSobrenome;
    private final EditText campoCfmv;
    private final EditText campoTelefone;
    private final EditText campodataNascimento;
    private final RadioGroup sexos;
    private final RadioButton campoMasculino;
    private final RadioButton campoFeminino;
    private Veterinario veterinario;

    public FormularioVeterinarioHelper(FormVeterinarioActivity activity) {
        campoNome = activity.findViewById(R.id.campo_veterinario_nome);
        campoSobrenome = activity.findViewById(R.id.campo_veterinario_sobrenome);
        campoCfmv = activity.findViewById(R.id.campo_veterinario_cfmv);
        campoTelefone = activity.findViewById(R.id.campo_veterinario_telefone);
        campodataNascimento = activity.findViewById(R.id.campo_veterinario_dataNascimento);
        sexos = activity.findViewById(R.id.radioGroup_sexos);
        campoMasculino = activity.findViewById(R.id.campo_veterinario_masculino);
        campoFeminino = activity.findViewById(R.id.campo_veterinario_feminino);
        veterinario = new Veterinario();
    }

    public Veterinario pegaVeterinario() {

        veterinario.setNome(campoNome.getText().toString());
        veterinario.setSobrenome(campoSobrenome.getText().toString());
        veterinario.setCfmv(campoCfmv.getText().toString());
        veterinario.setTelefone(campoTelefone.getText().toString());

        veterinario.setDataNascimento(campodataNascimento.getText().toString());

        //Pegando qual radiobutton foi selecinada
        int selectecId = sexos.getCheckedRadioButtonId();

        switch (selectecId) {
            case R.id.campo_veterinario_masculino:
                veterinario.setSexo(Sexo.MASCULINO);
                break;

            case R.id.campo_veterinario_feminino:
                veterinario.setSexo(Sexo.FEMININO);
                break;
        }

        return veterinario;
    }

    public void preencheFormulario(final Veterinario veterinario) {
        Long codigo = veterinario.getCodigo();

        Call<Veterinario> retrofit = new RetrofitInicializador().getVeterinarioService().buscarPorId(codigo);

        retrofit.enqueue(new Callback<Veterinario>() {
            @Override
            public void onResponse(Call<Veterinario> call, Response<Veterinario> response) {

                Veterinario veterinario = response.body();

                campoNome.setText(veterinario.getNome());
                campoSobrenome.setText(veterinario.getSobrenome());
                campoCfmv.setText(veterinario.getCfmv());
                campoTelefone.setText(veterinario.getTelefone());
                campodataNascimento.setText(veterinario.getDataNascimento());

                final Sexo sexo = veterinario.getSexo();

                if (sexo == Sexo.MASCULINO) {
                    campoMasculino.setChecked(true);
                    campoFeminino.setChecked(false);

                } else {
                    campoFeminino.setChecked(true);
                    campoMasculino.setChecked(false);
                }

                FormularioVeterinarioHelper.this.veterinario = veterinario;

            }

            @Override
            public void onFailure(Call<Veterinario> call, Throwable t) {

            }
        });
    }
}
