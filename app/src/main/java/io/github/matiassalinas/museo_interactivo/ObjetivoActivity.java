package io.github.matiassalinas.museo_interactivo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ObjetivoActivity extends AppCompatActivity {

    private Objetivo objetivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetivo);

        objetivo = (Objetivo) getIntent().getSerializableExtra("objetivo");
        setTitles();
    }

    private void setTitles(){
        TextView objetivoTitle = (TextView) findViewById(R.id.objetivoTitle);
        TextView textoObjetivo = (TextView) findViewById(R.id.textoObjetivo);
        ImageView imageObjetivo = (ImageView) findViewById(R.id.imageObjetivo);
        LinearLayout imageLayoutObjetivo = (LinearLayout) findViewById(R.id.imageLayoutObjetivo);

        objetivoTitle.setText(objetivo.getTitulo());
        textoObjetivo.setText(objetivo.getTexto());
        if(objetivo.getImagen() == ""){
            imageLayoutObjetivo.setVisibility(View.GONE);
            textoObjetivo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        else{
            new DownloadImageTask(imageObjetivo).execute(objetivo.getImagen());
        }
    }
}
