package com.example.rosentantau.sql;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;


public abstract class sqlConect {

    Context context;

    String url = "jdbc:jtds:sqlserver://192.168.2.152;databaseName=Produccion";
    String user = "produccion";
    String pass = "produccion2020";



    public Connection getConexion() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver());

            Log.i("my app","nos conectamos");
            return DriverManager.getConnection(url, user, pass);


        } catch (Exception e) {
            Log.i("my app","error de conexion \n"+e);
            return null;
        }

    }

    public void closeConexion(Connection con) throws Exception {
        if (con != null) {
            con.close();
        }
    }

    public void closeConexion(Connection con, ResultSet rs) throws Exception {
        if (con != null) {
            con.close();
        }
        if (rs != null) {
            rs.close();
        }


    }

}
