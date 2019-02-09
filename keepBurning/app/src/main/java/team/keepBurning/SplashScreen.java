package team.keepBurning;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends Activity {
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);

            logo = (ImageView) findViewById(R.id.logo);
            Animation anima = AnimationUtils.loadAnimation(this, R.anim.fadein);
            logo.startAnimation(anima);
        printKeyHash();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   validar();
                    Log.d("despues validar","ouch");
                }
            },2500);



        }

        public void validar(){
            String sesioniciada=getFromSharedPreferences(this,"id");
                    /*Intent intent= new Intent(SplashScreen.this, Empezar.class);

                    startActivity(intent);*/
            Log.d("sesion iniciada",sesioniciada);
            if (sesioniciada!=""){
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }else{
                Intent intent  = new Intent(SplashScreen.this, Login.class);
                startActivity(intent );
            }
            finish();
        }
    private void printKeyHash(){

    }
    public static String getFromSharedPreferences(Context context,String usuario) {

        SharedPreferences preferences = context.getSharedPreferences("login_preference", MODE_PRIVATE);
        return preferences.getString(usuario, "");

    }







}
