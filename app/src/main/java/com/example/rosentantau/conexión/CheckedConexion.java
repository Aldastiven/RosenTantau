package com.example.rosentantau.conexión;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.widget.Toast;

public class CheckedConexion {
     ConnectivityManager cm;
     NetworkInfo ni;


    public boolean checkedConexion(Activity contex){

        try{
            cm =(ConnectivityManager) contex.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            ni = cm.getActiveNetworkInfo();

            boolean tipoConexionWIfi = false;
            boolean tipoConexionDatos = false;

            if(ni != null){
                cm =(ConnectivityManager) contex.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo WIFI = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo DATOS = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                assert WIFI != null;
                if(WIFI.isConnected()){
                    return tipoConexionWIfi=true;
                }
                assert DATOS != null;
                if(DATOS.isConnected()){
                    return tipoConexionDatos=true;
                }
            }else {
                return tipoConexionWIfi = false;
            }

        }catch (Exception ex){
            Toast.makeText(contex, "¡¡ OCURRIO UN ERROR AL REVISAR LA CONEXION DE RED !! \n \n"+ex, Toast.LENGTH_SHORT).show();
        }
    return true;
    }
}
