package br.com.renanjardel.vetappjava.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.renanjardel.vetappjava.R;
import br.com.renanjardel.vetappjava.activity.FormClienteActivity;

public class FragmentDadosCliente extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form_cliente, container, false);
    }
}
