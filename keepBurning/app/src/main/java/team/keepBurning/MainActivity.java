package team.keepBurning;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private android.support.v7.widget.Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView nv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer);
        nv= findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MapaFragment()).commit();
            nv.setCheckedItem(R.id.op_juego);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.op_juego:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MapaFragment()).commit();
                break;
            case R.id.op_perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();
                break;

            case R.id.op_jugadores:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ComidasFavoritas()).commit();

                break;
            case R.id.op_salir:
                Intent intent= new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
