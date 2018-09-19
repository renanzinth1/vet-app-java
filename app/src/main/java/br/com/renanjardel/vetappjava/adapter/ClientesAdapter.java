package br.com.renanjardel.vetappjava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.model.Cliente;

public class ClientesAdapter extends BaseAdapter {

    private final Context context;
    private final List<Cliente> listaClientes;

    public ClientesAdapter(Context context, List<Cliente> listaClientes) {
        this.context = context;
        this.listaClientes = listaClientes;
    }

    @Override
    public int getCount() {
        return listaClientes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaClientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaClientes.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Cliente cliente = listaClientes.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.cliente_list_item, parent, false);
        }

        TextView campoNome = view.findViewById(R.id.cliente_item_nome);
        TextView campoSobrenome = view.findViewById(R.id.cliente_item_sobrenome);
        TextView campoCpf = view.findViewById(R.id.cliente_item_cpf);
        //campoCpf.addTextChangedListener(MaskEditUtil.mask((EditText) campoCpf, MaskEditUtil.FORMAT_CPF));

        //if (campoNome != null)
        campoNome.setText(cliente.getNome());

        //if (campoSobrenome != null)
        campoSobrenome.setText(cliente.getSobrenome());

        //if (campoCpf != null)
        campoCpf.setText(cliente.getCpfFormatado());

        return view;
    }
}
