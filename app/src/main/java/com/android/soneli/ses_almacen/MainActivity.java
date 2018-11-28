package com.android.soneli.ses_almacen;





import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

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

    ArrayList  Insidencias;
    ArrayList  Excedentes;
    ArrayList  Nopedidos;
    ArrayList faltantes;

    ListView Lista;

    EditText eFolio;
    EditText eCodigoArt;
    EditText eCajas;
    EditText ePxC;

    RadioButton rbCaptura;
    RadioButton rbEdicion;

    Button bAgregar;
    Button bBuscar;
    Button bGuardar;
    Button bLimpiar;

    AlertDialog.Builder builder;
    AlertDialog.Builder baviso;

    boolean sista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Lista= (ListView) findViewById(R.id.ListAticulos);

        eFolio=(EditText) findViewById(R.id.eFolio);
        eCodigoArt=(EditText) findViewById(R.id.eCodigoArt);
        eCajas=(EditText) findViewById(R.id.eCaja);
        ePxC=(EditText) findViewById(R.id.ePxC);

        rbCaptura=(RadioButton) findViewById(R.id.rbCptura);
        rbEdicion=(RadioButton) findViewById(R.id.rbEdicion);

        bAgregar=(Button)findViewById(R.id.bagregar);
        bBuscar=(Button) findViewById(R.id.bBuscar);
        bGuardar=(Button) findViewById(R.id.bGuardar);
        bLimpiar=(Button) findViewById(R.id.bLimpiar);

        arrayArticulos=new ArrayList<Pedido>();
        Insidencias=new ArrayList();
        Excedentes=new ArrayList();
        Nopedidos=new ArrayList();
        faltantes=new ArrayList();

        builder = new AlertDialog.Builder(this);
        baviso = new AlertDialog.Builder(this);

        inhabilitaTodo();

        rbCaptura.setChecked(true);

        eFolio.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    RecibaDatos(eFolio.getText().toString());
                    return true;
                }// end if.

                return false;
            }
        });

        eCodigoArt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {

                    rbCaptura.setEnabled(true);
                    rbEdicion.setEnabled(true);

                    eCajas.setEnabled(true);
                    ePxC.setEnabled(true);

                    bAgregar.setEnabled(true);
                    return true;
                }// end if.

                return false;
            }
        });

        bAgregar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(eCodigoArt.getText().toString().replace(" ","").equals("")){
                    Toast.makeText(MainActivity.this, "Falta escanear un codigo de articulo", Toast.LENGTH_SHORT).show();
                }else{
                    if(eCajas.getText().toString().replace(" ","").equals("") || ePxC.getText().toString().replace(" ","").equals("")){
                        Toast.makeText(MainActivity.this, "Falta agregar la cantidad de cajas y la cantidad de piezas", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean existe=false;

                        if(rbCaptura.isChecked()){


                            for(int i=0;i<arrayArticulos.size();i++){
                                if(arrayArticulos.get(i).getArticuloCodigo().toString().equals(eCodigoArt.getText().toString())){
                                    int lRecibido;
                                    Pedido Articulo;

                                    lRecibido=arrayArticulos.get(i).getCaptura() + (Integer.parseInt(eCajas.getText().toString()) * Integer.parseInt(ePxC.getText().toString()));
                                    Articulo=new Pedido(arrayArticulos.get(i).getArticuloCodigo().toString(),arrayArticulos.get(i).getArticuloDescripcion().toString(),arrayArticulos.get(i).getTPedido(),lRecibido);
                                    arrayArticulos.set(i,Articulo);

                                    Lista.setAdapter(null);
                                    Lista.setAdapter(Adapter);



                                    limpiarAgregado();
                                    existe=true;
                                    break;
                                }
                            }

                            if(existe==false){
                                revisaArticulo(eCodigoArt.getText().toString());
                            }
                        }else{
                            for(int i=0;i<arrayArticulos.size();i++){
                                if(arrayArticulos.get(i).getArticuloCodigo().toString().equals(eCodigoArt.getText().toString())){
                                    int lRecibido;
                                    Pedido Articulo;

                                    lRecibido=(Integer.parseInt(eCajas.getText().toString()) * Integer.parseInt(ePxC.getText().toString()));
                                    Articulo=new Pedido(arrayArticulos.get(i).getArticuloCodigo().toString(),arrayArticulos.get(i).getArticuloDescripcion().toString(),arrayArticulos.get(i).getTPedido(),lRecibido);
                                    arrayArticulos.set(i,Articulo);

                                    Lista.setAdapter(null);
                                    Lista.setAdapter(Adapter);

                                    limpiarAgregado();
                                    existe=true;
                                    break;
                                }
                            }
                            if(existe==false){
                                aviso("Aviso","Articulo NO EXISTE para su edición, Favor de verificar.");
                                baviso.show();
                            }
                        }
                    }
                }




            }
        });

        bLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarTodo();
            }
        });

        rbCaptura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rbCaptura.isChecked()){
                    rbEdicion.setChecked(false);
                }
            }
        });

        rbEdicion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rbEdicion.isChecked()){
                    rbCaptura.setChecked(false);
                }
            }
        });

        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean continua;
                for(int i=0;i<arrayArticulos.size();i++){
                    continua=true;
                    for(int j=0;j<Insidencias.size();j++){
                        Toast.makeText(MainActivity.this, String.valueOf(Insidencias.get(j)), Toast.LENGTH_SHORT).show();
                        if(Integer.parseInt(String.valueOf(Insidencias.get(j)))==i){
                            continua=false;
                            break;
                        }
                    }
                    for(int j=0;j<Nopedidos.size();j++){
                        Toast.makeText(MainActivity.this, String.valueOf(Nopedidos.get(j)), Toast.LENGTH_SHORT).show();
                        if(Integer.parseInt(String.valueOf(Nopedidos.get(j)))==i){
                            continua=false;
                            break;
                        }
                    }
                    if (continua==true){
                        if(arrayArticulos.get(i).getCaptura()<arrayArticulos.get(i).getTPedido()){
                            faltantes.add(i);
                            guardarDatosPedido(eFolio.getText().toString(),arrayArticulos.get(i).ArticuloCodigo,arrayArticulos.get(i).captura);
                        }
                        if(arrayArticulos.get(i).getCaptura()>arrayArticulos.get(i).getTPedido()){
                            Excedentes.add(i);
                            guardarDatosPedido(eFolio.getText().toString(),arrayArticulos.get(i).ArticuloCodigo,arrayArticulos.get(i).TPedido);
                        }
                    }

                }


                for(int j=0;j<Insidencias.size();j++){
                    guardarDatosInsidencias(eFolio.getText().toString(),arrayArticulos.get(Integer.parseInt(String.valueOf(Insidencias.get(j)))).ArticuloCodigo,arrayArticulos.get(Integer.parseInt(String.valueOf(Insidencias.get(j)))).ArticuloDescripcion,arrayArticulos.get(Integer.parseInt(String.valueOf(Insidencias.get(j)))).captura,"Articulo Nuevo");
                }
                for(int j=0;j<Nopedidos.size();j++){
                    guardarDatosInsidencias(eFolio.getText().toString(),arrayArticulos.get(Integer.parseInt(String.valueOf(Insidencias.get(j)))).ArticuloCodigo,arrayArticulos.get(Integer.parseInt(String.valueOf(Insidencias.get(j)))).ArticuloDescripcion,arrayArticulos.get(Integer.parseInt(String.valueOf(Insidencias.get(j)))).captura,"Articulo No pedido");
                }
                for(int j=0;j<Excedentes.size();j++){
                    int ArtExcendente;
                    ArtExcendente=arrayArticulos.get(Integer.parseInt(String.valueOf(Insidencias.get(j)))).captura - arrayArticulos.get(Integer.parseInt(String.valueOf(Insidencias.get(j)))).getTPedido();
                    guardarDatosInsidencias(eFolio.getText().toString(),arrayArticulos.get(Integer.parseInt(String.valueOf(Insidencias.get(j)))).ArticuloCodigo,arrayArticulos.get(Integer.parseInt(String.valueOf(Insidencias.get(j)))).ArticuloDescripcion,ArtExcendente,"Articulos Excedentes");
                }
            }
        });

    }


    public void RecibaDatos(String Folio){

        Lista.setAdapter(null);
        arrayArticulos.clear();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://sonelidesarrollo.ddns.net:8088/Pedidos/PedidosDetalles?PedidosId="+Folio;

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

                        if(arrayArticulos.size()>0){
                            Adapter=new Adaptador_Tabla(getApplicationContext(),arrayArticulos);
                            Lista.setAdapter(Adapter);

                            eCodigoArt.setEnabled(true);
                            eFolio.setEnabled(false);
                        }else{
                            Toast.makeText(MainActivity.this, "Folio No encontrado", Toast.LENGTH_SHORT).show();
                            eCodigoArt.setEnabled(false);
                        }



                        Lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(MainActivity.this, arrayArticulos.get(position).getArticuloDescripcion().toString(), Toast.LENGTH_SHORT).show();

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

    public void revisaArticulo(String PArticulo){

        AsyncHttpClient cliente=new AsyncHttpClient();
        String url = "http://sonelidesarrollo.ddns.net:8088/Pedidos/Articulo?ArticuloCodigo="+PArticulo;
        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200)
                {
                    try {



                        int lRecibido;
                        Pedido Articulo;


                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        if(jsonArray.length()>0){

                            Toast.makeText(MainActivity.this, "lo puse true"+String.valueOf(sista), Toast.LENGTH_SHORT).show();
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                lRecibido=(Integer.parseInt(eCajas.getText().toString()) * Integer.parseInt(ePxC.getText().toString()));
                                Articulo=new Pedido(jsonArray.getJSONObject(i).getString("ArticuloCodigo"),jsonArray.getJSONObject(i).getString("ArticuloDescripcion"),0,lRecibido);
                                arrayArticulos.add(Articulo);
                            }

                            if(arrayArticulos.size()>0){
                                Nopedidos.add(arrayArticulos.size()-1);
                            }else{
                                Nopedidos.add(arrayArticulos.size());
                            }



                            Lista.setAdapter(null);
                            Lista.setAdapter(Adapter);

                            limpiarAgregado();

                        }else{
                            onCreateDialog("Aviso","EL codigo del articulo NO SE ENCUENTRA EN LA LISTA, ¿Deseas que se agrege como NUEVO PRODUCTO a la lista?");
                            builder.show();
                        }
                    }
                    catch (JSONException e)
                    {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(MainActivity.this, String.valueOf(sista), Toast.LENGTH_SHORT).show();


    }

    public void guardarDatosPedido(String pedido,String ArticuloCodigo,int Surtido){
        AsyncHttpClient cliente2 =new AsyncHttpClient();
        String url="http://sonelidesarrollo.ddns.net:8088/Pedidos/PedidosDetallesUpdate?PedidosId="+pedido+"&ArticuloCodigo="+ArticuloCodigo+"&Surtido="+String.valueOf(Surtido);
        cliente2.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {

                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        if (jsonArray.length() > 0) {
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                if(jsonArray.getJSONObject(i).getString("resultado")=="true"){

                                }else{
                                    Toast.makeText(MainActivity.this, "Ocurrio un error al intentar guardar este articulo.", Toast.LENGTH_SHORT).show();
                                }
                            }


                        }
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, "Fallo la conexion al servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void guardarDatosInsidencias(String pedido,String ArticuloCodigo,String Descripcion,int Cantidad,String Tipo){
        AsyncHttpClient cliente2 =new AsyncHttpClient();
        String url="http://sonelidesarrollo.ddns.net:8088/Pedidos/PedidosDetallesInsidencias?PedidosId="+pedido+"&ArticuloCodigo="+ArticuloCodigo+"&ArticuloDescripcion="+Descripcion+"&Cantidad="+String.valueOf(Cantidad)+"&Tipo="+Tipo;
        cliente2.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {

                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        if (jsonArray.length() > 0) {
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                if(jsonArray.getJSONObject(i).getString("resultado")=="true"){

                                }else{
                                    Toast.makeText(MainActivity.this, "Ocurrio un error al intentar guardar este articulo.", Toast.LENGTH_SHORT).show();
                                }
                            }


                        }
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, "Fallo la conexion al servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inhabilitaTodo(){
        eCodigoArt.setEnabled(false);
        eCajas.setEnabled(false);
        ePxC.setEnabled(false);
        rbCaptura.setEnabled(false);
        rbEdicion.setEnabled(false);
        bAgregar.setEnabled(false);
    }

    private void limpiarAgregado(){
        eCodigoArt.setText("");
        eCajas.setText("");
        ePxC.setText("");

        rbCaptura.setChecked(true);
        rbEdicion.setChecked(false);
    }

    private void limpiarTodo(){
        eFolio.setText("");
        limpiarAgregado();
        eFolio.setEnabled(true);
        inhabilitaTodo();

        Lista.setAdapter(null);
    }

    public Dialog onCreateDialog(String titulo,String mensaje) {


        // Set the dialog title
        builder.setTitle(titulo)

        .setMessage(mensaje)

                // Set the action buttons
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                        if (eCodigoArt.getText().toString().replace(" ", "").equals("")) {
                            Toast.makeText(MainActivity.this, "Falta escanear un codigo de articulo", Toast.LENGTH_SHORT).show();
                        } else {
                            if (eCajas.getText().toString().replace(" ", "").equals("") || ePxC.getText().toString().replace(" ", "").equals("")) {
                                Toast.makeText(MainActivity.this, "Falta agregar la cantidad de cajas y la cantidad de piezas", Toast.LENGTH_SHORT).show();
                            } else {
                                boolean existe = false;

                                if (rbCaptura.isChecked()) {


                                    for (int i = 0; i < arrayArticulos.size(); i++) {
                                        if (arrayArticulos.get(i).getArticuloCodigo().toString().equals(eCodigoArt.getText().toString())) {
                                            int lRecibido;
                                            Pedido Articulo;

                                            lRecibido = arrayArticulos.get(i).getCaptura() + (Integer.parseInt(eCajas.getText().toString()) * Integer.parseInt(ePxC.getText().toString()));
                                            Articulo = new Pedido(arrayArticulos.get(i).getArticuloCodigo().toString(), arrayArticulos.get(i).getArticuloDescripcion().toString(), arrayArticulos.get(i).getTPedido(), lRecibido);
                                            arrayArticulos.set(i, Articulo);

                                            Lista.setAdapter(null);
                                            Lista.setAdapter(Adapter);


                                            limpiarAgregado();
                                            existe = true;
                                            break;
                                        }
                                    }

                                    if (existe == false) {
                                        revisaArticulo(eCodigoArt.getText().toString());
                                    }
                                } else {
                                    for (int i = 0; i < arrayArticulos.size(); i++) {
                                        if (arrayArticulos.get(i).getArticuloCodigo().toString().equals(eCodigoArt.getText().toString())) {
                                            int lRecibido;
                                            Pedido Articulo;

                                            lRecibido = (Integer.parseInt(eCajas.getText().toString()) * Integer.parseInt(ePxC.getText().toString()));
                                            Articulo = new Pedido(arrayArticulos.get(i).getArticuloCodigo().toString(), arrayArticulos.get(i).getArticuloDescripcion().toString(), arrayArticulos.get(i).getTPedido(), lRecibido);
                                            arrayArticulos.set(i, Articulo);

                                            Lista.setAdapter(null);
                                            Lista.setAdapter(Adapter);

                                            limpiarAgregado();
                                            existe = true;
                                            break;
                                        }
                                    }
                                    if (existe == false) {
                                        aviso("Aviso", "Articulo NO EXISTE para su edición, Favor de verificar.");
                                        baviso.show();
                                    }
                                }
                            }
                        }




                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    public Dialog aviso(String titulo,String mensaje) {


        // Set the dialog title
        baviso.setTitle(titulo)

                .setMessage(mensaje)

                // Set the action buttons
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                ;

        return baviso.create();
    }

}


