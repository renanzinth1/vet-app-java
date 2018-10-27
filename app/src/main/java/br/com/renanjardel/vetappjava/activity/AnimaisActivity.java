package br.com.renanjardel.vetappjava.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.renanjardel.vetappjava.R;
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

        animaisView = findViewById(R.id.lista_animal);

        registerForContextMenu(animaisView);

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
