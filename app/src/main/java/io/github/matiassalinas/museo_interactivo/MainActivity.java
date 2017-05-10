package io.github.matiassalinas.museo_interactivo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private ImageButton scanBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPreferences();

        scanBtn = (ImageButton) findViewById(R.id.scanButton);
        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.scanButton){
            editor.clear();
            editor.putInt("idMuseo",0);
            editor.putString("idEntrada","MI001");
            editor.putString("login","OK");
            editor.commit();
            Museo museo = new Museo(1,"Nombre","correo","direccion","telefono",null);
            Usuario usuario = new Usuario("MI001", "matias", null);
            login(museo, usuario);
        }
    }

    public void login(Museo museo, Usuario usuario){
        //Intent i = new Intent(this,DetectorActivity.class);
        Intent i = new Intent(this,HomeActivity.class);
        i.putExtra("usuario",usuario);
        i.putExtra("museo",museo);
        startActivity(i);
        finish();

    }

    public void checkPreferences(){
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = prefs.edit();
        String restoredText = prefs.getString("login", null);
        if (restoredText != null) {
            //prefs.getInt("idMuseo",0);
            //prefs.getString("idEntrada",null);
            Museo museo = new Museo(1,"Nombre","correo","direccion","telefono",null);
            Usuario usuario = new Usuario("MI001", "matias", null);
            login(museo,usuario);
        }
    }
}
