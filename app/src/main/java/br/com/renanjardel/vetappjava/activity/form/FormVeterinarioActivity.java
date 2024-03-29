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

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.helper.FormularioVeterinarioHelper;
import br.com.renanjardel.vetappjava.model.Veterinario;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormVeterinarioActivity extends AppCompatActivity {

    private FormularioVeterinarioHelper helper;
    private Veterinario veterinario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_veterinario);

        helper = new FormularioVeterinarioHelper(this);

        Intent intent = getIntent();
        veterinario = (Veterinario) intent.getSerializableExtra("veterinario");

        if (veterinario != null)
            helper.preencheFormulario(veterinario);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_formulario, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);

        if (veterinario == null) {
            menu.findItem(R.id.menu_formulario_remover).setVisible(false);
            menu.findItem(R.id.menu_formulario_editar).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menu_formulario_remover:
                this.remover(veterinario);
                break;

            case R.id.menu_formulario_editar:
                helper.campoTrue(true);
                break;

            case R.id.menu_formulario_salvar:

                Veterinario veterinario = helper.pegaVeterinario();

                if(veterinario.getCodigo() != null) {
                    final Call<Veterinario> editar = new RetrofitInicializador().getVeterinarioService().editar(veterinario.getCodigo(), veterinario);
                    editar.enqueue(new Callback<Veterinario>() {
                        @Override
                        public void onResponse(Call<Veterinario> call, Response<Veterinario> response) {
                            int resposta = response.code();

                            if(resposta == 202){
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormVeterinarioActivity.this, "Veterinário alterado!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormVeterinarioActivity.this, "Veterinário não alterado!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Veterinario> call, Throwable t) {
//                            Log.e("onFailure", "Requisão mal sucedida!");
//                            Toast.makeText(FormVeterinarioActivity.this, "Veterinário não alterado!", Toast.LENGTH_SHORT).show();
//                            finish();
                        }
                    });

                } else {
                    Call call = new RetrofitInicializador().getVeterinarioService().salvar(veterinario);

                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            int resposta = response.code();

                            if (resposta == 201) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormVeterinarioActivity.this, "Veterinario salvo!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormVeterinarioActivity.this, "Veterinario não salvo!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            //Log.e("onFailure", "Requisão mal sucedida!");
                            //Toast.makeText(FormVeterinarioActivity.this, "Veterinario não salvo!", Toast.LENGTH_SHORT).show();
                            //finish();
                        }
                    });
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void remover(final Veterinario veterinario) {
        new AlertDialog
                .Builder(FormVeterinarioActivity.this)
                .setTitle("Excluir")
                .setIcon(R.drawable.ic_error_icon)
                .setMessage("Deseja excluir o veterinário " + veterinario.getNome() + " " + veterinario.getSobrenome() + "?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Call<Void> removerVeterinario = new RetrofitInicializador().getVeterinarioService().remover(veterinario.getCodigo());
                        removerVeterinario.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormVeterinarioActivity.this, "Veterinario removido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormVeterinarioActivity.this, "Veterinario não removido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
