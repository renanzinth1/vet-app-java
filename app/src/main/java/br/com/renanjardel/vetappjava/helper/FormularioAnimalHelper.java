package br.com.renanjardel.vetappjava.helper;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.Normalizer;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.form.FormAnimalActivity;
import br.com.renanjardel.vetappjava.model.Animal;
import br.com.renanjardel.vetappjava.model.Especie;
import br.com.renanjardel.vetappjava.model.SexoAnimal;
import br.com.renanjardel.vetappjava.model.SubEspecie;

public class FormularioAnimalHelper {

    private EditText campoNome;
    private EditText campoDataNascimento;
    private RadioGroup sexos;
    private RadioButton campoMacho;
    private RadioButton campoFemea;
    //private Spinner spinnerEspecie;
    //private Spinner spinnerSubEspecie;

    private Animal animal;

    public FormularioAnimalHelper(FormAnimalActivity activity) {
        campoNome = activity.findViewById(R.id.campo_animal_nome);
        campoDataNascimento = activity.findViewById(R.id.campo_animal_dataNascimento);
        sexos = activity.findViewById(R.id.radioGroup_sexos);
        campoMacho = activity.findViewById(R.id.campo_animal_macho);
        campoFemea = activity.findViewById(R.id.campo_animal_femea);
        //spinnerEspecie = activity.findViewById(R.id.spinner_especies);
        //spinnerSubEspecie = activity.findViewById(R.id.spinner_subEspecies);

        animal = new Animal();
    }


    public Animal pegaAnimal() {
        animal.setNome(campoNome.getText().toString());
//        animal.setDataNascimento(campoDataNascimento.getText().toString());

        int checkedRadioButtonId = sexos.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case R.id.campo_animal_macho:
                animal.setSexo(SexoAnimal.MACHO);
                break;

            case R.id.campo_animal_femea:
                animal.setSexo(SexoAnimal.FEMEA);
                break;
        }

        //animal.setSubEspecie((SubEspecie) spinnerSubEspecie.getSelectedItem());

        return animal;
    }

    public void setSubEspecie(SubEspecie subEspecie) {
        this.animal.setSubEspecie(subEspecie);
    }

    public void campoTrue(boolean value) {
        campoNome.setEnabled(value);
        campoDataNascimento.setEnabled(value);
        campoMacho.setEnabled(value);
        campoFemea.setEnabled(value);
    }
}
