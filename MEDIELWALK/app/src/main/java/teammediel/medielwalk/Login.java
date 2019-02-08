package teammediel.medielwalk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {
    private TextView user, pass;
    private UnToast toast;
    private ProgressBar pro;
    private Button btnValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String sesioniciada=getFromSharedPreferencesNull("usuario");
        if(sesioniciada==null){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            user= (TextView) findViewById(R.id.txtuser);
            pass= (TextView) findViewById(R.id.txtpass);
            pro= (ProgressBar) findViewById(R.id.pro1);
            btnValidar= (Button) findViewById(R.id.button);
        }
        else{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        }

    }
    public void logear(View v){
        boolean camposLlenos= fullFields();
        boolean internetEnabled= internetActive();
        if(!user.getText().toString().isEmpty() && !pass .getText().toString().isEmpty()){
           if(camposLlenos){
               if(internetEnabled){
                   new Task1().execute();
               }
               else{
                   toast.show(this, "¡Error, sin conexión!", Toast.LENGTH_LONG);
               }
           }


        }else{
            toast.show(this, "Complete sus datos", Toast.LENGTH_LONG);
        }
    }

    private String getFromSharedPreferences(String usuario) {
        SharedPreferences sharedPre = getPreferences(Context.MODE_PRIVATE);
        return  sharedPre.getString(usuario,null);

    } private String getFromSharedPreferencesNull(String usuario) {
        usuario=null;
        SharedPreferences sharedPre = getPreferences(Context.MODE_PRIVATE);
        return  sharedPre.getString(usuario,null);

    }


    private boolean fullFields(){

        String usuario = user.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if(TextUtils.isEmpty(usuario)){
            Toast.makeText(Login.this,"¡Upps no ingresaste tu Usuario!",Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(Login.this,"¡Upps no ingresaste contraseña!",Toast.LENGTH_LONG).show();
            return false;
        }


        return true;

    }

    public boolean internetActive(){
        ConnectivityManager active = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (active != null)
        {
            NetworkInfo[] res = active.getAllNetworkInfo();
            if (res != null)
                for (int i = 0; i < res.length; i++)
                    if (res[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
    public void registrar(View v){
        Intent intent2= new Intent(this, Registro.class);
        startActivity(intent2);
    }

    class Task1 extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            pro.setVisibility(View.VISIBLE);
            btnValidar.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            String userLock = user.getText().toString();
            String passwordLock = pass.getText().toString();
            //simular tiempo
            try {
               // Thread.sleep(5000);
                return getData(userLock,passwordLock);//comprobar que usuario no exista devuelve true si no existe
            }catch (Exception  e){
                e.printStackTrace();
            }

            //return strings[0];
            return null;
        }



        @Override
        protected void onPostExecute(String s) {
            Log.d("Trae String",s);
            String idPrueba=s;
            if(s.equals("")){
                Toast.makeText(Login.this,"¡Usuario no existe!",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(Login.this, Login.class);
                startActivity(intent);

            }
            else if(s.equals("fail pass")){
                Toast.makeText(Login.this,"¡Contraseña Incorrecta!",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(Login.this, Login.class);
                startActivity(intent);
            }
            else{
                final String USER_SHARED = idPrueba;
                saveLoginSharedPreferences(USER_SHARED);
                Toast.makeText(Login.this,"¡Sesión Iniciada!",Toast.LENGTH_LONG).show();
                pro.setVisibility(View.INVISIBLE);
                btnValidar.setEnabled(true);
                Intent intent= new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }




        }


        public String getData(String useri,String passweri){
            String sql = "https://appspooky.herokuapp.com/listUsers";
            String mensaje = "";
            String mensajeContrasena = "";
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = null;
            HttpURLConnection conn;

            try {
                url = new URL(sql);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");

                conn.connect();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;

                StringBuffer response = new StringBuffer();

                String json = "";

                while((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }

                json = response.toString();

                JSONArray jsonArr = null;

                jsonArr = new JSONArray(json);

                int cont=0;
                for(int i = 0;i<jsonArr.length();i++){
                    cont++;
                    JSONObject jsonObject = jsonArr.getJSONObject(i);

                    Log.d("Usuario",jsonObject.optString("usuario"));
                    mensaje = jsonObject.optString("usuario");
                    mensajeContrasena = jsonObject.optString("pass");
                    if(useri.equals(mensaje)){
                        Log.d("Usuario prueba 1",mensaje);
                        if(passweri.equals(mensajeContrasena)){
                            mensaje = jsonObject.optString("id");
                            return mensaje;
                        }
                        else {
                            mensaje = "fail pass";
                            return mensaje;
                        }


                    }

                    if(cont==jsonArr.length()){
                        mensaje = "";
                        return mensaje;
                    }
                }

                //sal.setText(mensaje);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mensaje = "";
            return mensaje;
        }

    }

    private void saveLoginSharedPreferences(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", username);
        editor.commit();
    }

}
