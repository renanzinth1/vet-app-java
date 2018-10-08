package br.com.renanjardel.vetappjava.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.form.FormMedicamentoActivity;

public class MedicamentosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        Button botaoNovoMedicamento = findViewById(R.id.novo_medicamento);

        botaoNovoMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCadastrarMedicamento = new Intent(MedicamentosActivity.this, FormMedicamentoActivity.class);
                startActivity(goCadastrarMedicamento);
            }
        });
    }
}
