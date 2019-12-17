package com.example.rosentantau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class Camera extends AppCompatActivity {

    private ZBarScannerView vbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        vbc = new ZBarScannerView(this);
        vbc.setResultHandler(new Camera.barcodeimp());
        setContentView(vbc);
    }

    @Override
    public void onResume(){
        super.onResume();
        vbc.startCamera();
    }

    @Override
    public void onPause(){
        super.onPause();
        vbc.stopCamera();
    }

    public class barcodeimp implements ZBarScannerView.ResultHandler {

        @Override
        public void handleResult(Result rawResult) {
            try {
                String bc = rawResult.getContents();
                if(bc != null){
                    boolean camActiva = true;
                    Intent i = new Intent(Camera.this,MainActivity.class);
                    i.putExtra("codigo",bc);
                    startActivityForResult(i,0);
                    vbc.stopCamera();
                }else {
                    Toast.makeText(Camera.this, "no hay resultado", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception ex){
                Toast.makeText(Camera.this, "Exception al leer el codigo \n \n "+ex.toString(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Camera.this,MainActivity.class);
                startActivity(i);
            }
        }
    }


    public void onBackPressed(){
        Intent i = new Intent(Camera.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        vbc.stopCamera();
    }
}
