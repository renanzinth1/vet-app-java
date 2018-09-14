package br.com.renanjardel.vetappjava.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.adapter.ClientesAdapter;
import br.com.renanjardel.vetappjava.model.Cliente;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        Button botaoNovoCliente = findViewById(R.id.novo_cliente);

        botaoNovoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent goCadastrarCliente = new Intent(ClientesActivity.this, FormClienteActivity.class);
                 startActivity(goCadastrarCliente);
            }
        });
    }

    @Override
    protected void onResume() {
        final ListView clientesView = findViewById(R.id.lista_clientes);

        Call<List<Cliente>> clientes = new RetrofitInicializador().getClienteService().listar();

        clientes.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                List<Cliente> clientes = response.body();
                ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>
                        (ClientesActivity.this, android.R.layout.simple_list_item_1, clientes);
                clientesView.setAdapter(adapter);

                //ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>
                // (this, android.R.layout.simple_list_item_1, response);
                //clientesView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {

            }
        });

        super.onResume();
    }
}
