package team.keepBurning;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class CaloriesFragment extends Fragment implements View.OnClickListener{
    public Comidas arreglo []= new Comidas[16];
    View view;
    Button soda,icrecream,chocolate,coffe,pizza,hamurguer,hotdog,popcorn,cakeslice,cupcake,bread,donut,chicken,sandwich,
    snack,french;

    Dialog myDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.calories, viewGroup, false);
       llenarArray();
        soda = (Button) view.findViewById(R.id.soda);
        icrecream = (Button) view.findViewById(R.id.icream);
        chocolate = (Button) view.findViewById(R.id.chocolate);
        coffe = (Button) view.findViewById(R.id.coffe);
        pizza= (Button) view.findViewById(R.id.pizza);
        hamurguer = (Button) view.findViewById(R.id.hamburger);
        hotdog = (Button) view.findViewById(R.id.hotdog);
        popcorn = (Button) view.findViewById(R.id.palomitas);
        cakeslice = (Button) view.findViewById(R.id.cakeslice);
        cupcake = (Button) view.findViewById(R.id.cupcake);
        bread = (Button) view.findViewById(R.id.panBlanco);
        donut = (Button) view.findViewById(R.id.dona);
        chicken = (Button) view.findViewById(R.id.chicken);
        sandwich = (Button) view.findViewById(R.id.sandwich);
        snack = (Button) view.findViewById(R.id.chips);
        french = (Button) view.findViewById(R.id.french);
        soda.setOnClickListener(this);
        icrecream.setOnClickListener(this);
        chocolate.setOnClickListener(this);
        coffe.setOnClickListener(this);
        pizza.setOnClickListener(this);
       hamurguer.setOnClickListener(this);
        hotdog.setOnClickListener(this);
        popcorn.setOnClickListener(this);
        cakeslice.setOnClickListener(this);
        cupcake.setOnClickListener(this);
        bread.setOnClickListener(this);
        donut.setOnClickListener(this);
        chicken.setOnClickListener(this);
        sandwich.setOnClickListener(this);
        snack.setOnClickListener(this);
        french.setOnClickListener(this);

        myDialog = new Dialog(getActivity());


        return view;

    }
      public void llenarArray(){
          arreglo[0] = new Comidas("icream",207,200);
          arreglo[1] = new Comidas("chocolate",556,200);
          arreglo[2] = new Comidas("coffe",110,200);
          arreglo[3] = new Comidas("pizza",255,100);
          arreglo[4] = new Comidas("hamburger",295,100);
          arreglo[5] = new Comidas("palomitas",375,100);
          arreglo[6] = new Comidas("hotdog",290,100);
          arreglo[7] = new Comidas("soda",355,355);
          arreglo[8] = new Comidas("cakeslices",257,100);
          arreglo[9] = new Comidas("cupcake",305,100);
          arreglo[10] = new Comidas("panBlanco",265,100);
          arreglo[11] = new Comidas("dona",417,100);
          arreglo[12] = new Comidas("chicken",246,100);
          arreglo[13] = new Comidas("sandwich",233,100);
          arreglo[14] = new Comidas("chips",228,45);
          arreglo[15] = new Comidas("french.",312,100);

         }



    @Override
    public void onClick(View v) {
        String nam="",calor="",por="";
        Integer c,p;
        Button temp;
        temp = (Button) v.findViewById(v.getId());
        String namet= getResources().getResourceEntryName(v.getId());
       // Toast.makeText(getActivity(),namet,Toast.LENGTH_LONG).show();
        for (Comidas s : arreglo)
           if(namet.equals(s.nombre)){
            nam=s.nombre;
            c=s.calorias;
            p=s.porcion;
            calor=String.valueOf(c);
            por=String.valueOf(p);
               ShowPopup(v,nam,calor,por);
           }

    }




    public void ShowPopup(View v,String nameS, String caloriesS, String porcionS) {
        TextView txtclose;
        TextView name,calories,porcion;

        Button btnFollow;
        myDialog.setContentView(R.layout.popup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        porcion =(TextView) myDialog.findViewById(R.id.porci);
        calories =(TextView) myDialog.findViewById(R.id.calo);
        name =(TextView) myDialog.findViewById(R.id.nom);
        txtclose.setText("X");
        name.setText(nameS);
        calories.setText(caloriesS);
        porcion.setText(porcionS);
        btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
