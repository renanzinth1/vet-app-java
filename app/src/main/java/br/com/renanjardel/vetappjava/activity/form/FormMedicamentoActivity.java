package br.com.renanjardel.vetappjava.activity.form;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.renanjardel.vetappjava.R;

public class FormMedicamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_medicamento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_formulario_salvar:

                Toast.makeText(FormMedicamentoActivity.this, "Medicamento salvo!", Toast.LENGTH_SHORT).show();
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
