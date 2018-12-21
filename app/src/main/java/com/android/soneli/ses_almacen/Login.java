package com.android.soneli.ses_almacen;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;


public class Login extends AppCompatActivity {

    EditText eUsuario;
    EditText ePassword;

    Button bIngresar;

    String vUsuario;

    ConexionInternet obj;

    //AsyncHttpClient clienteweb = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eUsuario=(EditText) findViewById(R.id.eUsuario);
        ePassword=(EditText) findViewById(R.id.ePassword);
        bIngresar=(Button) findViewById(R.id.bIngresar);

        bIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(obj.isConnected())
                {
                    //validarusu();
                    getDatos();
                }else{
                    Toast.makeText(Login.this, "Conexión sin internet,Favor de verificar.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        obj = new ConexionInternet(this);
    }

   /* public void validarusu(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://sonelidesarrollo.ddns.net:8088/Usuarios/LoginUsuario?User="+eUsuario.getText().toString()+"&Pass="+ePassword.getText().toString();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        if (jsonArray.length()>0){
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                vUsuario=jsonArray.getJSONObject(i).getString("UsuariosLogin");
                                Intent principal = new Intent(getApplicationContext(), MainActivity.class);
                                principal.putExtra("UsuarioLogin", eUsuario.getText().toString());
                                startActivity(principal);
                                finish();

                            }
                        }else{
                            Toast.makeText(Login.this, "Usuario o contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (JSONException e)
                    {
                        Toast.makeText(Login.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(Login.this, "Fallo ", Toast.LENGTH_SHORT).show();
            }
        });
    } */



    public void getDatos(){
        String sql="http://sonelidesarrollo.ddns.net:8088/Usuarios/LoginUsuario?User="+eUsuario.getText().toString()+"&Pass="+ePassword.getText().toString();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url= null;

        try {
            url = new URL(sql);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            conn.setRequestMethod("GET");
            conn.connect();


            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            json = response.toString();

            JSONArray jsonarr = null;

            jsonarr = new JSONArray(json);

            for (int i = 0; i < jsonarr.length(); i++) {
                JSONObject jsonobject = jsonarr.getJSONObject(i);

                vUsuario = jsonobject.optString("UsuariosLogin");
                Intent principal = new Intent(getApplicationContext(), MainActivity.class);
                principal.putExtra("UsuarioLogin", eUsuario.getText().toString());
                startActivity(principal);
                finish();

            }

            conn.disconnect();


        } catch (IOException e) {
            e.printStackTrace();
            conn.disconnect();
            Toast.makeText(Login.this, "Fallo la conexion al servidor [OPENPEDINS]", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            conn.disconnect();
            Toast.makeText(Login.this, "Fallo la conexion al servidor [OPENPEDINS]", Toast.LENGTH_SHORT).show();
        }
    }

}
