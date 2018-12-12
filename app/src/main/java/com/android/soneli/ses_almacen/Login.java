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
                    validarusu();
                }else{
                    Toast.makeText(Login.this, "Conexión sin internet,Favor de verificar.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        obj = new ConexionInternet(this);
    }

    public void validarusu(){
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
    }

}
