package br.com.renanjardel.vetappjava.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.renanjardel.vetappjava.R;

public class VeterinariosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinarios);

        Button botaoNovoVeterinario = findViewById(R.id.novo_veterinario);

        botaoNovoVeterinario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCadastrarVeterinario = new Intent(VeterinariosActivity.this, FormVeterinarioActivity.class);
                startActivity(goCadastrarVeterinario);
            }
        });
    }
}
