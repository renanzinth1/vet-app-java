package br.com.renanjardel.vetappjava.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.renanjardel.vetappjava.activity.fragments.FragmentDadosCliente;
import br.com.renanjardel.vetappjava.activity.fragments.FragmentListaAnimais;

public class MyFragmentPageAdapter extends FragmentStatePagerAdapter {

    private String[] myTabTitles;

    public MyFragmentPageAdapter(FragmentManager fm, String[] myTabTitles) {
        super(fm);
        this.myTabTitles = myTabTitles;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new FragmentDadosCliente();

            case 1:
                return new FragmentListaAnimais();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.myTabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.myTabTitles[position];
    }
}
