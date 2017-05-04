package io.github.matiassalinas.museo_interactivo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ObjetivoActivity extends AppCompatActivity {

    private Objetivo objetivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetivo);

        objetivo = (Objetivo) getIntent().getSerializableExtra("objetivo");
        Log.d("OBJ",objetivo.getTitulo());
    }
}
