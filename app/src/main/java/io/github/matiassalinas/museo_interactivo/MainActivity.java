package io.github.matiassalinas.museo_interactivo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private ImageButton scanBtn;
    private ArrayList<String> result;
    private String idEntrada;
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
            Intent mIntent = new Intent(MainActivity.this, DetectorActivity.class);
            startActivityForResult(mIntent,0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null) return;
        Usuario usuario = (Usuario) data.getSerializableExtra("usuario");
        Museo museo = (Museo) data.getSerializableExtra("museo");
        editor.clear();
        editor.putString("login","OK");
        editor.putString("idEntrada",usuario.getIdEntrada());
        editor.commit();
        login(museo,usuario);
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
            idEntrada = prefs.getString("idEntrada",null);
            Thread tr5 = new Thread(){
                @Override
                public void run() {
                    try {
                        result = WebServiceActions.iniciarSesion(idEntrada);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Usuario usuario = new Usuario(idEntrada,result.get(0).toString(),null);
                            Museo museo = new Museo(Integer.parseInt(result.get(1)),result.get(2).toString(),result.get(3).toString(),
                                    result.get(4).toString(), result.get(5).toString(), null);
                            login(museo,usuario);

                        }
                    });


                }
            };
            tr5.start();

        }
    }
}
