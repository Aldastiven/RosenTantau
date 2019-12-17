package com.example.rosentantau.Model;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rosentantau.Model.InterFaz.main;
import com.example.rosentantau.Model.TAB.mainTab;
import com.example.rosentantau.Tools.JsonAdmin;
import com.example.rosentantau.conexión.CheckedConexion;
import com.example.rosentantau.sql.sqlConect;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class iMain extends sqlConect implements main {

    List<mainTab> il= new ArrayList<>();
    Connection cn = null;
    String path = null;
    JsonAdmin ja = null;

    public String nombre;

    public  String ins= "INSERT INTO CicloVariedadAndroid (PlanoSiembraID, Fecha ,Indicador, FlagActivo ,idTerminal) VALUES (?,?,?,?,?)";

    public String all="";

    public iMain(String path){
        this.cn = getConexion();
        getPath(path);
    }

    public void getPath(String path){
        ja = new JsonAdmin();
        this.path = path;
    }


    @Override
    public String  insert(mainTab o) throws Exception {
        try {
            o.setIdReg((long) il.size() + 1);
            il.add(o);
            local();

            return "se realizo correctamente";
        }catch (Exception ex){
            return "Exception en el metodo insert iMain \n \n"+ex.toString();
        }
    }

    @Override
    public String update(mainTab o, Long id) throws Exception {
        return null;
    }

    @Override
    public String delete(Long id) throws Exception {
        try{
            all();
            il.clear();
            local();
            return "se elimino";
        }catch (Exception ex){
            return "Corrio un error en la clase delete de iMain.java \n \n"+ex.toString();
        }
    }

    @Override
    public String limpiar(mainTab o) throws Exception {
        return null;
    }

    @Override
    public mainTab oneId(Long id) throws Exception {
        return null;
    }

    @Override
    public boolean local() throws Exception {
        String contenido = il.toString();
        return ja.WriteJson(path, nombre, contenido);
    }

    @Override
    public List<mainTab> all() throws Exception {
        Gson gson = new Gson();
        il = gson.fromJson(ja.ReadJson(path, nombre), new TypeToken<List<mainTab>>() {
        }.getType());

        return il;
    }

    @Override
    public String send(List<mainTab> ls) {
        return null;
    }

    public String record(mainTab o){
        //+"T00:00:00"
        try{
            PreparedStatement ps = cn.prepareStatement(ins);
            ps.setString(1,o.getCodebar());
            ps.setString(2,o.getFecha());
            ps.setString(3,o.getSelSpinner());
            ps.setString(4,"1");
            ps.setString(5,o.getTerminal());
            ps.executeUpdate();

            return "se enviaron los datos";

        }catch (Exception ex){
            Log.i("my app","error en record \n"+ex);
            return "Error en record \n \n"+ex.toString();
        }
    }

    public String eliminar(){
        try{
            all();
            il.clear();
            local();
            return "se elimino";
        }catch (Exception ex){
            return "no se elimino \n \n" +ex.toString();
        }
    }
}
