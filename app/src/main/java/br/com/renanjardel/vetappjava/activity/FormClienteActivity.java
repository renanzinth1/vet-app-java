package br.com.renanjardel.vetappjava.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.renanjardel.vetappjava.R;

import br.com.renanjardel.vetappjava.helper.FormularioClienteHelper;
import br.com.renanjardel.vetappjava.model.Cliente;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_salvar:

                Cliente cliente = helper.pegaCliente();

                Call call = new RetrofitInicializador().getClienteService().registrar(cliente);

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        Log.i("onResponse", "Requisição feita com sucesso!");
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.e("onFailure", "Requisão mal sucedida!");
                    }
                });

                Toast.makeText(FormClienteActivity.this, "Cliente salvo!", Toast.LENGTH_SHORT).show();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
