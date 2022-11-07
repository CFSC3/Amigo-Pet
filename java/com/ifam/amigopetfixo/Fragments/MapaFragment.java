package com.ifam.amigopetfixo.Fragments;



import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifam.amigopetfixo.Permissoes.Permissoes;
import com.ifam.amigopetfixo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap maps;
    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private LocationManager locationManager;
    private LocationListener locationListener;
    private int opcao=3;


    public MapaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        //Validar permissões
        Permissoes.validarPermissoes(permissoes, getActivity(), 1);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapaFragment.this);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            opcao = bundle.getInt("dados");
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        maps = googleMap;

        //Objeto responsável por gerenciar a localização do usuário
        // locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Add a marker in Sydney and move the camera
        LatLng myLocation = new LatLng(-3.074376, -60.040186);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.setMyLocationEnabled(true);
            maps.getUiSettings().setMyLocationButtonEnabled(true);
            maps.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 12));}

        if (opcao==1) {
            PET_SHOPS_e_CLÍNICAS_VETERINARIAS();
        }else if(opcao==2){
            CASAS_DE_ACOLHIMENTO_E_ONGs();
        }else if(opcao==3){
            Todos();
        }

    }

    public void PET_SHOPS_e_CLÍNICAS_VETERINARIAS(){

         /*
        PET SHOPS e CLÍNICAS VETERINÁRIAS
         */

        //Litle Pet Shopping MUNDI
        LatLng location = new LatLng(-3.088031840837465, -59.99682756728039);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Little Pets: Shopping MUNDI - Avenida Efigênio Salles"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));

        }

        //Litle Pet Condomínio Morada do Sol

        location = new LatLng(-3.0933773984442987, -59.99629524867098);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Little Pets: Condomínio Morada do Sol"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));

        }

        //Pet Shop Bicho Solto
        location = new LatLng(-3.089904910229803, -59.98581569825006);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Bicho Solto: R. Santo Antônio, 148 - Coroado"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));

        }

        //Pettopia Clinica Veterinária e Petshop
        location = new LatLng(-3.088834990638149, -59.98595265076715);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pettopia Clinica Veterinária e Petshop: R. Santo Antônio - Coroado"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Pet Shop bicho capricho V8
        location = new LatLng(-3.088535019626981, -59.994793211631006);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop bicho capricho V8: Av. Efigênio Salles"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Gypsy Pet Shop
        location = new LatLng(-3.099319654935535, -59.98753994660877);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Gypsy Pet Shop: R. Guanabara - Petrópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Bichos.cão Pet Shop
        location = new LatLng(-3.089092108580265, -59.982519423247226);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Bichos.cão Pet Shop: R. Amazonas - Coroado"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Pet Shop Bicho Capricho
        location = new LatLng(-3.096390504317769, -60.00635677729676);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Bicho Capricho: R. Belo Horizonte - Adrianópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //LOSAnimais Pet Shop
        location = new LatLng(-3.094320159890765, -59.98200443911923);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("LOSAnimais Pet Shop: Av. Beira Mar - Coroado"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //American Pet Shop
        location = new LatLng(-3.089092108580265, -59.98161820102324);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("American Pet Shop: R. Santo Antônio - Coroado"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Mega Pet Store
        location = new LatLng(-3.0997781654815997, -60.00974053073676);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Mega Pet Store: Av. Jorn. Umberto Calderaro Filho - Adrianópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Clínica Veterinária 24 Horas Unipett
        location = new LatLng(-3.088106489464023, -59.987197195743136);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Clínica Veterinária 24 Horas Unipett: Av. Cosme Ferreira - Coroado"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Miss Pet
        location = new LatLng(-3.0901644447527983, -60.007588561424754);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Miss Pet: Av. Guilherme Paraense - Adrianópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Cobasi
        location = new LatLng(-3.1005576336312735, -59.99209213867276);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Cobasi: Petrópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Pet Shop Victor E Victória
        location = new LatLng(-3.1032552735397183, -59.99283441539275);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Victor E Victória: Petrópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Patas Pet Shop
        location = new LatLng(-3.1058816280997763, -60.018965976768754);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Patas Pet Shop: R. Rio Itannana, 708 - Nossa Sra. das Gracas"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Bicho Capricho Pet Boutique
        location = new LatLng(-3.0964371202533054, -60.00997763073675);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Bicho Capricho Pet Boutique: Av. Jorn. Umberto Calderaro Filho, 1169 - loja 04 - Adrianópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Bulbix Pet Shop
        location = new LatLng(-3.103311616479063, -60.01212056142476);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Bulbix Pet Shop: No mesmo piso da loja Dinâmica - Av. Mário Ypiranga, 1300 - Adrianópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Casa da ração Dr. pet
        location = new LatLng(-3.090420550289144, -59.98256233859122);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Casa da ração Dr. pet: Alameda Amazonas, 300 - Educandos"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Magia Canina Pet Shop
        location = new LatLng(-3.079814739904485, -60.00963576195277);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Magia Canina Pet Shop: Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Banho e Tosa / Dog Farma - Pet Shop
        location = new LatLng(-3.070621769013238, -59.93926155815803);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Banho e Tosa / Dog Farma - Pet Shop: Av. Cosme ferreira 7167 Zumbi dos Palmares "));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Zoopet
        location = new LatLng(-3.0874202982268986, -60.005866172044904);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Zoopet: Av. Guilherme Paraense, 5 - Adrianópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Belocão Pet Shop (Antiga Casa Doberman P10)
        location = new LatLng(-3.080999258560743, -60.014078127270416);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Belocão Pet Shop (Antiga Casa Doberman P10): Av. Ivonete Machado, 539 Ed. Eurico Neto - Lojas 2/3 - Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Clínica Veterinária Parque 10
        location = new LatLng(-3.0827921584661455, -60.01221764295682);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Clínica Veterinária Parque 10: R. 6, 224 - Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Centro de Estética Patinhas de Ouro
        location = new LatLng(-3.0797067207714695, -60.00655281754892);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Centro de Estética Patinhas de Ouro: Av. Maneca Marques, 772 - Sala 01 - Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Petzando PetShop
        location = new LatLng(-3.1001047259675114, -59.963294150797644);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Petzando PetShop: R. Alberto Carreira, 280 - Loja 4 - Distrito Industrial"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Cat & Dog Pet Shop e Consultório Veterinário
        location = new LatLng(-3.093933942241503, -60.01753914561274);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Cat & Dog Pet Shop e Consultório Veterinário: Rua Dr. Tomás, 12 - Adrianópolis, Manaus - AM"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Pet Elegance
        location = new LatLng(-3.080853944722015, -60.013685024283916);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Elegance: Avenida Ivanete Machado, 1037 - Antiga Av. Perimetral - Parque 10"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //1001pets
        location = new LatLng(-3.081521338920386, -60.01089006257879);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("1001pets: R. do Comercio, 838-A - Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Zoo Box Pet
        location = new LatLng(-3.0805823075229317, -60.01315912377657);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Zoo Box Pet: R. Dom Milton Correia Pereira, 578 - Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Casa da Ração Parque 10
        location = new LatLng(-3.081628717505235, -60.013605242328545);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Casa da Ração Parque 10: R. Perimetral I, 938 - Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Barao Pet Shop
        location = new LatLng(-3.0772989511233155, -59.99788860593381);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Barao Pet Shop: Rua Alexandre Magno, 1969 - Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Barony Pet Shop - Nova República
        location = new LatLng(-3.1045772401624925, -59.96272385491843);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Barony Pet Shop - Nova República: R. Alberto Carreira, 29 - Japiim"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Pet Shop Banho e Tosa
        location = new LatLng(-3.1093467933378345, -59.98363498890152);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Banho e Tosa: R. Polivalente, 10 - Japiim"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //L’amour De Lulu Pet Shop
        location = new LatLng(-3.1044199572767863, -60.02107865362559);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("L’amour De Lulu Pet Shop: R. Acre, n 1 - qd 37 - Nossa Sra. das Graças"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Suzi's Pet Shop - Consultório Veterinário
        location = new LatLng(-3.1079133698257535, -59.96706566289385);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Suzi's Pet Shop - Consultório Veterinário: Eliza Miranda Shopping Mall n, Av. Buriti, S/N - Eliza Miranda"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Shop da Ração
        location = new LatLng(-3.07790401468791, -60.01164031029608);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Shop da Ração: R. 32, 960 - Parque Dez de Novembros"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Pet Shop Red Nose
        location = new LatLng(-3.07863543772414, -59.97053961755262);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Red Nose: R. Marcelo Dias, 117 - Coroado"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Pet Shop Rock Dog
        location = new LatLng(-3.097198991060266, -60.023578507736964);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Rock Dog: Av. Djalma Batista - Chapada"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Dog Show Comércio de Alimentos para Animais
        location = new LatLng(-3.1074560299787652, -60.010779966508395);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Dog Show Comércio de Alimentos para Animais: R. Salvador, 445 - Adrianópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }



        //Pet Shop Skina Da Racao
        location = new LatLng(-3.0777882307202287, -59.96901726359852);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Skina Da Racao: Av. Efigênio Salles"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Amazon Vet
        location = new LatLng(-3.0770528622812066, -59.97518415012433);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Amazon Vet: R. dos Crisântemos, 214 - Aleixo, Manaus - AM"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Balaio de Pelos - Pet Shop e Clínica Veterinária Balaio de Pelos
        location = new LatLng(-3.109548730488723, -60.00810139945128);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Balaio de Pelos - Pet Shop e Clínica Veterinária Balaio de Pelos: R. Valério Botelho de Andrade, 245 - São Francisco"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Pet Lounge
        location = new LatLng(-3.078585657063555, -60.012273182420586);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Lounge: R. Perimetral I, 1247 - Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Pet Shop Victor e Victória
        location = new LatLng(-3.1094859241915302, -59.9837297262513);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Victor e Victória: Rua Polivalente Nº 2b - Japiim I"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //JS Aquários & Acessórios
        location = new LatLng(-3.0929510179621835, -59.98674103757888);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("JS Aquários & Acessórios: Rua Brasília número 125 A - Coroado"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Petland Vieiralves
        location = new LatLng(-3.1089390471850176, -60.01673590650325);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Petland Vieiralves: R. Pará, 505 - Nossa Sra. das Graças"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Pet Shop Patas Chick
        location = new LatLng(-3.1159870634326325, -60.00464327100459);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Patas Chick: R. Gen. Carneiro, 259 - São Francisco"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }



        //Spa do Pet
        location = new LatLng(-3.107766026929966, -60.0105906706317);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Spa do Pet: R. Salvador, 449 - loja 10 - Adrianópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Estimania Pet Shop
        location = new LatLng(-3.1131564607481614, -60.02319862720315);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Estimania Pet Shop: R. Pico das Águas, 195 - São Geraldo"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //The Dogs Pet Store
        location = new LatLng(-3.0762692877960953, -60.00930795908348);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("The Dogs Pet Store: Rua Álvaro Braga, 3 conjunto jardim amazonas - Parque 10 de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Meu Pet, Minha Vida
        location = new LatLng(-3.0758562990592493, -59.99911937526809);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Meu Pet, Minha Vida: Rua Alexandre Magno, 1005 - loja c - Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Monalinda's Pet
        location = new LatLng(-3.1132718144270606, -59.99110239628727);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Monalinda's Pet: R. Frederico Guilherme, 493 - Petrópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Bichomania Pet Shop
        location = new LatLng(-3.1128636382875374, -60.02346271375053);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Bichomania Pet Shop: Av. Djalma Batista, 482 - Chapada"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Casa Canário
        location = new LatLng(-3.112201770967521, -59.98124912009913);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Casa Canário: Av. Rodrigo Otávio, 4976 - Japiim"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Pet Place
        location = new LatLng(-3.1032515280709316, -60.02494223900975);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Place: Pátio Gourmet - Av. Djalma Batista, 1375 - São Geraldo"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Vip Pet
        location = new LatLng(-3.0756330399622023, -59.99840085518876);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Vip Pet: R. Rosalind Franklin, 3 - Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Madagascar Pet Center
        location = new LatLng(-3.0937088468527887, -60.02641635046973);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Madagascar Pet Center: Av. Constantino Nery, 2850 - Chapada"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Maskote Pet Shop & Clínica 24 Horas
        location = new LatLng(-3.0935293001343487, -60.0305058390576);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Maskote Pet Shop & Clínica 24 Horas: Av. Dr. Theomario Pinto da Costa, 2262 - Chapada"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //SL Aquários
        location = new LatLng(-3.0953253414829236, -60.00964740530519);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("SL Aquários: Av. Jorn. Umberto Calderaro Filho, 992 - Adrianópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Shop da Ração
        location = new LatLng(-3.0692719462853515, -60.00517367960947);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Shop da Ração: Av. Tancredo Neves, 01 - Parque 10 de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Casa Doberman
        location = new LatLng(-3.1155247918184847, -59.98257259960052);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Casa Doberman: Av. Rodrigo Otávio, 46 - Japiim II"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //PetShop Patrulha dos Pets
        location = new LatLng(-3.113287106550533, -59.98171072913588);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("PetShop Patrulha dos Pets: Av. Rodrigo Otávio, 4634 - Japiim"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Neide Pets
        location = new LatLng(-3.104502174462776, -59.99258791529308);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Neide Pets: Av. Paulo VI, 04 - Petrópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Charme Pet
        location = new LatLng(-3.073562747936924, -60.00155515078348);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Charme Pet: Av. Rua Alexandre Magno, 34 - sala 16 - Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Casa dos Animais - Petrópolis
        location = new LatLng(-3.117167350253257, -59.995966300137255);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Casa dos Animais - Petrópolis: R. Cel. Ferreira de Araújo, 741 - Petrópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Pet Shop Personal Vet
        location = new LatLng(-3.0701316193950823, -60.01168062745855);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Personal Vet: Rua Dallas, Numero 450 - Quadra 2 C - Flores"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Friends Web Petshop
        location = new LatLng(-3.093124482431468, -60.03548738834401);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Friends Web Petshop: Dr Yellow Mall - Av. Dr. Theomario Pinto da Costa, 680 - Chapada"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Fish e Cia
        location = new LatLng(-3.0942647347035153, -60.00932831327645);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Fish e Cia: Av. Jorn. Umberto Calderaro Filho, 2204-2208 - Adrianópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Pharmapet Manaus
        location = new LatLng(-3.116192531851937, -59.98266769455278);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pharmapet Manaus: Av penetrção 2 Conjunto 31 de março japiim II - Japiim"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Dog Model
        location = new LatLng(-3.1141693590535877, -60.021420623794036);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Dog Model: R. Belém - Adrianópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Gatil Fofucats
        location = new LatLng(-3.074894112771083, -60.00786993416692);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Gatil Fofucats: Av. Tancredo Neves, 31 - Parque 10 de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Toy Pet Shop
        location = new LatLng(-3.1208678842769495, -59.99196357818587);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Toy Pet Shop: R. Cunha Melo, N° 20 - Petrópolis"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Consultório Veterinário e Pet Shop Reino Animal
        location = new LatLng(-3.0737478556735383, -59.949518019872244);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Consultório Veterinário e Pet Shop Reino Animal: R. Rosarinho, 81 - São José Operário"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Pet Shop Bicho Sapeca
        location = new LatLng(-3.071825309360445, -59.94634856992167);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Bicho Sapeca: Av. Cosme Ferreira, 5884 - Zumbi dos Palmares"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Loja Pet+
        location = new LatLng(-3.0621178146465384, -60.00104588016765);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Loja Pet+: Av. Tancredo Neves, 2077 - Flores"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Pet Shop Amazon Dog
        location = new LatLng(-3.0736452245047934, -60.03579850838439);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Amazon Dog: Av. Des. João Machado, 850 - Alvorada"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //AnjoDon Pet Care
        location = new LatLng(-3.072919086729009, -60.00237346510862);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("AnjoDon Pet Care: Rua Alexandre Magno, 665 - Parque 10 de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Dr Vet Pets Consultório Veterinário e Pet Shop
        location = new LatLng(-3.067024248351743, -59.99976767663068);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Dr Vet Pets Consultório Veterinário e Pet Shop: Rua Virginia Wolf, 15, Conj. Shangrilá - Parque 10 de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Leão Pet Shop
        location = new LatLng(-3.1230082868372127, -60.00617278195633);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Leão Pet Shop: Entre Drogaria Rios e Panificadora Cíntia - Av. Carvalho Leal, 1351 - Cachoeirinha"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Mc Dog Pet Shop e Clínica Veterinária
        location = new LatLng(-3.0675753287891068, -59.95254820566324);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Mc Dog Pet Shop e Clínica Veterinária: Av. Autaz Mirim, 5566 - Térreo - São José Operário"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Petnik Petshop
        location = new LatLng(-3.121635221637512, -60.01171262835846);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Petnik Petshop: Av. Duque de Caxias, 1552 - Praça 14 de Janeiro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Pet Shop É o Bicho
        location = new LatLng(-3.0602262731427823, -59.99259259753515);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop É o Bicho: Av. Prof. Nilton Lins, 4423 - Flores"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Pet +
        location = new LatLng(-3.0620832047505284, -60.001077594534415);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet +: Av. Tancredo Neves, 2077 - Parque Dez de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Pet House & Cia
        location = new LatLng(-3.123660696464827, -60.00777979375177);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet House & Cia: Av. Castelo Branco, 1016 - Cachoeirinha"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Minas Pet Shop
        location = new LatLng(-3.0558207626851166, -59.99408346797797);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Minas Pet Shop: R. Marquês de Erval, nº 547 - Flores"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Au Miau Pet Shop
        location = new LatLng(-3.0710852950585847, -59.94538935995049);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Au Miau Pet Shop: Alameda Cosme Ferreira, 6073 - São José"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Mundo Animal
        location = new LatLng(-3.129644458725474, -60.02573794207646);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Mundo Animal: Rua 10 de Julho, 203 - Centro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Pet Shop Osso Meu Hotelzinho
        location = new LatLng(-3.1079277367525475, -60.040597207868935);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Osso Meu Hotelzinho: R. Maria Cavalcante, 5 - São Jorge"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //JJ Aquarismo Manaus
        location = new LatLng(-3.0787141463327443, -60.02644911946109);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("JJ Aquarismo Manaus: Av. Constantino Nery, 2500 - Flores"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Feras Pet Shop - Cachoeirinha
        location = new LatLng(-3.1305497028392835, -59.99062896600314);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Feras Pet Shop - Cachoeirinha:Av. Carvalho Leal - Cachoeirinha"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Latidu's Dog
        location = new LatLng(-3.130396887797309, -60.01336432161337);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Latidu's Dog: R. Visc. de Porto Alegre, 666 - Praça 14 de Janeiro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Aubig pet shop
        location = new LatLng(-3.056637427193096, -59.96282157508963);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Aubig pet shop: R. João Câmara - Novo Aleixo"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //L A dos Santos Oliveira
        location = new LatLng(-3.121633335532416, -60.01169931885877);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("L A dos Santos Oliveira: Av. Japurá, 1623 - Praça 14 de Janeiro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Pet Amigo Pet Shop
        location = new LatLng(-3.058263689118712, -60.032268622987374);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Amigo Pet Shop: Rua Boa Esperança, 27 - Da Paz"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        //Pet Shop Bicho Legal
        location = new LatLng(-3.1247167058507226, -60.007769611837716);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Bicho Legal: Av. Castelo Branco, 1299 - Sala 5 - Cachoeirinha"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Planeta Animal
        location = new LatLng(-3.1077695966630943, -60.04113954683141);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Planeta Animal: R. Santa Helena, 722 - São Jorge"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Pet Lar
        location = new LatLng(-3.0637983218951663, -59.99613248484541);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Lar: R. Mozart Guarnieri, 958 - Parque 10 de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Pet Sam
        location = new LatLng(-3.1324211046448562, -60.01212809349526);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Sam: Av. Duque de Caxias, 419 - Centro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Clínica Veterinária Arca de Noé
        location = new LatLng(-3.0882972666128023, -60.024422199961734);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Clínica Veterinária Arca de Noé: Av. Djalma Batista, 455 - Chapada"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Cazagro Pet Shop
        location = new LatLng(-3.1396436268162953, -60.02366767404166);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Cazagro Pet Shop: R. dos Barés, Nº 52 - Box 10 - Centro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Hiper Pet
        location = new LatLng(-3.0913255993564808, -60.038245213149544);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Hiper Pet: R. Paxiúbas - Dom Pedro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Vira-Lata e Cia
        location = new LatLng(-3.057377695676985, -60.05150205919346);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Vira-Lata e Cia: R. Santo Antônio, 259 - Coroado II"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Pet Shop Ponta Negra
        location = new LatLng(-3.0681647762027975, -60.09470968076391);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Pet Shop Ponta Negra: Av. Coronel Teixeira, 7581 - Ponta Negra"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Patinhas de Anjos
        location = new LatLng(-3.1078649006683414, -60.057258878414224);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Patinhas de Anjos: Av. Oscar Borel, 92 - Compensa"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Casa dos Cães
        location = new LatLng(-3.107069853520409, -60.053198750364665);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Casa dos Cães: Avenida São Pedro 20"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

        //Santo Mascote Pet Shop
        location = new LatLng(-3.0784910459932626, -60.04861839606977);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Santo Mascote Pet Shop: R. Prof. Abílio Alencar, 332 - Alvorada"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }

    }

    public void CASAS_DE_ACOLHIMENTO_E_ONGs(){
        /*
        CASAS DE ACOLHIMENTO E ONGs
         */

        LatLng location = new LatLng(-3.088031840837465, -59.99682756728039);

        location = new LatLng(-3.1007139478583525, -59.99958841043759);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Ong Anjos de Rua Manaus: Tv. Ouroeste - Aleixo"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));

        }

        //Casa dos Animais
        location = new LatLng(-3.1171649914054753, -59.99595959374723);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Casa dos Animais: R. Cel. Ferreira de Araújo - Petrópoli"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));

        }

        //Quatro Patas- Centro de Beleza Animal (Manaus)
        location = new LatLng(-3.06838579292115, -60.0005054307855);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Quatro Patas- Centro de Beleza Animal (Manaus): Av. Amazonas Cavalcante - Parque 10 de Novembro"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));

        }

        //Centro de Controle de Zoonoses de Manaus
        location = new LatLng(-3.1080302524504657, -60.0537987758877);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            maps.addMarker(new MarkerOptions().position(location).title("Centro de Controle de Zoonoses de Manaus: Av. Brasil - Compensa"));
            maps.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }

    public void Todos(){
        PET_SHOPS_e_CLÍNICAS_VETERINARIAS();
        CASAS_DE_ACOLHIMENTO_E_ONGs();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResultado : grantResults) {

            //permission denied (negada)
            if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                //Alerta
                alertaValidacaoPermissao();
            } else if (permissaoResultado == PackageManager.PERMISSION_GRANTED) {
                //Recuperar localizacao do usuario

                /*
                 * 1) Provedor da localização
                 * 2) Tempo mínimo entre atualizacões de localização (milesegundos)
                 * 3) Distancia mínima entre atualizacões de localização (metros)
                 * 4) Location listener (para recebermos as atualizações)
                 * */
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0,
                            0,
                            locationListener
                    );
                }

            }
        }

    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
