package com.android.soneli.ses_almacen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static ArrayList PedidoId  = new ArrayList(), ArticuloCodigo = new ArrayList(),ArticuloDescripcion = new ArrayList(),TPedido = new ArrayList();
    private Bundle bPedidoId, bArticuloCodigo, bArticuloDescripcion, bTPedido;
    ArrayAdapter<Pedido> Adapter;

    ListView Lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Lista= (ListView) findViewById(R.id.Lista);
    }

    public void RecibaDatos(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://localhost:8088/Pedidos/PedidosDetalles?PedidosId=1",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try{

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

       /* bPedidoId.clear();
        bArticuloCodigo.clear();
        bArticuloDescripcion.clear();
        bTPedido.clear();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://localhost:8088/Pedidos/PedidosDetalles?PedidosId=1";

        client.post(url, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody)
            {
                if (statusCode == 200)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            bPedidoId.putStringArrayList(jsonArray.getJSONObject(i).getString("PedidoId"));
                            bArticuloCodigo.add(jsonArray.getJSONObject(i).getString("ArticuloCodigo") );
                            bArticuloDescripcion.add(jsonArray.getJSONObject(i).getString("ArticuloDescripcion"));
                            bTPedido.add(jsonArray.getJSONObject(i).getString("TPedido") );
                        }
                        //Lista.setAdapter(new imagenAdpater(Reciba_Detalles.this));
                    }
                    catch (JSONException e)
                    {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error)
            {

            }
        });*/

    }
}
