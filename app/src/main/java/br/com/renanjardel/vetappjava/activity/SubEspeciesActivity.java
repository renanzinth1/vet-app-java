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
import br.com.renanjardel.vetappjava.activity.form.FormSubEspecieActivity;
import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.model.SubEspecie;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubEspeciesActivity extends AppCompatActivity {

    private ListView subEspeciesView;
    private Especie especie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_especies);

        Intent intent = getIntent();
        especie = (Especie) intent.getSerializableExtra("especie");

        carregaLista();

        Button botaoNovaSubEspecie = findViewById(R.id.nova_subEspecie);

        botaoNovaSubEspecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCadastrarSubEspecie = new Intent(SubEspeciesActivity.this, FormSubEspecieActivity.class);

                //Passando objeto de especie para depois setar no subEspecie
                goCadastrarSubEspecie.putExtra("especie", especie);
                startActivity(goCadastrarSubEspecie);
            }
        });

        subEspeciesView = findViewById(R.id.lista_subEspecie);

        subEspeciesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View view, int position, long id) {
                SubEspecie subEspecie = (SubEspecie) lista.getItemAtPosition(position);
                Intent formEspecie = new Intent(SubEspeciesActivity.this, FormSubEspecieActivity.class);
                formEspecie.putExtra("subEspecie", subEspecie);
                startActivity(formEspecie);
            }
        });

        registerForContextMenu(subEspeciesView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final SubEspecie subEspecie = (SubEspecie) subEspeciesView.getItemAtPosition(info.position);

        final MenuItem remover = menu.add("Remover");
        remover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                callExcludeAlertDialog(subEspecie);
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void callExcludeAlertDialog(final SubEspecie subEspecie) {
        new AlertDialog
                .Builder(SubEspeciesActivity.this)
                .setTitle("Excluir")
                .setIcon(R.drawable.ic_error_icon)
                .setMessage("Deseja excluir a Subespecie " + subEspecie.getNome() + "?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Call<Void> removersubEspecie = new RetrofitInicializador().getSubEspecieService().remover(subEspecie.getCodigo());
                        removersubEspecie.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(SubEspeciesActivity.this, "Subespecie removido!", Toast.LENGTH_SHORT).show();
                                carregaLista();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(SubEspeciesActivity.this, "Subespecie não removido!", Toast.LENGTH_SHORT).show();
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

        final Call<List<SubEspecie>> subespecies = new RetrofitInicializador().getEspecieService().listarSubEspecies(especie.getCodigo());

        subespecies.enqueue(new Callback<List<SubEspecie>>() {
            @Override
            public void onResponse(Call<List<SubEspecie>> call, Response<List<SubEspecie>> response) {
                List<SubEspecie> subespecies = response.body();

                //SubEspeciesAdapter adapter = new SubEspeciesAdapter(SubEspeciesActivity.this, subespecies);
                ArrayAdapter<SubEspecie> adapter = new ArrayAdapter<SubEspecie> (SubEspeciesActivity.this, android.R.layout.simple_list_item_1, subespecies);
                subEspeciesView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<SubEspecie>> call, Throwable t) {

            }
        });
    }
}
