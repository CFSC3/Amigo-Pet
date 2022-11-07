package com.ifam.amigopetfixo.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.ifam.amigopetfixo.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class Poste_EventoFragment extends Fragment {

    private TabItem postes;
    private TabItem eventos;
    private TabLayout escolha;
    private PosteFragment posteFragment;
    private EventosFragment eventosFragment;
    private int opcao = 0;
    private FrameLayout frameLayout;


    public Poste_EventoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_poste__evento, container, false);

        postes = view.findViewById(R.id.tabPoste);
        eventos = view.findViewById(R.id.tabEventos);
        escolha = view.findViewById(R.id.tabs);

            posteFragment = new PosteFragment();

            //Configurar o objeto para o fragment
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameConteudo, posteFragment);
            fragmentTransaction.commit();

            /*eventosFragment = new EventosFragment();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameConteudo, eventosFragment);
            fragmentTransaction.commit();
        }*/

        buttons2();

        return view;

    }

    public void buttons(){

        postes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posteFragment = new PosteFragment();

                //Configurar o objeto para o fragment
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameConteudo, posteFragment);
                fragmentTransaction.commit();
            }
        });

        eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventosFragment = new EventosFragment();
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameConteudo, eventosFragment);
                fragmentTransaction.commit();
            }
        });


    }

    public void buttons2(){
        escolha.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0: posteFragment = new PosteFragment();

                        //Configurar o objeto para o fragment
                        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frameConteudo, posteFragment);
                        fragmentTransaction.commit(); break;

                    case 1: eventosFragment = new EventosFragment();
                        FragmentTransaction fragmentTransaction2 = getChildFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.frameConteudo, eventosFragment);
                        fragmentTransaction2.commit(); break;

                    default:
                        Toast.makeText(getActivity(), "Não deu em nada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
