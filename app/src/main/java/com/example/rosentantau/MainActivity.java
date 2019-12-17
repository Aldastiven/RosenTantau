package com.example.rosentantau;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rosentantau.Model.TAB.mainTab;
import com.example.rosentantau.Model.iMain;
import com.example.rosentantau.Tools.Dialog;
import com.example.rosentantau.conexión.CheckedConexion;

import java.io.File;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    TextView txtp1, txtp2;
    EditText txt_codbar;

    String path = null;

    List<mainTab> mt = new ArrayList<>();
    iMain im = null;

    Dialog dialogModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        try {
            spinner = findViewById(R.id.Spinner_programación);
            txtp1 = findViewById(R.id.txt_p1);
            txtp2 = findViewById(R.id.txt_p2);
            txt_codbar = findViewById(R.id.cap_codbar);
            txt_codbar.setRawInputType (Configuration.KEYBOARD_QWERTY);

            cargarSpinner();
            responsive();
            cargaPlan();
        }catch (Exception ex){
            Toast.makeText(this, "Error en onCreate \n \n" +ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        getCode();
    }

    //METODOS DE AYUDA (OBTIENE INFORMACIÓN)
    public String getFecha(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        String fecha = sdf.format(calendar.getTime());
        return fecha;
    }
    public String getPhoneName(){
        try {
            BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
            String deviceName = myDevice.getName();
            return deviceName;
        }catch (Exception ex){
            Toast.makeText(this, "Exception al obtener el nombre de la Terminal \n \n"+ex.toString(), Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    //metodo que carga el crud(iMain)
    public void cargaPlan(){
        path = getExternalFilesDir(null) + File.separator;
        try {

            im = new iMain(path);
            im.nombre = "RTsend";

        }catch (Exception ex){
            Toast.makeText(this, "Exception al cargar los planos \n \n"+ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //metodo que comprueba conexion de red
    public boolean metCC(){
        boolean acceso;
        try{

            CheckedConexion cc = new CheckedConexion();

            if(cc.checkedConexion(this)){
                acceso = true;
                return acceso ;
            }else{
                acceso = false;
                return acceso;
            }

        }catch (Exception ex){
            acceso = false;
            return acceso;
        }
    }

    //metodo que valida el nombre de la conexion
    public boolean conNaem(){
        String dir = "";
        int puerto = 000;
        boolean dark;

        try{
            Socket s = new Socket();
            if(s.isConnected()){
                dark = true;
                return dark;
            }else {
                dark = false;
                return dark;
            }
        }catch (Exception ex){
            Toast.makeText(this, "Se encontro una Exception al validar el nombre de la conexion ", Toast.LENGTH_SHORT).show();
            dark = false;
            return dark;
        }
    }

    //metodo para captar el tamaño de la pantalla
    public void responsive() {
        try {
            if((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
                    txtp1.setTextSize(20);
                    txtp2.setTextSize(20);
                    txt_codbar.setTextSize(25);

            }else if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
                //Toast.makeText(this, "Cellphone" , Toast.LENGTH_LONG).show();

            }else if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
                //Toast.makeText(this, "Small sized screen" , Toast.LENGTH_LONG).show();


            }else if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                //Toast.makeText(this, "XLarge sized screen" , Toast.LENGTH_LONG).show();

            }else { }
        }catch (Exception e){
            Toast.makeText(this, "El tamaño de la pantalla no es ni X grande, ni grande, ni normal o pequeña" , Toast.LENGTH_LONG).show();
        }
    }

    //metodo para cargar el spinner tipo de programación
    public void cargarSpinner(){
        String[] OptionArray = {"Selecciona","Programación","Corte"};

        ArrayAdapter<String> spinnerArray = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item_personal, OptionArray);
        spinner.setAdapter(spinnerArray);
    }

    //metodo para activar la camera
    public void btnBarCode(View v){
        Intent i = new Intent(MainActivity.this, Camera.class);
        startActivity(i);
    }

    //metodo que recibe lo que capturo el scanner
    public  void getCode(){
        try{
            Bundle bundle = getIntent().getExtras();

            if(bundle != null){
                String code = bundle.getString("codigo");
                //String[] Arraycode = code.split(",");
                //txt_codbar.setText(Arraycode[0]);
                txt_codbar.setText(code);
            }else {}

        }catch (Exception ex){
            Toast.makeText(this, "Ocurrio un error al recibir el dato del scanner \n \n"+ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    //metodo que guarda el los datos y los emigra al fichero Json
    public void guardar(View v){

        try {
            String codigo = txt_codbar.getText().toString();
            String selected = spinner.getSelectedItem().toString();

            if(!codigo.equals("")){
                if(!selected.equals("Selecciona")) {

                    mainTab mt2 = new mainTab();

                    mt2.setFecha(getFecha());
                    mt2.setCodebar(codigo);
                    mt2.setSelSpinner(selected);
                    mt2.setTerminal(getPhoneName());
                    im.insert(mt2);

                    inicializador();
                    dialogModal = new Dialog(this);
                    //dialogModal.progressBar("Guardando...","Se ha guardado exitosamente el registro",15);
                    dialogModal.Dialog("Se ha guardado exitosamente el registro");
                }else {
                    Toast.makeText(this, "Por favor asegurate que seleccionaste en el desplegable.", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(this, "El campo del codigo no puede quedar vacio.", Toast.LENGTH_LONG).show();
            }

        }catch (Exception ex){
            Toast.makeText(this, "Exception al guardar el Json \n \n"+ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void enviar(View v){
        try{
            iMain im3 = new iMain(path);
            String nombre = "RTsend";
            im3.nombre = nombre;

            List<mainTab> mtt = im3.all();

            if (metCC()) {
                if(mtt.isEmpty()){
                    Toast.makeText(this, "No hay registros pendientes para enviar", Toast.LENGTH_LONG).show();
                }else {
                }
            } else {
                Toast.makeText(this, "Comprueba la conexión a una red" , Toast.LENGTH_SHORT).show();
            }

            /*
            if(mtt.isEmpty()){
                Toast.makeText(this, "No hay registros pendientes para enviar", Toast.LENGTH_LONG).show();
            }else {
            }*/



        }catch (Exception ex){
            Toast.makeText(MainActivity.this, "Exception al enviar datos \n \n"+ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //inicializa los campos al momento de guardar
    public void inicializador(){
        txt_codbar.setText("");
        spinner.setSelection(0);
    }
    
}
