package teammediel.medielwalk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import java.net.URL;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.HttpURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;


public class Registro extends AppCompatActivity {

    public final static String hostSaveUser = "https://appspooky.herokuapp.com/SaveUser";
    public final static String hostUsers = "https://appspooky.herokuapp.com/listUsers";
    private boolean visible=false;
    private EditText txtpass, user, edad, ediName;
    private RadioGroup genero;
    private ProgressBar proRegis;
    private ImageView ojo;
    private UnToast toast;
    private Button btnRegistrar;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        ojo= (ImageView) findViewById(R.id.ojo);
        proRegis= (ProgressBar) findViewById(R.id.pro2);
        ediName= (EditText) findViewById(R.id.name);
        txtpass= (EditText) findViewById(R.id.txtpass);
        user= (EditText) findViewById(R.id.txtuser);
        edad= (EditText) findViewById(R.id.txtedad);
        btnRegistrar= (Button) findViewById(R.id.btnRegis);
        genero= (RadioGroup) findViewById(R.id.grupoGenero);
    }
    public void logear(View v){
        Intent intent= new Intent(this, Login.class);
        startActivity(intent);
    }

    public void registrar(View v){
        boolean camposLlenos= fullFields();
        boolean internetEnabled= internetActive();

      //  Intent intent= new Intent(this, MainActivity.class);
        if(user.getText().toString().isEmpty() && txtpass.getText().toString().isEmpty()&& ediName.getText().toString().isEmpty() && edad.getText().toString().isEmpty() && genero.getCheckedRadioButtonId() == -1 ){
            toast.show(this, "Faltan datos por completar", Toast.LENGTH_LONG);
       }else if(camposLlenos){//comprobar que ningun campo este vacio
            if(internetEnabled){//comprobar que este conectado a internet
                new TaskRegistrer().execute();

            }
            else{
                toast.show(this, "¡Error, sin conexión!", Toast.LENGTH_LONG);
            }

        }

    }


    public void ejecutarVisibilidad(View v){
        if(visible){
            txtpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            visible=false;
            ojo.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
        }else{
            txtpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            visible=true;
            ojo.setImageResource(R.drawable.eye_off);
        }
    }


    private boolean fullFields(){
        int selectedId = genero.getCheckedRadioButtonId();
        RadioButton generoSelect = (RadioButton) findViewById(selectedId);
        String email = user.getText().toString().trim();
        String password = txtpass.getText().toString().trim();
        String age = edad.getText().toString().trim();
        String name = ediName.getText().toString().trim();

        gender=generoSelect.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(Registro.this,"¡Upps no ingresaste tu usuario!",Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(Registro.this,"¡Upps no ingresaste contraseña!",Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty(age)){
            Toast.makeText(Registro.this,"¡Upps no ingresaste tu edad!",Toast.LENGTH_LONG).show();
            return false;
        }

        if(genero.getCheckedRadioButtonId() == -1 ){
            Toast.makeText(Registro.this,"¡Upps faltan datos!",Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty(name)){
            Toast.makeText(Registro.this,"¡Upps no ingresaste tu nombre!",Toast.LENGTH_LONG).show();
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



    class TaskRegistrer extends AsyncTask<String,Void,String> {


        @Override
        protected void onPreExecute() {
            proRegis.setVisibility(View.VISIBLE);
            btnRegistrar.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            //simular tiempo
            String useroh = user.getText().toString();
            try {
               return getData(useroh);//comprobar que usuario no exista devuelve true si no existe

               // Thread.sleep(5000);

            }catch (Exception e){
                e.printStackTrace();
            }

            //return strings[0];
            return null;
        }



        @Override
        protected void onPostExecute(String s) {
            Log.d("Trae String",s);
              if(s.equals("")){
                  new HTTPAsyncTask().execute();
                    Toast.makeText(Registro.this,"¡Inicia Sesión!",Toast.LENGTH_LONG).show();
                  proRegis.setVisibility(View.INVISIBLE);
                  btnRegistrar.setEnabled(true);
                  Intent intent= new Intent(Registro.this, Login.class);
                  startActivity(intent);
                }
                else{
                    Toast.makeText(Registro.this,"¡Upps Usuario ya existe!",Toast.LENGTH_LONG).show();
                  Intent intent= new Intent(Registro.this, Registro.class);
                  startActivity(intent);
                }

        }





        public String getData(String useri){
            String sql = "https://appspooky.herokuapp.com/listUsers";
            String mensaje = "";
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
                    if(useri.equals(mensaje)){
                        Log.d("Usuario prueba 1",mensaje);

                        return mensaje;
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

//enviar datos para guardar usuario
    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return HttpPost(hostSaveUser);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Se ha detectado un error.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(Registro.this,"¡Hecho!",Toast.LENGTH_LONG).show();
        }
    }
    private String HttpPost(String myUrl) throws IOException, JSONException {
        String result = "";

        URL url = new URL(myUrl);

        // 1. create HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // 2. build JSON object
        JSONObject jsonObject = buidJsonObject();

        // 3. add JSON content to POST request body
        setPostRequestContent(conn, jsonObject);

        // 4. make POST request to the given URL
        conn.connect();

        // 5. return response message
        return conn.getResponseMessage()+"";

    }

    private JSONObject buidJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("usuario", user.getText().toString());
        jsonObject.accumulate("pass",  txtpass.getText().toString());
        jsonObject.accumulate("edad",  edad.getText().toString());
        jsonObject.accumulate("name",ediName.getText().toString());
        jsonObject.accumulate("sexo",gender);

        return jsonObject;
    }

    private void setPostRequestContent(HttpURLConnection conn,
                                       JSONObject jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        Log.i(MainActivity.class.toString(), jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }

}