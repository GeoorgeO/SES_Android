package com.android.soneli.ses_almacen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class seleccionarpedido extends AppCompatActivity {

    ListView Listapedidos;

    Button bcancelarl;

    ArrayList<listapedidos> arrayPedidos;

    Adaptador_listapedidos Adapter;
    ConexionInternet obj;

    String vpedido;
    int cliks;

String UsuarioLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionarpedido);
        Listapedidos= (ListView) findViewById(R.id.Listapedidos);

        bcancelarl=(Button) findViewById(R.id.bcancelarl);

        arrayPedidos=new ArrayList<listapedidos>();

        obj = new ConexionInternet(this);

        cliks=0;
        vpedido="";
        if(obj.isConnected())
        {
            RecibaDatos();
        }else{
            Toast.makeText(seleccionarpedido.this, "Sin internet,Favor de verificar su conexión", Toast.LENGTH_SHORT).show();
        }

        bcancelarl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    protected void onStart(){
        super.onStart();
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            if(getIntent().getExtras().getString("UsuarioLogin")==null){

            }else {
                UsuarioLogin = getIntent().getExtras().getString("UsuarioLogin");
            }
        }
    }

    public void RecibaDatos(){

        Listapedidos.setAdapter(null);
        arrayPedidos.clear();
        AsyncHttpClient cliente10 = new AsyncHttpClient();

        String url = "http://sonelidesarrollo.ddns.net:8088/Pedidos/Pedidos";

        cliente10.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200)
                {
                    try {

                        listapedidos Pedido;

                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for(int i=0;i<jsonArray.length();i++)
                        {
                            Pedido=new listapedidos(jsonArray.getJSONObject(i).getString("PedidosId"),jsonArray.getJSONObject(i).getString("ProveedorNombre"),jsonArray.getJSONObject(i).getString("FechaInsert"),"0");
                            arrayPedidos.add(Pedido);

                        }

                        if(arrayPedidos.size()>0){
                            Adapter=new Adaptador_listapedidos(getApplicationContext(),arrayPedidos);
                            Listapedidos.setAdapter(Adapter);
                        }else{
                            Toast.makeText(seleccionarpedido.this, "Folio No encontrado", Toast.LENGTH_SHORT).show();
                        }


                        Listapedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                cliks++;
                                if(vpedido.equals(arrayPedidos.get(position).getPedidosid())){
                                    if(cliks==2){
                                        Intent intent = new Intent(seleccionarpedido.this, MainActivity.class);
                                        intent.removeExtra("NumeroPedido");
                                        intent.putExtra("NumeroPedido", arrayPedidos.get(position).getPedidosid());
                                        intent.putExtra("UsuarioLogin", UsuarioLogin);
                                        startActivity(intent);

                                        finish();
                                    }
                                }else{
                                    cliks=1;
                                }
                                vpedido=arrayPedidos.get(position).getPedidosid();

                            }

                        });

                    }
                    catch (JSONException e)
                    {
                        Toast.makeText(seleccionarpedido.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(seleccionarpedido.this, "Ocurrio un error al conectar al servidor [PEDLIST]", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
