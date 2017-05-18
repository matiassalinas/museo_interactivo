package io.github.matiassalinas.museo_interactivo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class ObjetivoActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener{

    private Objetivo objetivo;
    private Usuario usuario;

    private int intentos = 0, puntaje=100;
    private TextView intentosText;
    private String textoAnterior = new String();

    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;
    private TextView resultTextView;
    private PointsOverlayView pointsOverlayView;
    private QRCodeReaderView qrCodeReaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetivo);
        intentosText= (TextView) findViewById(R.id.intentosText);
        objetivo = (Objetivo) getIntent().getSerializableExtra("objetivo");
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        setTitles();
    }

    private void setTitles(){
        TextView textoObjetivo = (TextView) findViewById(R.id.textoObjetivo);
        ImageView imageObjetivo = (ImageView) findViewById(R.id.imageObjetivo);
        LinearLayout imageLayoutObjetivo = (LinearLayout) findViewById(R.id.imageLayoutObjetivo);

        setTitle(objetivo.getTitulo());
        textoObjetivo.setText(objetivo.getTexto());
        Log.d("IMG",objetivo.getImagen());
        if(objetivo.getImagen() == "" || objetivo.getImagen().isEmpty()){
            imageLayoutObjetivo.setVisibility(View.GONE);
            //textoObjetivo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        else{
            new DownloadImageTask(imageObjetivo).execute(objetivo.getImagen());
        }

        String str = getResources().getString(R.string.intentos, 0,0,100);
        intentosText.setText(str);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if(resultTextView!=null){
            resultTextView.setText(text);
            String[] resultado = text.split("##");
            pointsOverlayView.setPoints(points);
            if(resultado.length!=3 || resultado.length == 0){
                Toast.makeText(getApplicationContext(),"Código Inválido", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!resultado[0].equals(getString(R.string.app_key))){
                Toast.makeText(getApplicationContext(),"Código Inválido", Toast.LENGTH_SHORT).show();
                return;
            }
            if(resultado[2].equals(String.valueOf(objetivo.getIdObjetivo())) && !text.equals(textoAnterior)){
                intentos++;
                String str = getResources().getString(R.string.intentos, intentos,puntaje,100);
                intentosText.setText(str);
                qrCodeReaderView.stopCamera();
                Thread tr3 = new Thread(){
                    @Override
                    public void run() {
                        WebServiceActions.objetivoCompletado(usuario.getIdEntrada(),objetivo.getIdObjetivo(),puntaje);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                usuario.addHistorial(new Historial(objetivo.getIdObjetivo(),puntaje,objetivo.getIdZona()));
                                Toast.makeText(getApplicationContext(),"Objetivo Completado", Toast.LENGTH_SHORT).show();
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("usuario", usuario);
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        });

                    }
                };
                tr3.start();
            }else if(!text.equals(textoAnterior)){
                intentos++;
                if(puntaje>20) puntaje-=20;
                String str = getResources().getString(R.string.intentos, intentos,0,100);
                intentosText.setText(str);
                Toast.makeText(getApplicationContext(),"Código Escaneado no corresponde al Objetivo", Toast.LENGTH_SHORT).show();
            }
            textoAnterior = text;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("RESUME","RESUME");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            //setContentView(R.layout.activity_detector);
            initQRCodeReaderView();
        }else {
            Log.d("ELSE","ELSE");
            requestCameraPermission();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

    private void initQRCodeReaderView(){
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        resultTextView = (TextView) findViewById(R.id.result_text_view);
        pointsOverlayView = (PointsOverlayView) findViewById((R.id.points_overlay_view));
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setTorchEnabled(true);
        qrCodeReaderView.setBackCamera();
    }
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(ObjetivoActivity.this, new String[]{
                Manifest.permission.CAMERA
        }, MY_PERMISSION_REQUEST_CAMERA);
    }
}
