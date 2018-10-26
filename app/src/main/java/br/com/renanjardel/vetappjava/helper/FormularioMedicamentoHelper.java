package br.com.renanjardel.vetappjava.helper;

import android.view.View;
import android.widget.EditText;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.form.FormMedicamentoActivity;
import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.model.Medicamento;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioMedicamentoHelper {

    private final EditText campoNome;
    private Medicamento medicamento;

    public FormularioMedicamentoHelper(FormMedicamentoActivity activity) {
        campoNome = activity.findViewById(R.id.campo_medicamento_nome);
        medicamento = new Medicamento();
    }

    public Medicamento pegaMedicamento() {
        medicamento.setNome(campoNome.getText().toString());

        return medicamento;
    }


    public void preencherFormulario(Medicamento medicamento) {
        Long codigo = medicamento.getCodigo();

        final Call<Medicamento> retrofit = new RetrofitInicializador().getMedicamentoService().buscarPorId(codigo);
        retrofit.enqueue(new Callback<Medicamento>() {
            @Override
            public void onResponse(Call<Medicamento> call, Response<Medicamento> response) {
                Medicamento medicamento = response.body();

                campoNome.setText(medicamento.getNome());

                FormularioMedicamentoHelper.this.medicamento = medicamento;

                campoTrue(false);

            }

            @Override
            public void onFailure(Call<Medicamento> call, Throwable t) {

            }
        });
    }

    public void campoTrue(boolean value) {
        campoNome.setEnabled(value);
    }
}
