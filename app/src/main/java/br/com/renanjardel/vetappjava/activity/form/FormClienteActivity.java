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
import android.widget.ImageButton;
import android.widget.Toast;

import br.com.renanjardel.vetappjava.R;

import br.com.renanjardel.vetappjava.activity.AnimaisActivity;
import br.com.renanjardel.vetappjava.helper.FormularioClienteHelper;
import br.com.renanjardel.vetappjava.model.Cliente;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormClienteActivity extends AppCompatActivity {

    private FormularioClienteHelper helper;

    private ImageButton btnListarAnimais;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cliente);

        helper = new FormularioClienteHelper(this);
        btnListarAnimais = findViewById(R.id.listar_animais);

        Intent intent = getIntent();
        cliente = (Cliente) intent.getSerializableExtra("cliente");

        if (cliente != null)
            helper.preencheFormulario(cliente);
        else
            btnListarAnimais.setVisibility(View.GONE);

        btnListarAnimais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormClienteActivity.this, AnimaisActivity.class);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_formulario, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);

        if (cliente == null) {
            menu.findItem(R.id.menu_formulario_remover).setVisible(false);
            menu.findItem(R.id.menu_formulario_editar).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_remover:
                this.remover(cliente);
                break;

            case R.id.menu_formulario_editar:
                helper.campoTrue(true);
                break;

            case R.id.menu_formulario_salvar:
                Cliente cliente = helper.pegaCliente();

                if (cliente.getCodigo() != null) {
                    final Call<Cliente> editar = new RetrofitInicializador().getClienteService().editar(cliente.getCodigo(), cliente);
                    editar.enqueue(new Callback<Cliente>() {
                        @Override
                        public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                            Log.i("onResponse", "Requisição feita com sucesso!");
                            Toast.makeText(FormClienteActivity.this, "Cliente alterado!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Cliente> call, Throwable t) {
                            Log.e("onFailure", "Requisão mal sucedida!");
                            Toast.makeText(FormClienteActivity.this, "Cliente não alterado!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                } else {
                    Call call = new RetrofitInicializador().getClienteService().salvar(cliente);

                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            int resposta = response.code();

                            if (resposta == 201) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormClienteActivity.this, "Cliente salvo!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormClienteActivity.this, "Cliente não salvo!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            //Log.e("onFailure", "Requisão mal sucedida!");
                            //Toast.makeText(FormClienteActivity.this, "Cliente não salvo!", Toast.LENGTH_SHORT).show();
                            //finish();
                        }
                    });
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void remover(final Cliente cliente) {
        new AlertDialog
                .Builder(FormClienteActivity.this)
                .setTitle("Excluir")
                .setIcon(R.drawable.ic_error_icon)
                .setMessage("Deseja excluir o cliente " + cliente.getNome() + " " + cliente.getSobrenome() + "?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Call<Void> removerCliente = new RetrofitInicializador().getClienteService().remover(cliente.getCodigo());
                        removerCliente.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Log.i("onResponse", "Requisição feita com sucesso!");
                                Toast.makeText(FormClienteActivity.this, "Cliente removido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("onFailure", "Requisão mal sucedida!");
                                Toast.makeText(FormClienteActivity.this, "Cliente não removido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
