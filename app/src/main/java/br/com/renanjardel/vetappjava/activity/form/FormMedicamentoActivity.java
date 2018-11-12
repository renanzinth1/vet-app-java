package br.com.renanjardel.vetappjava.activity.form;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.helper.FormularioMedicamentoHelper;
import br.com.renanjardel.vetappjava.model.Medicamento;
import br.com.renanjardel.vetappjava.model.Medicamento;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormMedicamentoActivity extends AppCompatActivity {

    private FormularioMedicamentoHelper helper;
    private Medicamento medicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_medicamento);

        helper = new FormularioMedicamentoHelper(this);

        Intent intent = getIntent();
        medicamento = (Medicamento) intent.getSerializableExtra("medicamento");

        if (medicamento != null)
            helper.preencherFormulario(medicamento);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);

        if (medicamento == null) {
            menu.findItem(R.id.menu_formulario_remover).setVisible(false);
            menu.findItem(R.id.menu_formulario_editar).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_formulario_remover:
                this.remover(medicamento);
                break;

            case R.id.menu_formulario_editar:
                helper.campoTrue(true);
                break;

            case R.id.menu_formulario_salvar:
                Medicamento medicamento = helper.pegaMedicamento();

                if (medicamento.getCodigo() != null) {
                    final Call<Medicamento> editar = new RetrofitInicializador().getMedicamentoService().editar(medicamento.getCodigo(), medicamento);

                    editar.enqueue(new Callback<Medicamento>() {
                        @Override
                        public void onResponse(Call<Medicamento> call, Response<Medicamento> response) {
                            int resposta = response.code();

                            if(resposta == 202){
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormMedicamentoActivity.this, "Medicamento alterado!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormMedicamentoActivity.this, "Medicamento não alterado!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Medicamento> call, Throwable t) {

                        }
                    });

                } else {
                    final Call<Void> salvar = new RetrofitInicializador().getMedicamentoService().salvar(medicamento);

                    salvar.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            int resposta = response.code();

                            if (resposta == 201) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormMedicamentoActivity.this, "Medicamento salvo!", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormMedicamentoActivity.this, "Medicamento não salvo!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
        }

        return super.onOptionsItemSelected(item);
    }

    private void remover(final Medicamento medicamento) {
        new AlertDialog
                .Builder(FormMedicamentoActivity.this)
                .setTitle("Excluir")
                .setIcon(R.drawable.ic_error_icon)
                .setMessage("Deseja excluir o medicamento " + medicamento.getNome() + "?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Call<Void> removerMedicamento = new RetrofitInicializador().getMedicamentoService().remover(medicamento.getCodigo());
                        removerMedicamento.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormMedicamentoActivity.this, "Medicamento removido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormMedicamentoActivity.this, "Medicamento não removido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
