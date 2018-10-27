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
import br.com.renanjardel.vetappjava.activity.form.FormEspecieActivity;
import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EspeciesActivity extends AppCompatActivity {

    private ListView especiesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especies);

        carregaLista();

        Button botaoNovaEspecie = findViewById(R.id.nova_especie);

        botaoNovaEspecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCadastrarEspecie = new Intent(EspeciesActivity.this, FormEspecieActivity.class);
                startActivity(goCadastrarEspecie);
            }
        });

        especiesView = findViewById(R.id.lista_especie);

        especiesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View view, int position, long id) {
                Especie especie = (Especie) lista.getItemAtPosition(position);
                Intent formEspecie = new Intent(EspeciesActivity.this, FormEspecieActivity.class);
                formEspecie.putExtra("especie", especie);
                startActivity(formEspecie);
            }
        });

        registerForContextMenu(especiesView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Especie especie = (Especie) especiesView.getItemAtPosition(info.position);

        final MenuItem remover = menu.add("Remover");
        remover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                callExcludeAlertDialog(especie);
                return false;
            }
        });

        final MenuItem subespecies = menu.add("Subespécies");
        subespecies.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final Intent goToListarSubEspecies = new Intent(EspeciesActivity.this, SubEspeciesActivity.class);
                goToListarSubEspecies.putExtra("especie", especie);
                startActivity(goToListarSubEspecies);
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void callExcludeAlertDialog(final Especie especie) {
        new AlertDialog
                .Builder(EspeciesActivity.this)
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
                                Toast.makeText(EspeciesActivity.this, "Especie removido!", Toast.LENGTH_SHORT).show();
                                carregaLista();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(EspeciesActivity.this, "Especie não removido!", Toast.LENGTH_SHORT).show();
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

    public void carregaLista() {
        //final ListView especiesView = findViewById(R.id.lista_especies);

        final Call<List<Especie>> especies = new RetrofitInicializador().getEspecieService().listar();

        especies.enqueue(new Callback<List<Especie>>() {
            @Override
            public void onResponse(Call<List<Especie>> call, Response<List<Especie>> response) {
                List<Especie> especies = response.body();

                //EspeciesAdapter adapter = new EspeciesAdapter(EspeciesActivity.this, especies);
                ArrayAdapter<Especie> adapter = new ArrayAdapter<Especie> (EspeciesActivity.this, android.R.layout.simple_list_item_1, especies);
                especiesView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Especie>> call, Throwable t) {

            }
        });
    }
}
