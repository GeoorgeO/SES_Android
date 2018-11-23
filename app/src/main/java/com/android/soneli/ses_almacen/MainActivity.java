package com.android.soneli.ses_almacen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {


    ArrayList<Pedido> arrayArticulos;
    Adaptador_Tabla Adapter;

    ListView Lista;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Lista= (ListView) findViewById(R.id.ListAticulos);

        arrayArticulos=new ArrayList<Pedido>();
        RecibaDatos();
    }

    public void RecibaDatos(){



        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://sonelidesarrollo.ddns.net:8088/Pedidos/PedidosDetalles?PedidosId=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200)
                {
                    try {

                        Pedido Articulo;


                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for(int i=0;i<jsonArray.length();i++)
                        {
                            Articulo=new Pedido(jsonArray.getJSONObject(i).getString("ArticuloCodigo"),jsonArray.getJSONObject(i).getString("ArticuloDescripcion"),Integer.parseInt(jsonArray.getJSONObject(i).getString("TPedido")),0);
                            arrayArticulos.add(Articulo);

                        }

                        Adapter=new Adaptador_Tabla(getApplicationContext(),arrayArticulos);
                        Lista.setAdapter(Adapter);
                        Lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        });

                    }
                    catch (JSONException e)
                    {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, "Fallo ", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
