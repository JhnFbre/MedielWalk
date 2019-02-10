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
    public Comidas arreglo []= new Comidas[28];
    View view;
    Button soda,icrecream,chocolate,coffe,pizza,hamurguer,hotdog,popcorn,cakeslice,cupcake,bread,donut,chicken,sandwich,
    snack,french,shawarma,bolonq,bolonc,bolonmx,patacon,empanada,pancake,taco,salchipapa,papipollo,arroz,galletas;

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
        shawarma= (Button) view.findViewById(R.id.shawarma);
        bolonc= (Button) view.findViewById(R.id.bolonch);
        bolonq = (Button) view.findViewById(R.id.bolonq);
        bolonmx= (Button) view.findViewById(R.id.bolonmx);
        patacon= (Button) view.findViewById(R.id.patacon);
        empanada= (Button) view.findViewById(R.id.empanada);
        pancake= (Button) view.findViewById(R.id.pancake);
        taco= (Button) view.findViewById(R.id.taco);
        salchipapa= (Button) view.findViewById(R.id.salchipapa);
        papipollo= (Button) view.findViewById(R.id.papipollo);
        arroz= (Button) view.findViewById(R.id.rice);
        galletas= (Button) view.findViewById(R.id.galletas);
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
         shawarma.setOnClickListener(this);
         bolonq.setOnClickListener(this);
         bolonc.setOnClickListener(this);
         bolonmx.setOnClickListener(this);
         patacon.setOnClickListener(this);
         empanada.setOnClickListener(this);
         pancake.setOnClickListener(this);
         taco.setOnClickListener(this);
         salchipapa.setOnClickListener(this);
         papipollo.setOnClickListener(this);
         arroz.setOnClickListener(this);
         galletas.setOnClickListener(this);

        myDialog = new Dialog(getActivity());



        return view;

    }
      public void llenarArray(){
          arreglo[0] = new Comidas("icream",207,200,"Helado");
          arreglo[1] = new Comidas("chocolate",556,200,"1 Barra de chocolate");
          arreglo[2] = new Comidas("coffe",110,200,"1 Taza de café con Leche");
          arreglo[3] = new Comidas("pizza",255,100,"1 Rebanada de Pizza");
          arreglo[4] = new Comidas("hamburger",295,100,"1 Hamburguesa");
          arreglo[5] = new Comidas("palomitas",375,100,"1 Porción de canguil");
          arreglo[6] = new Comidas("hotdog",290,100,"1 Hot Dog");
          arreglo[7] = new Comidas("soda",355,355,"1 Soda tradicional");
          arreglo[8] = new Comidas("cakeslices",257,100,"1 Rebanada de Pastel");
          arreglo[9] = new Comidas("cupcake",305,100,"1 Cupcake");
          arreglo[10] = new Comidas("panBlanco",265,100,"1 Pan Blanco");
          arreglo[11] = new Comidas("dona",417,100,"1 Dona");
          arreglo[12] = new Comidas("chicken",246,100,"1 Presa de pollo fito");
          arreglo[13] = new Comidas("sandwich",233,100,"1 Sandwich de queso y huevo");
          arreglo[14] = new Comidas("chips",228,45, "1 Bolsa de Papitas");
          arreglo[15] = new Comidas("french.",312,100,"1 Porción de papas");
          arreglo[16] = new Comidas("shawarma.",150,100, "1 Shawarma");
          arreglo[17] = new Comidas("bolonq",250,100, "1 Bolon de queso");
          arreglo[18] = new Comidas("bolonch",300,150, "1 Bolon de Chicharron");
          arreglo[19] = new Comidas("bolonmx",400,100, "1 Bolon de Mixto");
          arreglo[20] = new Comidas("patacon",210,100, "1 Porción de Patacones");
          arreglo[21] = new Comidas("empanada",160,100, "1 Empanada");
          arreglo[22] = new Comidas("pancake",400,3, "3 Pancakes");
          arreglo[23] = new Comidas("taco",137,100, "1 Taco");
          arreglo[24] = new Comidas("papipollo",484,200, "1 Papipollo");
          //https://www.myfitnesspal.com/es/food/search?page=1&search=salchipapa
          arreglo[25] = new Comidas("salchipapa",974,400, "1 Salchipapa");
          arreglo[26] = new Comidas("rice",442,200, "1 taza de arroz");
          arreglo[27] = new Comidas("galleta",136,28, "Galleta");

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
            nam=s.etiqueta;
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
