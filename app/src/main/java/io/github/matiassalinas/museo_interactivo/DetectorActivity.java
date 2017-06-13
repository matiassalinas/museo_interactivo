package io.github.matiassalinas.museo_interactivo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import org.json.JSONException;

import java.util.ArrayList;

public class DetectorActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener {

    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;
    private TextView resultTextView;
    private PointsOverlayView pointsOverlayView;
    private QRCodeReaderView qrCodeReaderView;

    private Usuario usuario;
    private Museo museo;
    private ArrayList<String> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if(resultTextView!=null){
            resultTextView.setText(text);
            pointsOverlayView.setPoints(points);
            final String[] resultado = text.split("##");
            if(resultado.length!=2 || resultado.length == 0){
                Toast.makeText(getApplicationContext(),"Código Inválido", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(!resultado[0].equals(getString(R.string.app_key))){
                Toast.makeText(getApplicationContext(),"Código Inválido", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                qrCodeReaderView.stopCamera();
                Thread tr4 = new Thread(){
                    @Override
                    public void run() {
                        try {
                            result = WebServiceActions.iniciarSesion(resultado[1]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(result == null){
                                    Toast.makeText(getApplicationContext(),"Código Inválido", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                usuario = new Usuario(resultado[1],result.get(0).toString(),null);
                                museo = new Museo(Integer.parseInt(result.get(1)),result.get(2).toString(),result.get(3).toString(),
                                        result.get(4).toString(), result.get(5).toString(), null);
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("usuario", usuario);
                                resultIntent.putExtra("museo", museo);
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();

                            }
                        });


                    }
                };
                tr4.start();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            setContentView(R.layout.activity_detector);
            initQRCodeReaderView();
        }else {
            requestCameraPermission();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (qrCodeReaderView != null) {
            qrCodeReaderView.stopCamera();
        }
    }

    private void initQRCodeReaderView(){
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        resultTextView = (TextView) findViewById(R.id.result_text_view);
        pointsOverlayView = (PointsOverlayView) findViewById((R.id.points_overlay_view));
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        //qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        //qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.CAMERA
        }, MY_PERMISSION_REQUEST_CAMERA);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initQRCodeReaderView();
        } else {
            Toast.makeText(getApplicationContext(),"Camera permission request was denied.", Toast.LENGTH_SHORT).show();
        }
    }
}