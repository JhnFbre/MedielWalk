package team.keepBurning;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ProfileFragment extends Fragment {
 TextView showName,showUser,showGender,showPeso,showAltura,showAge;
    View view;
    String userId;
    Button btnEdit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.profile, viewGroup, false);

        userId = getFromSharedPreferences("id");
        Log.d("Soy el usuario", userId);
        showName = (TextView) view.findViewById(R.id.name);
        showUser = (TextView) view.findViewById(R.id.user);
        showGender = (TextView) view.findViewById(R.id.gender);
        showAge = (TextView) view.findViewById(R.id.age);


        fillProfile(userId);

        return view;

    }

    private void fillProfile(String userId) {
        getData(userId);

    }


    private String getFromSharedPreferences(String usuario) {
        SharedPreferences sharedPre = getActivity().getSharedPreferences("login_preference",Context.MODE_PRIVATE);
        return  sharedPre.getString(usuario,"");

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
                showName.setText(nameUser);
                showUser.setText("@"+userName);
                showGender.setText(genderUser);
                showAge.setText(ageUser+" "+"años");


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



}


