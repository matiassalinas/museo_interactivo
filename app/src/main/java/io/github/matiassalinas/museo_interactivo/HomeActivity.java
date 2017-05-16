package io.github.matiassalinas.museo_interactivo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    private Museo museo;
    private Usuario usuario;
    private TextView puntajeTxt;
    private TextView rangoTxt;
    private Button salirBtn;

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        museo = (Museo) getIntent().getSerializableExtra("museo");
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        puntajeTxt = (TextView) findViewById(R.id.puntajeTextView);
        rangoTxt = (TextView) findViewById(R.id.rankTextView);
        historial();
        salirBtn = (Button) findViewById(R.id.salirBtn);
        salirBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                editor = prefs.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(usuario.getHistorial()!=null) historial();
    }

    private void historial(){
        Thread tr0 = new Thread(){
            @Override
            public void run() {
                try {
                    usuario.setHistorial(WebServiceActions.getHistorial(usuario.getIdEntrada()));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String str = getResources().getString(R.string.puntaje, usuario.puntajeTotal());
                            puntajeTxt.setText(str);
                            if(museo.getZonasSize()==0) showZonas();
                            else setList();
                            Toast.makeText(getApplicationContext(),"Historial cargado", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        };
        tr0.start();
    }

    private void showZonas(){
        Thread tr1 = new Thread(){
            @Override
            public void run() {
                try {
                    museo.setZonas(WebServiceActions.getZonas(museo.getIdMuseo()));
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
        String str = getResources().getString(R.string.ranking, usuario.getRango(getBaseContext(),WebServiceActions.getCantObjetivos()));
        rangoTxt.setText(str);
        ZonasAdapter adapter = new ZonasAdapter(getBaseContext(),museo.getZonas(),usuario.getHistorial());
        ListView listZonas = (ListView) findViewById(R.id.listZonas);
        listZonas.setAdapter(adapter);
        listZonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(HomeActivity.this, ZonaActivity.class);
                Toast.makeText(getApplicationContext(),museo.getZona(position).getNombre(), Toast.LENGTH_SHORT).show();
                mIntent.putExtra("zona", museo.getZona(position));
                mIntent.putExtra("usuario",usuario);
                startActivity(mIntent);
            }
        });
    }


}
