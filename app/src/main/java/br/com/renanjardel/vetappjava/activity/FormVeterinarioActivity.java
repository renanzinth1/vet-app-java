package br.com.renanjardel.vetappjava.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.helper.FormularioClienteHelper;
import br.com.renanjardel.vetappjava.helper.FormularioVeterinarioHelper;
import br.com.renanjardel.vetappjava.model.Cliente;
import br.com.renanjardel.vetappjava.model.Veterinario;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormVeterinarioActivity extends AppCompatActivity {

    private FormularioVeterinarioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_veterinario);

        helper = new FormularioVeterinarioHelper(this);

        Intent intent = getIntent();
        Veterinario veterinario = (Veterinario) intent.getSerializableExtra("veterinario");

        if (veterinario != null)
            helper.preencheFormulario(veterinario);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
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

                Toast.makeText(FormVeterinarioActivity.this, "Veterinário Salvo!", Toast.LENGTH_SHORT).show();
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
