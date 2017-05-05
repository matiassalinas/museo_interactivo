package io.github.matiassalinas.museo_interactivo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class ZonaActivity extends AppCompatActivity {

    private Zona zona;
    private ArrayList<Objetivo> objetivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona);
        zona = (Zona) getIntent().getSerializableExtra("zona");
        objetivos = new ArrayList<>();
        setTitles();
        showObjetivos();

    }

    private void setTitles(){
        TextView zonaTitle = (TextView) findViewById(R.id.zonaTitle);
        TextView descripcionZona = (TextView) findViewById(R.id.descripcionZona);
        ImageView imageZona = (ImageView) findViewById(R.id.imageZona);
        LinearLayout imageLayoutZona = (LinearLayout) findViewById(R.id.imageLayoutZona);

        zonaTitle.setText(zona.getNombre());
        descripcionZona.setText(zona.getDescripcion());
        if(zona.getImagen() == ""){
            imageLayoutZona.setVisibility(View.GONE);
            descripcionZona.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        else{
            new DownloadImageTask(imageZona).execute(zona.getImagen());
        }
    }

    private void showObjetivos(){
        Thread tr2 = new Thread(){
            @Override
            public void run() {
                try {
                    if(objetivos.size()==0) objetivos = WebServiceActions.getObjetivos(zona.getIdZona());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setList();
                            Toast.makeText(getApplicationContext(),"Objetivos cargados", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        };
        tr2.start();
    }

    private void setList(){
        ObjetivosAdapter adapter = new ObjetivosAdapter(getBaseContext(),objetivos);
        ListView listObjetivos = (ListView) findViewById(R.id.listObjetivos);
        listObjetivos.setAdapter(adapter);
        listObjetivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(ZonaActivity.this, ObjetivoActivity.class);
                Toast.makeText(getApplicationContext(),objetivos.get(position).getTitulo(), Toast.LENGTH_SHORT).show();
                mIntent.putExtra("objetivo", objetivos.get(position));
                startActivity(mIntent);
            }
        });
    }

}
