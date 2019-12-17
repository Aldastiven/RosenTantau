package com.example.rosentantau.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonAdmin {

    public Boolean WriteJson(String path, String nombre, String contenido) throws  Exception{
        boolean ok = false;

            FileOutputStream fos = null;
            File f = new File(path+nombre+".json");
            fos = new FileOutputStream(f);
            fos.write(contenido.getBytes());
            fos.close();
            ok = true;

        return ok;
    }

    public String ReadJson(String path, String nombre) throws Exception{
        String jsonString = "";

            path = path+nombre+".json";
            File file = new File(path);

            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) !=null ){
                sb.append(line).append("\n");
            }

            jsonString = sb.toString();
            return jsonString;

        }
    }

