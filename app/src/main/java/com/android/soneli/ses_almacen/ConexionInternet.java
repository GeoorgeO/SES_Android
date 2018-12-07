package com.android.soneli.ses_almacen;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConexionInternet {
    Context context;

    public ConexionInternet(Context context)
    {
        this.context = context;
    }

    public boolean isConnected()
    {
        ConnectivityManager conn = (ConnectivityManager)
                context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(conn != null)
        {
            @SuppressLint("MissingPermission") NetworkInfo info = conn.getActiveNetworkInfo();
            if(info != null)
            {
                if(info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
