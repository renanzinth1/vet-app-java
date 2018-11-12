package br.com.renanjardel.vetappjava.helper;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.Normalizer;
import java.util.List;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.form.FormAnimalActivity;
import br.com.renanjardel.vetappjava.model.Animal;
import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.model.Sexo;
import br.com.renanjardel.vetappjava.model.SexoAnimal;
import br.com.renanjardel.vetappjava.model.SubEspecie;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioAnimalHelper {

    private EditText campoNome;
    private EditText campoDataNascimento;
    private RadioGroup sexos;
    private RadioButton campoMacho;
    private RadioButton campoFemea;
    private Spinner spinnerEspecie;
    private Spinner spinnerSubEspecie;

    private List<Especie> especies;
    private List<SubEspecie> subespecies;

    private FormAnimalActivity activity;
    private Animal animal;

    public FormularioAnimalHelper(FormAnimalActivity activity) {
        campoNome = activity.findViewById(R.id.campo_animal_nome);
        campoDataNascimento = activity.findViewById(R.id.campo_animal_dataNascimento);
        sexos = activity.findViewById(R.id.radioGroup_sexos);
        campoMacho = activity.findViewById(R.id.campo_animal_macho);
        campoFemea = activity.findViewById(R.id.campo_animal_femea);
        spinnerEspecie = activity.findViewById(R.id.spinner_especies);
        spinnerSubEspecie = activity.findViewById(R.id.spinner_subEspecies);
        this.activity = activity;

        animal = new Animal();
    }


    public Animal pegaAnimal() {
        animal.setNome(campoNome.getText().toString());
        animal.setDataNascimento(campoDataNascimento.getText().toString());

        int checkedRadioButtonId = sexos.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.campo_animal_macho:
                animal.setSexo(SexoAnimal.MACHO);
                break;

            case R.id.campo_animal_femea:
                animal.setSexo(SexoAnimal.FEMEA);
                break;
        }

        animal.setSubEspecie((SubEspecie) spinnerSubEspecie.getSelectedItem());

        return animal;
    }

    public void campoTrue(boolean value) {
        campoNome.setEnabled(value);
        campoDataNascimento.setEnabled(value);
        campoMacho.setEnabled(value);
        campoFemea.setEnabled(value);
        spinnerEspecie.setEnabled(value);
        spinnerSubEspecie.setEnabled(value);
    }

    public void carregaSpinnerEspecieESubEspecie() {

        final Call<List<Especie>> especieCall = new RetrofitInicializador().getEspecieService().listar();

        especieCall.enqueue(new Callback<List<Especie>>() {
            @Override
            public void onResponse(Call<List<Especie>> call, Response<List<Especie>> response) {

                FormularioAnimalHelper.this.especies = response.body();

                ArrayAdapter<Especie> adapter = new ArrayAdapter<Especie> (activity, android.R.layout.simple_list_item_1, especies);
                spinnerEspecie.setAdapter(adapter);

                spinnerEspecie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        Especie especie = (Especie) parent.getItemAtPosition(position);

                        final Call<List<SubEspecie>> subEspecieCall = new RetrofitInicializador().getEspecieService().listarSubEspecies(especie.getCodigo());

                        subEspecieCall.enqueue(new Callback<List<SubEspecie>>() {
                            @Override
                            public void onResponse(Call<List<SubEspecie>> call, Response<List<SubEspecie>> response) {

                                FormularioAnimalHelper.this.subespecies = response.body();

                                final ArrayAdapter<SubEspecie> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, subespecies);
                                spinnerSubEspecie.setAdapter(adapter);

                                if(animal.getCodigo()!= null){
                                    spinnerSubEspecie.setSelection(pegarPosicaoSubEspecie(animal.getSubEspecie()));
                                }

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
            }

            @Override
            public void onFailure(Call<List<Especie>> call, Throwable t) {

            }
        });
    }

    public int pegarPosicaoEspecie(Especie especie){

        for(int i = 0; (i <= especies.size()-1); i++){
            if(especies.get(i).equals(especie)){
                return i;
            }
        }
        return 0;
    }

    public int pegarPosicaoSubEspecie(SubEspecie subEspecie){

        for(int i = 0; (i <= subespecies.size()-1); i++){
            if(subespecies.get(i).equals(subEspecie)){
                return i;
            }
        }
        return 0;
    }

    public void preencherFomulario(Animal animal) {
        Long codigo = animal.getCodigo();

        Call<Animal> retrofit = new RetrofitInicializador().getAnimalService().buscarPorId(codigo);

        retrofit.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                Animal animal = response.body();


                campoNome.setText(animal.getNome());
                campoDataNascimento.setText(animal.getDataNascimento());

                final SexoAnimal sexo = animal.getSexo();

                if(sexo == SexoAnimal.MACHO){
                    campoMacho.setChecked(true);
                    campoFemea.setChecked(false);
                } else {
                    campoFemea.setChecked(true);
                    campoMacho.setChecked(false);
                }

                spinnerEspecie.setSelection(pegarPosicaoEspecie(animal.getSubEspecie().getEspecie()));

                //Aqui estava dando bug, então mudei para dentro do carregaSpinnerEspecieESubEspecie()
                //spinnerSubEspecie.setSelection(pegarPosicaoSubEspecie(animal.getSubEspecie()));

                FormularioAnimalHelper.this.animal = animal;

                campoTrue(false);
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {

            }
        });
    }
}
