package br.com.renanjardel.vetappjava.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import br.com.renanjardel.vetappjava.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton botaoCliente = findViewById(R.id.botao_listar_cliente);
        ImageButton botaoVeterinario = findViewById(R.id.botao_listar_veterinario);
        ImageButton botaoMedicamento = findViewById(R.id.botao_listar_medicamento);
        ImageButton botaoEspecie = findViewById(R.id.botao_listar_especie);

        botaoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clienteIntent = new Intent(MainActivity.this, ClientesActivity.class);
                startActivity(clienteIntent);
            }
        });

        botaoVeterinario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent veterinarioIntent = new Intent(MainActivity.this, VeterinariosActivity.class);
                startActivity(veterinarioIntent);
            }
        });

        botaoMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent medicamentoIntent = new Intent(MainActivity.this, MedicamentosActivity.class);
                startActivity(medicamentoIntent);
            }
        });

        botaoEspecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent especieIntent = new Intent(MainActivity.this, EspeciesActivity.class);
                startActivity(especieIntent);
            }
        });

    }
}