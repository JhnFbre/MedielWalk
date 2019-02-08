package teammediel.medielwalk;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EditProfileFragment extends Fragment {

    public final static String hostUserUpdate = "https://appspooky.herokuapp.com/updateUser";
    View view;
    String userId;
    Button btnSave;
    ProgressBar pro3;
    UnToast toast;
    private RadioGroup genero;
    EditText editName,editAge;
    Button btnUpdate;
    private String gender;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.edit_profile, viewGroup, false);
        userId = getFromSharedPreferences("id");
        Log.d("Soy el usuario", userId);
        pro3 = (ProgressBar) view.findViewById(R.id.pro3);
        editName = (EditText) view.findViewById(R.id.name);
        editAge = (EditText) view.findViewById(R.id.age);
        genero= (RadioGroup) view.findViewById(R.id.grupoGenero);

        btnUpdate = (Button) view.findViewById(R.id.update);
        fillProfile(userId);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment fr=new ProfileFragment();
                boolean camposLlenos= fullFields();
                boolean internetEnabled= internetActive();
                // fr.setArguments(bn);
                if( editName.getText().toString().isEmpty()&& editAge.getText().toString().isEmpty() && genero.getCheckedRadioButtonId() == -1 ){
                    toast.show(getActivity(), "Faltan datos por completar", Toast.LENGTH_LONG);
                }else if(camposLlenos){//comprobar que ningun campo este vacio
                    if(internetEnabled){//comprobar que este conectado a internet
                        new TaskUpdate().execute();

                    }
                    else{
                        toast.show(getActivity(), "¡Error, sin conexión!", Toast.LENGTH_LONG);
                    }

                }
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer,fr)
                        .addToBackStack(null)
                        .commit();

            }
        });





        return view;



    }

    private boolean fullFields(){
        int selectedId = genero.getCheckedRadioButtonId();
        RadioButton generoSelect = (RadioButton) view.findViewById(selectedId);

        String age = editAge.getText().toString().trim();
        String name = editName.getText().toString().trim();

        gender=generoSelect.getText().toString();

        if(TextUtils.isEmpty(age)){
            Toast.makeText(getActivity(),"¡Upps no ingresaste tu edad!",Toast.LENGTH_LONG).show();
            return false;
        }

        if(genero.getCheckedRadioButtonId() == -1 ){
            Toast.makeText(getActivity(),"¡Upps faltan datos!",Toast.LENGTH_LONG).show();
            return false;
        }

        if(genero.getCheckedRadioButtonId() == -1 ){
            Toast.makeText(getActivity(),"¡Upps faltan datos!",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }


    public boolean internetActive(){
        ConnectivityManager active = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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

    private String getFromSharedPreferences(String usuario) {
        SharedPreferences sharedPre = getActivity().getSharedPreferences("login_preference",Context.MODE_PRIVATE);
        return  sharedPre.getString(usuario,"");

    }

    private void fillProfile(String userId) {
        getData(userId);

    }

    public void getData(String idUser){
        String sql = "https://appspooky.herokuapp.com/lisUser/"+idUser;
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
                String nameUser,userName,ageUser,contactUser,forceUser,genderUser;
                Log.d("El id es",jsonObject.optString("id"));
                nameUser = jsonObject.optString("name");
                userName = jsonObject.optString("usuario");
                genderUser = jsonObject.optString("sexo");
                ageUser = jsonObject.optString("edad");
                editName.setText(nameUser);
                editAge.setText(ageUser+" "+"años");


            }

            //sal.setText(mensaje);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    class TaskUpdate extends AsyncTask<String,Void,String> {


        @Override
        protected void onPreExecute() {
            pro3.setVisibility(View.VISIBLE);
            btnUpdate.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                try {
                    return HttpUpdate(hostUserUpdate);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Se ha detectado un error.";
            }
        }



        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(),"¡Hecho!",Toast.LENGTH_LONG).show();

        }

        private String HttpUpdate(String myUrl) throws IOException, JSONException {
            String result = "";

            URL url = new URL(myUrl);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            // when you are PUT do make sure you assign appropriate header
            // In this case POST.
            Log.d("Antes de buil", userId);
            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.connect();

            // like this you can create your JOSN object which you want to PUT what every JON body you like to PUT
            JSONObject jsonObject = buidJsonObject();
            Log.d("Pase buil", userId);

            // And this is how you will write to the URL
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.flush();
            wr.close();
            return "Finish";


        }


        private JSONObject buidJsonObject() throws JSONException {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", userId);
            jsonObject.put("name", editName.getText().toString());
            jsonObject.put("edad",  editAge.getText().toString());
            jsonObject.put("sexo",gender);
            Log.d("llegue al buil", userId);
            return jsonObject;
        }



    }
}


