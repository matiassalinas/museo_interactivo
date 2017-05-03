package io.github.matiassalinas.museo_interactivo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "LOG BD";
    private Museo museo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        museo = new Museo(1,"Nombre","correo","direccion","telefono",null);
        Thread tr1 = new Thread(){
            @Override
            public void run() {
                try {
                    if(museo.getZonasSize()==0) museo.setZonas(WebServiceActions.getZonas(museo.getIdMuseo()));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i = 0;i<museo.getZonasSize();i++){
                                Log.d("Zona: ",museo.getZona(i).getNombre());
                            }
                            Toast.makeText(getApplicationContext(),"Zonas cargadas", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        };
        tr1.start();
    }


}
