package br.com.renanjardel.vetappjava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.model.Veterinario;

public class VeterinariosAdapter extends BaseAdapter {

    private final Context context;
    private final List<Veterinario> listaVeterinarios;

    public VeterinariosAdapter(Context context, List<Veterinario> listaVeterinarios) {
        this.context = context;
        this.listaVeterinarios = listaVeterinarios;
    }

    @Override
    public int getCount() {
        return listaVeterinarios.size();
    }

    @Override
    public Object getItem(int position) {
        return listaVeterinarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaVeterinarios.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Veterinario veterinario = listaVeterinarios.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.veterinario_list_item, parent, false);
        }

        TextView campoNome = view.findViewById(R.id.veterinario_item_nome);
        TextView campoSobrenome = view.findViewById(R.id.veterinario_item_sobrenome);
        TextView campoCfmv = view.findViewById(R.id.veterinario_item_cfmv);

        //if (campoNome != null)
        campoNome.setText(veterinario.getNome());

        //if (campoSobrenome != null)
        campoSobrenome.setText(veterinario.getSobrenome());

        //if (campoCpf != null)
        campoCfmv.setText(veterinario.getCfmv());

        return view;
    }
}