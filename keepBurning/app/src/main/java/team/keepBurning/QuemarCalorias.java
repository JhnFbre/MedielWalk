package team.keepBurning;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class QuemarCalorias extends Activity implements SensorEventListener, OnMapReadyCallback {
    SensorManager sensorManager;
    Sensor sensor;
    CountDownTimer countDownTimer;
    String nomcomida, pasitos;
    int cancalorias, contadorpasos;
    private long tiempomili;
    boolean running = false;
    Button cancelar;
    EditText tiempo, comida, calorias, pasos;
    private GoogleMap mMap;
    Marker marcador;
    double lat= 0;
    double lng= 0;

    public long getTiempomili() {
        return tiempomili;
    }

    public void setTiempomili(long tiempomili) {
        this.tiempomili = tiempomili;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quemar_calorias);
        contadorpasos = 0;
        nomcomida= "";
        cancalorias = 0;
        cargarPreferencias();
        this.setTiempomili((cancalorias/2)*60000);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        iniciarTiempo();
        tiempo = (EditText) findViewById(R.id.tvtiempo);
            tiempo.setEnabled(false);
        comida = (EditText) findViewById(R.id.tvnomcomida);
            comida.setEnabled(false);
        calorias = (EditText) findViewById(R.id.tvcalorias);
            calorias.setEnabled(false);
        pasos  = (EditText) findViewById(R.id.tvpasos);
            pasos.setEnabled(false);
        cancelar = (Button) findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Toast toast = Toast.makeText(this, "Quemar calorías!"+pasitos, Toast.LENGTH_SHORT);
                toast.show();
        tiempo.setText(""+tiempomili);
        comida.setText(nomcomida);
        calorias.setText(""+cancalorias);

    }

    private void cargarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("comida", Context.MODE_PRIVATE);
        nomcomida = preferences.getString("nombrecomida", "Seleccione una comida antes");
        pasitos = preferences.getString("pasitos", "0");
        cancalorias = preferences.getInt("cantidadcaloria",0);
        preferences.edit().remove("nombrecomida").commit();
        preferences.edit().remove("pasitos").commit();
        preferences.edit().remove("cantidadcaloria").commit();
    }
    public void iniciarTiempo(){
        countDownTimer = new CountDownTimer(this.getTiempomili(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tiempomili = millisUntilFinished;
                updateTimer();
            }
            @Override
            public void onFinish() {

            }
        }.start();
    }
    private void updateTimer() {
        int minutos = (int) this.getTiempomili()/60000;
        int segundos = (int) this.getTiempomili() % 60000 /1000;
        String tiempoRestante="";
        tiempoRestante = "" + minutos;
        tiempoRestante += " : ";
        if (segundos<10){
            tiempoRestante+="0";
        }
        tiempoRestante += segundos;
        tiempo.setText(tiempoRestante);
    }

    @Override
    protected void onResume() {
        super.onResume();
        running =true;
        Sensor cuentaPasos = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (cuentaPasos != null){
            sensorManager.registerListener(this, cuentaPasos, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(this, "Sensor no disponible", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensor= event.sensor;
        int stepsCounter= Integer.parseInt(pasitos);
        if(sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            if (running){
                contadorpasos++;
                //pasos.setText(String.valueOf(event.values[0]-stepsCounter));
                pasos.setText(""+contadorpasos);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap=map;
        miUbicacion();
    }
    public void agregarMarcador(double lat, double lng){
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas,16);
        if (marcador != null) marcador.remove();
        int height = 200;
        int width = 200;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.logokb);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Mi posición")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        mMap.animateCamera(miUbicacion);
    }
    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            },1000);
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000,0, locationListener);
    }
}