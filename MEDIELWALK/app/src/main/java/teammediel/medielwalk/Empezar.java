package teammediel.medielwalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

public class Empezar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.empezar);

    }
    public void logear(View v){
        Intent intent= new Intent(this, Login.class);
        startActivity(intent);
    }

    public void registrar(View v){
        Intent intent2= new Intent(this, Registro.class);
        startActivity(intent2);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
