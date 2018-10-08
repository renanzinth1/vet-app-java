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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.form.FormVeterinarioActivity;
import br.com.renanjardel.vetappjava.adapter.VeterinariosAdapter;
import br.com.renanjardel.vetappjava.model.Veterinario;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VeterinariosActivity extends AppCompatActivity {

    private ListView veterinariosView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinarios);

        Button botaoNovoVeterinario = findViewById(R.id.novo_veterinario);

        botaoNovoVeterinario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCadastrarVeterinario = new Intent(VeterinariosActivity.this, FormVeterinarioActivity.class);
                startActivity(goCadastrarVeterinario);
            }
        });

        veterinariosView = findViewById(R.id.lista_veterinario);

        veterinariosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View view, int position, long l) {
                Veterinario veterinario = (Veterinario) lista.getItemAtPosition(position);
                Intent intent = new Intent(VeterinariosActivity.this, FormVeterinarioActivity.class);
                intent.putExtra("veterinario", veterinario);
                startActivity(intent);
            }
        });

        registerForContextMenu(veterinariosView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Veterinario veterinario = (Veterinario) veterinariosView.getItemAtPosition(info.position);

        final MenuItem remover = menu.add("Remover");
        remover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                callExcludeAlertDialog(veterinario);
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void callExcludeAlertDialog(final Veterinario veterinario) {
        new AlertDialog
                .Builder(VeterinariosActivity.this)
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
                                Toast.makeText(VeterinariosActivity.this, "Veterinario removido!", Toast.LENGTH_SHORT).show();
                                carregaLista();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(VeterinariosActivity.this, "Veterinario não removido!", Toast.LENGTH_SHORT).show();
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


        final Call<List<Veterinario>> veterinarios = new RetrofitInicializador().getVeterinarioService().listar();

        veterinarios.enqueue(new Callback<List<Veterinario>>() {
            @Override
            public void onResponse(Call<List<Veterinario>> call, Response<List<Veterinario>> response) {
                List<Veterinario> veterinarios = response.body();

                VeterinariosAdapter adapter = new VeterinariosAdapter(VeterinariosActivity.this, veterinarios);
                //ArrayAdapter<Veterinario> adapter = new ArrayAdapter<Veterinario> (VeterinariosActivity.this, android.R.layout.simple_list_item_1, Veterinarios);
                veterinariosView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Veterinario>> call, Throwable t) {

            }
        });
    }
}
