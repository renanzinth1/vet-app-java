package br.com.renanjardel.vetappjava.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.adapter.MyFragmentPageAdapter;
import br.com.renanjardel.vetappjava.helper.FormularioClienteHelper;
import br.com.renanjardel.vetappjava.model.Cliente;
import br.com.renanjardel.vetappjava.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteAnimaisActivity extends FormClienteActivity {

    //final FormClienteActivity formClient = new FormClienteActivity();

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_animais);

        FormularioClienteHelper helper = new FormularioClienteHelper(this);

        Intent intent = getIntent();
        Cliente cliente = (Cliente) intent.getSerializableExtra("cliente");

        if (cliente != null)
            helper.preencheFormulario(cliente);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

        mViewPager.setAdapter(new MyFragmentPageAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.title_tab)));

        mTabLayout.setupWithViewPager(mViewPager);
    }
}
