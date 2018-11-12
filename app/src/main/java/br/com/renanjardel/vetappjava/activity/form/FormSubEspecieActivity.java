package br.com.renanjardel.vetappjava.activity.form;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.helper.FormularioEspecieHelper;
import br.com.renanjardel.vetappjava.helper.FormularioSubEspecieHelper;
import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.model.SubEspecie;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormSubEspecieActivity extends AppCompatActivity {

    private FormularioSubEspecieHelper helper;
    private Especie especie;
    private SubEspecie subEspecie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_sub_especie);

        helper = new FormularioSubEspecieHelper(this);

        Intent intent = getIntent();
        subEspecie = (SubEspecie) intent.getSerializableExtra("subEspecie");
        especie = (Especie) intent.getSerializableExtra("especie");

        if (subEspecie != null)
            helper.preencheFormulario(subEspecie);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);

        if (subEspecie == null) {
            menu.findItem(R.id.menu_formulario_remover).setVisible(false);
            menu.findItem(R.id.menu_formulario_editar).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_remover:
                this.remover(subEspecie);
                break;

            case R.id.menu_formulario_editar:
                helper.campoTrue(true);
                break;

            case R.id.menu_formulario_salvar:
                SubEspecie subEspecie = helper.pegaSubEspecie();

                if (subEspecie.getCodigo() != null) {

                    final Call<SubEspecie> editar = new RetrofitInicializador().getSubEspecieService().editar(subEspecie.getCodigo(), subEspecie);
                    editar.enqueue(new Callback<SubEspecie>() {
                        @Override
                        public void onResponse(Call<SubEspecie> call, Response<SubEspecie> response) {
                            int resposta = response.code();

                            if(resposta == 202){
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormSubEspecieActivity.this, "SubEspecie alterado!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormSubEspecieActivity.this, "SubEspecie não alterado!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<SubEspecie> call, Throwable t) {

                        }
                    });

                } else {

                    //Setando a especie recuperada em um objeto com o nome subEspecie do tipo SubEspecie
                    subEspecie.setEspecie(especie);
                    Call call = new RetrofitInicializador().getSubEspecieService().salvar(subEspecie);

                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            int resposta = response.code();

                            if (resposta == 201) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormSubEspecieActivity.this, "SubEspecie salvo!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormSubEspecieActivity.this, "SubEspecie não salvo!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            //Log.e("onFailure", "Requisão mal sucedida!");
                            //Toast.makeText(FormEspecieActivity.this, "Especie não salvo!", Toast.LENGTH_SHORT).show();
                            //finish();
                        }
                    });
                }
        }

        return super.onOptionsItemSelected(item);
    }

    public void remover(final SubEspecie subEspecie) {
        new AlertDialog
                .Builder(FormSubEspecieActivity.this)
                .setTitle("Excluir")
                .setIcon(R.drawable.ic_error_icon)
                .setMessage("Deseja excluir a Subespecie " + subEspecie.getNome() + "?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Call<Void> removerSubEspecie = new RetrofitInicializador().getSubEspecieService().remover(subEspecie.getCodigo());
                        removerSubEspecie.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormSubEspecieActivity.this, "Subespecie removido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormSubEspecieActivity.this, "Subespecie não removido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
