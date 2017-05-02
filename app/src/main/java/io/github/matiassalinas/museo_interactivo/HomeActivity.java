package io.github.matiassalinas.museo_interactivo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "LOG BD";
    private int idMuseo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        idMuseo = 1;

        Thread tr1 = new Thread(){
            @Override
            public void run() {
                try {
                    JSONArray json = WebServiceActions.getZonas(idMuseo);
                    for(int i = 0; i<json.length();i++){
                        JSONObject zona = json.getJSONObject(i);
                        Log.d("idZona",(String)zona.get("idZona"));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Zonas cargadas", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        tr1.start();
    }


}
