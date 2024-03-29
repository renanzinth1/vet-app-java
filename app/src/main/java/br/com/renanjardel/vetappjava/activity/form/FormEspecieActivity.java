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
import android.widget.ImageButton;
import android.widget.Toast;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.SubEspeciesActivity;
import br.com.renanjardel.vetappjava.helper.FormularioEspecieHelper;
import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormEspecieActivity extends AppCompatActivity {

    private FormularioEspecieHelper helper;

    private ImageButton btnListarSubEspecies;
    private Especie especie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_especie);

        helper = new FormularioEspecieHelper(this);
        btnListarSubEspecies = findViewById(R.id.listar_subEspecie);

        Intent intent = getIntent();
        especie = (Especie) intent.getSerializableExtra("especie");

        if (especie != null)
            helper.preencheFormulario(especie);
        else
            btnListarSubEspecies.setVisibility(View.GONE);

        btnListarSubEspecies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormEspecieActivity.this, SubEspeciesActivity.class);
                intent.putExtra("especie", especie);
                startActivity(intent);
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_formulario, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);

        if (especie == null) {
            menu.findItem(R.id.menu_formulario_remover).setVisible(false);
            menu.findItem(R.id.menu_formulario_editar).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_remover:
                this.remover(especie);
                break;

            case R.id.menu_formulario_editar:
                helper.campoTrue(true);
                break;

            case R.id.menu_formulario_salvar:
                Especie especie = helper.pegaEspecie();

                if (especie.getCodigo() != null) {
                    final Call<Especie> editar = new RetrofitInicializador().getEspecieService().editar(especie.getCodigo(), especie);
                    editar.enqueue(new Callback<Especie>() {
                        @Override
                        public void onResponse(Call<Especie> call, Response<Especie> response) {
                            int resposta = response.code();

                            if(resposta == 202){
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormEspecieActivity.this, "Especie alterado!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormEspecieActivity.this, "Especie não alterado!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Especie> call, Throwable t) {

                        }
                    });

                } else {
                    Call call = new RetrofitInicializador().getEspecieService().salvar(especie);

                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            int resposta = response.code();

                            if (resposta == 201) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormEspecieActivity.this, "Especie salvo!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormEspecieActivity.this, "Especie não salvo!", Toast.LENGTH_SHORT).show();
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

    public void remover(final Especie especie) {
        new AlertDialog
                .Builder(FormEspecieActivity.this)
                .setTitle("Excluir")
                .setIcon(R.drawable.ic_error_icon)
                .setMessage("Deseja excluir a especie " + especie.getNome() + "?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Call<Void> removerEspecie = new RetrofitInicializador().getEspecieService().remover(especie.getCodigo());
                        removerEspecie.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormEspecieActivity.this, "Especie removido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormEspecieActivity.this, "Especie não removido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
