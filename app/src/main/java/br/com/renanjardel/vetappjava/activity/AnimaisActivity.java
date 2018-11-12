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
import br.com.renanjardel.vetappjava.activity.form.FormAnimalActivity;
import br.com.renanjardel.vetappjava.model.Animal;
import br.com.renanjardel.vetappjava.model.Cliente;
import br.com.renanjardel.vetappjava.model.SubEspecie;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimaisActivity extends AppCompatActivity {

    private ListView animaisView;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animais);

        Intent intent = getIntent();
        cliente = (Cliente) intent.getSerializableExtra("cliente");

        carregaLista();

        Button botaoNovoAnimal = findViewById(R.id.novo_animal);

        botaoNovoAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goCadastrarAnimal = new Intent(AnimaisActivity.this, FormAnimalActivity.class);
                goCadastrarAnimal.putExtra("cliente", cliente);
                startActivity(goCadastrarAnimal);
            }
        });

        animaisView = findViewById(R.id.lista_animal);
        animaisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Animal animal = (Animal) parent.getItemAtPosition(position);
                final Intent formAnimal = new Intent(AnimaisActivity.this, FormAnimalActivity.class);
                formAnimal.putExtra("animal", animal);
                startActivity(formAnimal);
            }
        });

        registerForContextMenu(animaisView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Animal animal = (Animal) animaisView.getItemAtPosition(info.position);

        final MenuItem remover = menu.add("Remover");
        remover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                callExcludeAlertDialog(animal);
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void callExcludeAlertDialog(final Animal animal) {
        new AlertDialog
                .Builder(AnimaisActivity.this)
                .setTitle("Excluir")
                .setIcon(R.drawable.ic_error_icon)
                .setMessage("Deseja excluir o Animal " + animal.getNome() + "?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Call<Void> removerAnimal = new RetrofitInicializador().getAnimalService().remover(animal.getCodigo());
                        removerAnimal.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(AnimaisActivity.this, "Animal removido!", Toast.LENGTH_SHORT).show();
                                carregaLista();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(AnimaisActivity.this, "Animal não removido!", Toast.LENGTH_SHORT).show();
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

        final Call<List<Animal>> animais = new RetrofitInicializador().getClienteService().listarAnimais(cliente.getCodigo());

        animais.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                List<Animal> animais = response.body();

                //SubEspeciesAdapter adapter = new SubEspeciesAdapter(SubEspeciesActivity.this, subespecies);
                ArrayAdapter<Animal> adapter = new ArrayAdapter<Animal> (AnimaisActivity.this, android.R.layout.simple_list_item_1, animais);
                animaisView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {

            }
        });

    }
}
