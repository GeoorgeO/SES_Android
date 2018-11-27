package com.android.soneli.ses_almacen;

import android.content.Intent;
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

import cz.msebera.android.httpclient.Header;


public class Login extends AppCompatActivity {

    EditText eUsuario;
    EditText ePassword;

    Button bIngresar;

    String vUsuario;

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

                validarusu();

            }
        });
    }

    public void validarusu(){



        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://sonelidesarrollo.ddns.net:8088/Usuarios/LoginUsuario?User="+eUsuario.getText().toString()+"&Pass="+ePassword.getText().toString();

        //Toast.makeText(Login.this, url, Toast.LENGTH_SHORT).show();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200)
                {
                    Toast.makeText(Login.this, "entro a 200", Toast.LENGTH_SHORT).show();
                    try {

                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        if (jsonArray.length()>0){
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                vUsuario=jsonArray.getJSONObject(i).getString("UsuariosLogin");

                                Intent principal = new Intent(getApplicationContext(), MainActivity.class);

                                startActivity(principal);
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
                //Toast.makeText(Login.this, "no entro a 200", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(Login.this, "Fallo ", Toast.LENGTH_SHORT).show();
            }


        });


    }

}
