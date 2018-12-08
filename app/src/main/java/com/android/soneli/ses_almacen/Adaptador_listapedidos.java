package com.android.soneli.ses_almacen;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;




public class Adaptador_listapedidos extends BaseAdapter{
    ArrayList<listapedidos>itemlist=new ArrayList<listapedidos>();

    Context mContext;

    public Adaptador_listapedidos(Context c,ArrayList<listapedidos> ArrayArticulos) {
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
        View itemView = inflater.inflate(R.layout.tablalistapedidos,parent,false);
        TextView pedidoid=(TextView)itemView.findViewById(R.id.pedidoid);
        TextView proveedornombre=(TextView)itemView.findViewById(R.id.proveedornombre);
        TextView fechainsert=(TextView)itemView.findViewById(R.id.fechainsert);
        TextView status=(TextView)itemView.findViewById(R.id.status);

        pedidoid.setText(""+itemlist.get(position).getPedidosid());
        proveedornombre.setText(""+itemlist.get(position).getProveedorNombre());
        fechainsert.setText(""+itemlist.get(position).getFechainsert());
        status.setText(""+itemlist.get(position).getEstatus());
        if(position%2==0){
            itemView.setBackgroundColor(Color.argb(255,248,248,248));
        }else{
            itemView.setBackgroundColor(Color.argb(255,255,255,255));
        }
        return itemView;
    }
}
