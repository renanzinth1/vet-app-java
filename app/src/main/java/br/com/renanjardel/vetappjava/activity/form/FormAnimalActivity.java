package br.com.renanjardel.vetappjava.activity.form;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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

    private FormularioAnimalHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_animal);

        helper = new FormularioAnimalHelper(this);
        Intent intent = getIntent();

        helper.carregaSpinnerEspecieESubEspecie();

        //Pegando o objeto cliente do formulário
        cliente = (Cliente) intent.getSerializableExtra("cliente");
        //Pegando o objeto animal da lista de animais ao selecionar
        animal = (Animal) intent.getSerializableExtra("animal");

        if(animal != null)
            helper.preencherFomulario(animal);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menu_formulario_remover:
                this.remover(animal);
                break;

            case R.id.menu_formulario_editar:
                helper.campoTrue(true);
                break;

            case R.id.menu_formulario_salvar:
                animal = helper.pegaAnimal();

                if (animal.getCodigo() != null) {

                    Call<Animal> editar = new RetrofitInicializador().getAnimalService().editar(animal.getCodigo(), animal);
                    editar.enqueue(new Callback<Animal>() {
                        @Override
                        public void onResponse(Call<Animal> call, Response<Animal> response) {
                            int resposta = response.code();

                            if(resposta == 202){
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormAnimalActivity.this, "Animal alterado!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormAnimalActivity.this, "Animal não alterado!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Animal> call, Throwable t) {

                        }
                    });

                } else {

                    animal.setCliente(cliente);
                    final Call<Void> salvarCall = new RetrofitInicializador().getAnimalService().salvar(animal);

                    salvarCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            int resposta = response.code();

                            if (resposta == 201) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormAnimalActivity.this, "Animal salvo!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormAnimalActivity.this, "Animal não salvo!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });

                }
        }

        return super.onOptionsItemSelected(item);
    }

    public void remover(final Animal animal) {
        new AlertDialog
                .Builder(FormAnimalActivity.this)
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
                                Toast.makeText(FormAnimalActivity.this, "Animal removido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormAnimalActivity.this, "Animal não removido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
