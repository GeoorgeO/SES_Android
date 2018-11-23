package com.android.soneli.ses_almacen;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador_Tabla extends BaseAdapter {
    ArrayList<Pedido>itemlist=new ArrayList<Pedido>();

    Context mContext;

        public Adaptador_Tabla(Context c,ArrayList<Pedido> ArrayArticulos) {
            mContext = c;
            itemlist=ArrayArticulos;
        }

        public int getCount() {
            return itemlist.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.items_tabla,parent,false);
            TextView ArticuloCodigo=(TextView)itemView.findViewById(R.id.ArticuloCodigo);
            TextView ArticuloDescripcion=(TextView)itemView.findViewById(R.id.ArticuloDescripcion);
            TextView TPedido=(TextView)itemView.findViewById(R.id.TPedido);
            TextView captura=(TextView)itemView.findViewById(R.id.captura);

            ArticuloCodigo.setText(""+itemlist.get(position).getArticuloCodigo());
            ArticuloDescripcion.setText(""+itemlist.get(position).getArticuloDescripcion());
            TPedido.setText(""+itemlist.get(position).getTPedido());
            captura.setText(""+itemlist.get(position).getCaptura());
            if(position%2==0){
                itemView.setBackgroundColor(Color.argb(255,248,248,248));
            }else{
                itemView.setBackgroundColor(Color.argb(255,255,255,255));
            }
            return itemView;
        }



}
