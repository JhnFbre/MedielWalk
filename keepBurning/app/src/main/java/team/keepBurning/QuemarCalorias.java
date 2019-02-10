package team.keepBurning;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuemarCalorias extends Fragment {
    String nomcomida="";
    int cancalorias=0;
    TextView tiempo, comida, calorias, pasos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_quemar_calorias, viewGroup, false);
        //cargarPreferencias();
        /*
        tiempo = (TextView) getActivity().findViewById(R.id.tvtiempo);
        comida = (TextView) getActivity().findViewById(R.id.tvnomcomida);
        calorias = (TextView) getActivity().findViewById(R.id.tvcalorias);
        pasos  = (TextView) getActivity().findViewById(R.id.tvpasos);

        comida.setText(nomcomida);
        calorias.setText(cancalorias);*/
        return view;
    }
}
