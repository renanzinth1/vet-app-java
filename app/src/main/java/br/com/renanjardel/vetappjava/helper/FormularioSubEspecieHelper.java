package br.com.renanjardel.vetappjava.helper;

import android.widget.EditText;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.form.FormSubEspecieActivity;
import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.model.SubEspecie;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioSubEspecieHelper {

    private final EditText campoNome;
    private SubEspecie subEspecie;

    public FormularioSubEspecieHelper(FormSubEspecieActivity activity) {
        campoNome = activity.findViewById(R.id.campo_subEspecie_nome);
        subEspecie = new SubEspecie();
    }

    public SubEspecie pegaSubEspecie() {
        subEspecie.setNome(campoNome.getText().toString());

        return subEspecie;
    }

    public void preencheFormulario(final SubEspecie subEspecie) {
        Long codigo = subEspecie.getCodigo();

        Call<SubEspecie> retrofit = new RetrofitInicializador().getSubEspecieService().buscarPorId(codigo);

        retrofit.enqueue(new Callback<SubEspecie>() {
            @Override
            public void onResponse(Call<SubEspecie> call, Response<SubEspecie> response) {

                SubEspecie subEspecie = response.body();

                campoNome.setText(subEspecie.getNome());

                FormularioSubEspecieHelper.this.subEspecie = subEspecie;

                campoTrue(false);

            }

            @Override
            public void onFailure(Call<SubEspecie> call, Throwable t) {

            }
        });
    }

    public void campoTrue(boolean value) {
        campoNome.setEnabled(value);
    }
}
