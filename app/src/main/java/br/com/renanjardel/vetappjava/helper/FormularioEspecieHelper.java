package br.com.renanjardel.vetappjava.helper;

import android.widget.EditText;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.form.FormEspecieActivity;
import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioEspecieHelper{

    private final EditText campoNome;
    private Especie especie;


    public FormularioEspecieHelper(FormEspecieActivity activity) {
        campoNome = activity.findViewById(R.id.campo_especie_nome);
        especie = new Especie();
    }

    public Especie pegaEspecie() {
        especie.setNome(campoNome.getText().toString());

        return especie;
    }

    public void preencheFormulario(final Especie especie) {
        Long codigo = especie.getCodigo();

        Call<Especie> retrofit = new RetrofitInicializador().getEspecieService().buscarPorId(codigo);

        retrofit.enqueue(new Callback<Especie>() {
            @Override
            public void onResponse(Call<Especie> call, Response<Especie> response) {

                Especie especie = response.body();

                campoNome.setText(especie.getNome());

                FormularioEspecieHelper.this.especie = especie;

                campoTrue(false);

            }

            @Override
            public void onFailure(Call<Especie> call, Throwable t) {

            }
        });
    }

    public void campoTrue(boolean value) {
        campoNome.setEnabled(value);
    }
}
