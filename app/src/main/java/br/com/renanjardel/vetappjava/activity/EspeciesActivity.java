package br.com.renanjardel.vetappjava.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.renanjardel.vetappjava.R;

public class EspeciesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especies);

        Button botaoNovaEspecie = findViewById(R.id.nova_especie);

        botaoNovaEspecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCadastrarEspecie = new Intent(EspeciesActivity.this, FormEspecieActivity.class);
                startActivity(goCadastrarEspecie);
            }
        });
    }
}
