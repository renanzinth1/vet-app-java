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

import java.util.List;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.form.FormMedicamentoActivity;
import br.com.renanjardel.vetappjava.adapter.ClientesAdapter;
import br.com.renanjardel.vetappjava.model.Cliente;
import br.com.renanjardel.vetappjava.model.Medicamento;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicamentosActivity extends AppCompatActivity {

    private ListView medicamentosView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        carregaLista();

        Button botaoNovoMedicamento = findViewById(R.id.novo_medicamento);

        botaoNovoMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCadastrarMedicamento = new Intent(MedicamentosActivity.this, FormMedicamentoActivity.class);
                startActivity(goCadastrarMedicamento);
            }
        });

        medicamentosView = findViewById(R.id.lista_medicamento);

        medicamentosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View view, int position, long id) {
                Medicamento medicamento = (Medicamento) lista.getItemAtPosition(position);
                Intent formMedicamento = new Intent(MedicamentosActivity.this, FormMedicamentoActivity.class);
                formMedicamento.putExtra("medicamento", medicamento);
                startActivity(formMedicamento);
            }
        });

        registerForContextMenu(medicamentosView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Medicamento medicamento = (Medicamento) medicamentosView.getItemAtPosition(info.position);

        MenuItem remover = menu.add("Remover");
        remover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                callExcludeAlertDialog(medicamento);
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void callExcludeAlertDialog(final Medicamento medicamento) {
        new AlertDialog
                .Builder(MedicamentosActivity.this)
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
                                Toast.makeText(MedicamentosActivity.this, "Medicamento removido!", Toast.LENGTH_SHORT).show();
                                carregaLista();

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(MedicamentosActivity.this, "Medicamento não removido!", Toast.LENGTH_SHORT).show();
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
        //final ListView clientesView = findViewById(R.id.lista_clientes);

        final Call<List<Medicamento>> medicamentos = new RetrofitInicializador().getMedicamentoService().listar();

        medicamentos.enqueue(new Callback<List<Medicamento>>() {
            @Override
            public void onResponse(Call<List<Medicamento>> call, Response<List<Medicamento>> response) {
                List<Medicamento> medicamentos = response.body();

                //ClientesAdapter adapter = new ClientesAdapter(ClientesActivity.this, clientes);
                ArrayAdapter<Medicamento> adapter = new ArrayAdapter<Medicamento>(MedicamentosActivity.this, android.R.layout.simple_list_item_1, medicamentos);
                medicamentosView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Medicamento>> call, Throwable t) {

            }
        });
    }
}
