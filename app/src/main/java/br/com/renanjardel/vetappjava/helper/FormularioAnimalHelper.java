package br.com.renanjardel.vetappjava.helper;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.form.FormAnimalActivity;

public class FormularioAnimalHelper {

    private EditText campoNome;
    private EditText campoDataNascimento;
    private RadioGroup sexos;
    private RadioButton campoMacho;
    private RadioButton campoFemea;

    public FormularioAnimalHelper(FormAnimalActivity activity) {
        campoNome = activity.findViewById(R.id.campo_animal_nome);
        campoDataNascimento = activity.findViewById(R.id.campo_animal_dataNascimento);
        sexos = activity.findViewById(R.id.radioGroup_sexos);
        campoMacho = activity.findViewById(R.id.campo_animal_macho);
        campoFemea = activity.findViewById(R.id.campo_animal_femea);
    }
}
