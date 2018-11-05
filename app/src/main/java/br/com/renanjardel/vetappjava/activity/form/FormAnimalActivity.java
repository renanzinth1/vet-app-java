package br.com.renanjardel.vetappjava.activity.form;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.List;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.AnimaisActivity;
import br.com.renanjardel.vetappjava.helper.FormularioAnimalHelper;
import br.com.renanjardel.vetappjava.model.Animal;
import br.com.renanjardel.vetappjava.model.Cliente;
import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.model.SubEspecie;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FormAnimalActivity extends AppCompatActivity {

    private Cliente cliente;
    private Animal animal;

    private Spinner spinnerEspecies;
    private Spinner spinnerSubEspecies;

    private long selectedItem;
    private FormularioAnimalHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_animal);

        Intent intent = getIntent();

        //Pegando o objeto cliente do formulário
        cliente = (Cliente) intent.getSerializableExtra("cliente");
        //Pegando o objeto animal da lista de animais ao selecionar
        animal = (Animal) intent.getSerializableExtra("animal");

//        if(animal != null)
//            helper.preencherFomulario(animal);

        carregaSpinnerEspecieESubEspecie();

        spinnerEspecies = findViewById(R.id.spinner_especies);
        spinnerSubEspecies = findViewById(R.id.spinner_subEspecies);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);

        if (animal == null) {
            menu.findItem(R.id.menu_formulario_remover).setVisible(false);
            menu.findItem(R.id.menu_formulario_editar).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public void carregaSpinnerEspecieESubEspecie() {

        final Call<List<Especie>> especieCall = new RetrofitInicializador().getEspecieService().listar();

        especieCall.enqueue(new Callback<List<Especie>>() {
            @Override
            public void onResponse(Call<List<Especie>> call, Response<List<Especie>> response) {

                List<Especie> especies = response.body();

                ArrayAdapter<Especie> adapter = new ArrayAdapter<Especie> (FormAnimalActivity.this, android.R.layout.simple_list_item_1, especies);
                spinnerEspecies.setAdapter(adapter);

                spinnerEspecies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        Especie especie = (Especie) parent.getItemAtPosition(position);

                        final Call<List<SubEspecie>> subEspecieCall = new RetrofitInicializador().getEspecieService().listarSubEspecies(especie.getCodigo());

                        subEspecieCall.enqueue(new Callback<List<SubEspecie>>() {
                            @Override
                            public void onResponse(Call<List<SubEspecie>> call, Response<List<SubEspecie>> response) {

                                final List<SubEspecie> subespecies = response.body();

                                final ArrayAdapter<SubEspecie> adapter = new ArrayAdapter<>(FormAnimalActivity.this, android.R.layout.simple_dropdown_item_1line, subespecies);
                                spinnerSubEspecies.setAdapter(adapter);
                            }

                            @Override
                            public void onFailure(Call<List<SubEspecie>> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

//                selectedItemId = spinnerEspecies.getSelectedItemPosition();

//                final Call<List<SubEspecie>> subEspecieCall = new RetrofitInicializador().getEspecieService().listarSubEspecies(selectedItemId);
//
//                subEspecieCall.enqueue(new Callback<List<SubEspecie>>() {
//                    @Override
//                    public void onResponse(Call<List<SubEspecie>> call, Response<List<SubEspecie>> response) {
//
//                        final List<SubEspecie> subEspecies = response.body();
//
//                        for(SubEspecie subEsp : subEspecies) {
//                            Log.i("TAG:", subEsp.getNome());
//                        }
////
////                        final ArrayAdapter<SubEspecie> adapter = new ArrayAdapter<>(FormAnimalActivity.this, android.R.layout.simple_dropdown_item_1line, subEspecies);
////                        spinnerSubEspecies.setAdapter(adapter);
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<SubEspecie>> call, Throwable t) {
//
//                    }
//                });
            }

            @Override
            public void onFailure(Call<List<Especie>> call, Throwable t) {

            }
        });

    }
}
