package br.com.renanjardel.vetappjava.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import br.com.renanjardel.vetappjava.R;

import br.com.renanjardel.vetappjava.helper.FormularioClienteHelper;
import br.com.renanjardel.vetappjava.model.Cliente;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import br.com.renanjardel.vetappjava.util.MaskEditUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormClienteActivity extends AppCompatActivity {

    private FormularioClienteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cliente);

        helper = new FormularioClienteHelper(this);

        Intent intent = getIntent();
        Cliente cliente = (Cliente) intent.getSerializableExtra("cliente");

        if (cliente != null)
            helper.preencheFormulario(cliente);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_editar:
                helper.campoTrue(true);
                break;

            case R.id.menu_formulario_salvar:

                Cliente cliente = helper.pegaCliente();

                if (cliente.getCodigo() != null) {
                    final Call<Cliente> editar = new RetrofitInicializador().getClienteService().editar(cliente.getCodigo(), cliente);
                    editar.enqueue(new Callback<Cliente>() {
                        @Override
                        public void onResponse(Call<Cliente> call, Response<Cliente> response) {

                            Log.i("onResponse", "Requisição feita com sucesso!");
                            Toast.makeText(FormClienteActivity.this, "Cliente alterado!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Cliente> call, Throwable t) {
                            Log.e("onFailure", "Requisão mal sucedida!");
                            Toast.makeText(FormClienteActivity.this, "Cliente não alterado!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                } else {
                    Call call = new RetrofitInicializador().getClienteService().salvar(cliente);

                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            int resposta = response.code();

                            if (resposta == 201) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormClienteActivity.this, "Cliente salvo!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormClienteActivity.this, "Cliente não salvo!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            //Log.e("onFailure", "Requisão mal sucedida!");
                            //Toast.makeText(FormClienteActivity.this, "Cliente não salvo!", Toast.LENGTH_SHORT).show();
                            //finish();
                        }
                    });
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
