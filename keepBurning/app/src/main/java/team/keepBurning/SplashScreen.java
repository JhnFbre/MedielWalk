package team.keepBurning;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = (ImageView) findViewById(R.id.logo);
        Animation anima = AnimationUtils.loadAnimation(this, R.anim.fadein);
        logo.startAnimation(anima);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(SplashScreen.this, Empezar.class);
                startActivity(intent);
            }
        },2500);
    }
}
