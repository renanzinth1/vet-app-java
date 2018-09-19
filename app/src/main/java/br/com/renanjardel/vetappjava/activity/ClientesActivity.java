package br.com.renanjardel.vetappjava.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.adapter.ClientesAdapter;
import br.com.renanjardel.vetappjava.model.Cliente;
import br.com.renanjardel.vetappjava.model.Pessoa;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientesActivity extends AppCompatActivity {

    private ListView clientesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        carregaLista();

        Button botaoNovoCliente = findViewById(R.id.novo_cliente);

        botaoNovoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent goCadastrarCliente = new Intent(ClientesActivity.this, FormClienteActivity.class);
                 startActivity(goCadastrarCliente);
            }
        });

        clientesView = findViewById(R.id.lista_clientes);

        clientesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View view, int position, long id) {
                Cliente cliente = (Cliente) lista.getItemAtPosition(position);
                Intent formCliente = new Intent(ClientesActivity.this, FormClienteActivity.class);
                formCliente.putExtra("cliente", cliente);
                startActivity(formCliente);
            }
        });

        registerForContextMenu(clientesView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Cliente cliente = (Cliente) clientesView.getItemAtPosition(info.position);

        final MenuItem remover = menu.add("Remover");
        remover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                callExcludeAlertDialog(cliente);
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void callExcludeAlertDialog(final Cliente cliente) {
        new AlertDialog
                .Builder(ClientesActivity.this)
                .setTitle("Excluir")
                .setIcon(R.drawable.ic_error_icon)
                .setMessage("Deseja excluir o cliente " + cliente.getNome() + " " + cliente.getSobrenome() + "?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Call<Void> removerCliente = new RetrofitInicializador().getClienteService().remover(cliente.getCodigo());
                        removerCliente.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(ClientesActivity.this, "Cliente removido!", Toast.LENGTH_SHORT).show();
                                carregaLista();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(ClientesActivity.this, "Cliente não removido!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    @Override
    protected void onResume() {
        carregaLista();
        super.onResume();
    }

    private void carregaLista() {
        final ListView clientesView = findViewById(R.id.lista_clientes);

        final Call<List<Cliente>> clientes = new RetrofitInicializador().getClienteService().listar();

        clientes.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                List<Cliente> clientes = response.body();

                ClientesAdapter adapter = new ClientesAdapter(ClientesActivity.this, clientes);
                //ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente> (ClientesActivity.this, android.R.layout.simple_list_item_1, clientes);
                clientesView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {

            }
        });
    }
}
