package br.com.renanjardel.vetappjava.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.renanjardel.vetappjava.R;

public class ClientesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        Button botaoNovoCliente = findViewById(R.id.novo_cliente);

        botaoNovoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent goCadastrarCliente = new Intent(ClientesActivity.this, FormClienteActivity.class);
                 startActivity(goCadastrarCliente);
            }
        });
    }
}
