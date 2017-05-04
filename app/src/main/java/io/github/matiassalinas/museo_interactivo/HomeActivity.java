package io.github.matiassalinas.museo_interactivo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    private Museo museo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        museo = new Museo(1,"Nombre","correo","direccion","telefono",null);
        showZonas();
    }

    private void showZonas(){
        Thread tr1 = new Thread(){
            @Override
            public void run() {
                try {
                    if(museo.getZonasSize()==0) museo.setZonas(WebServiceActions.getZonas(museo.getIdMuseo()));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setList();
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

    private void setList(){
        ZonasAdapter adapter = new ZonasAdapter(getBaseContext(),museo.getZonas());
        ListView listZonas = (ListView) findViewById(R.id.listZonas);
        listZonas.setAdapter(adapter);
        listZonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(HomeActivity.this, ZonaActivity.class);
                Toast.makeText(getApplicationContext(),museo.getZona(position).getNombre(), Toast.LENGTH_SHORT).show();
                mIntent.putExtra("zona", museo.getZona(position));
                startActivity(mIntent);
            }
        });
    }


}
