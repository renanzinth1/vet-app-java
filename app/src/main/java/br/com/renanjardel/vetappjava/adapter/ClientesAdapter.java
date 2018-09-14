package br.com.renanjardel.vetappjava.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Arrays;
import java.util.List;

import br.com.renanjardel.vetappjava.model.Cliente;
import retrofit2.Call;

public class ClientesAdapter extends BaseAdapter {

    private final Context context;
    private final Call<List<Cliente>> listaClientes;

    public ClientesAdapter(Context context, Call<List<Cliente>> listaClientes) {
        this.context = context;
        this.listaClientes = listaClientes;
    }

    @Override
    public int getCount() {

        List<Call<List<Cliente>>> calls = Arrays.asList(listaClientes);

//        int num = 0;
//        for (Call<List<Cliente>> listCall : Arrays.asList(listaClientes)) {
//            num++;
//        }

        return calls.size();
    }

    @Override
    public Object getItem(int position) {
         List<Call<List<Cliente>>> calls = Arrays.asList(listaClientes);

        return calls.get(position);
    }

    @Override
    public long getItemId(int position) {
        final List<Call<List<Cliente>>> calls = Arrays.asList(listaClientes);

        //TODO Falta ver problema aqui
        //return calls.get(position);
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
